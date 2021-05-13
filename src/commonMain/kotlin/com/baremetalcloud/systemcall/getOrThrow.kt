package com.baremetalcloud.systemcall

public fun ExecResult.getOrThrow(): String = when (this) {
    is ExecResult.Success -> stdout
    else -> throw Exception(toString())
}
