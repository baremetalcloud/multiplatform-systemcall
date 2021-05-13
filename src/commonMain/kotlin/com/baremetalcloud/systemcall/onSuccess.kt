package com.baremetalcloud.systemcall

public suspend fun ExecResult.onSuccess(action: suspend (ExecResult.Success) -> Unit): ExecResult {
    when (this) {
        is ExecResult.Success -> action(this)
    }
    return this
}

