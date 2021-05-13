package com.baremetalcloud.systemcall
public fun shellSplit(string: CharSequence): List<String> {
    val tokens = ArrayList<String>()
    var escaping = false
    var quoteChar = ' '
    var quoting = false
    var current = StringBuilder()
    for (i in 0 until string.length) {
        val c = string[i]
        if (escaping) {
            current.append(c)
            escaping = false
        } else if (c == '\\' && !(quoting && quoteChar == '\'')) {
            escaping = true
        } else if (quoting && c == quoteChar) {
            quoting = false
        } else if (!quoting && (c == '\'' || c == '"')) {
            quoting = true
            quoteChar = c
        } else if (!quoting && Character.isWhitespace(c)) {
            if (current.isNotEmpty()) {
                tokens.add(current.toString())
                current = StringBuilder()
            }
        } else {
            current.append(c)
        }
    }
    if (current.isNotEmpty()) {
        tokens.add(current.toString())
    }
    return tokens
}