package com.baremetalcloud.systemcall
import kotlinx.datetime.Clock


public data class ExecOptions(
    val timeout: Long = 60
)

public sealed class ExecResult {
    public data class Success(val stdout: String, val stderr: String? = null): ExecResult()
    public sealed class Failure: ExecResult() {
        public object Timeout: Failure()
        public data class Exception(val error: Throwable): Failure()
        public data class ExitCode(val exitCode: Int, val stdout: String, val stderr: String): Failure()
    }
}

public expect suspend fun systemCall(cmd: String, options: ExecOptions = ExecOptions(), dir: String = "/tmp/${Clock.System.now().epochSeconds}"): ExecResult



