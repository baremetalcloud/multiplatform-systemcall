package com.baremetalcloud.systemcall

public suspend fun ExecResult.onFailure(action: suspend (ExecResult.Failure) -> Unit): ExecResult {
    when (this) {
        is ExecResult.Failure -> action(this)
    }
    return this
}

