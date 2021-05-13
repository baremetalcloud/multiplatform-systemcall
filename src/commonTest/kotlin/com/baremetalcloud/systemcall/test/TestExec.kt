package com.baremetalcloud.systemcall.test

import com.baremetalcloud.systemcall.*
import com.baremetalcloud.runblocking.runBlockingCommon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlin.test.*

class TestExec : CoroutineScope by GlobalScope {

    @Test
    fun helloworld(): Unit = runBlockingCommon {
        systemCall("echo 'hello world'")
            .onSuccess {
                println(it.stdout)
                assertTrue(it.stdout.trim() == "hello world")
            }.onFailure {
                throw it.toThrowable()
            }
    }

    @Test
    fun execInDir(): Unit = runBlockingCommon {
        systemCall("pwd", dir = ".")
            .onSuccess {
                println(it.stdout)
                assertTrue(it.stdout.trim().startsWith("/"))
            }.onFailure {
                throw it.toThrowable()
            }
    }
}

private fun ExecResult.assertSuccess() = assertTrue(this is ExecResult.Success)
