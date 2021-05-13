package com.baremetalcloud.systemcall

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit


private sealed class ExecException: Exception() {
    object Timeout: ExecException()
    object ExitCode: ExecException()
}

public actual suspend fun systemCall(cmd: String, options: ExecOptions, dir: String): ExecResult = withContext(Dispatchers.IO) {
    lateinit var result: ExecResult
    lateinit var process: Process
    runCatching {
        val builder = ProcessBuilder(shellSplit(cmd)).directory(dir.toJavaFile().apply {
            mkdirs()
        })
        process = builder.start().also { it.waitFor(options.timeout, TimeUnit.SECONDS) }
        if(process.isAlive) {
            throw ExecException.Timeout
        }
        if (process.exitValue() != 0) {
            throw ExecException.ExitCode
        }
    }.onSuccess {
        result = ExecResult.Success(
            stdout = process.inputStream.bufferedReader().readText(),
            stderr = process.errorStream.bufferedReader().readText()
        )
    }.onFailure {
        result = if(it is ExecException) {
            when(it) {
                ExecException.ExitCode -> ExecResult.Failure.ExitCode(
                    exitCode = process.exitValue(),
                    stdout = process.inputStream.bufferedReader().readText(),
                    stderr = process.errorStream.bufferedReader().readText(),
                )
                ExecException.Timeout -> ExecResult.Failure.Timeout
            }
        } else {
            ExecResult.Failure.Exception(it)
        }
    }
    return@withContext result
}

private fun String.toJavaFile(): java.io.File = java.io.File(this)
