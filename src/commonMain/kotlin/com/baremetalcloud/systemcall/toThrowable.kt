package com.baremetalcloud.systemcall

public fun ExecResult.Failure.toThrowable(): Throwable = Exception(this.toString())
