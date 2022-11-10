package com.astep.bookoid.utils

fun String.prepareForSql(): String =
    this
        .trim()
        .replace("\\", "\\\\")
        .replace("%", "\\%")
        .replace("_", "\\_")
        
