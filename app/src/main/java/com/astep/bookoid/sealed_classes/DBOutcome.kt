package com.astep.bookoid.sealed_classes

sealed class DBOutcome<T> {
    data class Progress<T> (val data: T? = null) : DBOutcome<T>()
    data class Success<T> (val data: T) : DBOutcome<T>()
    data class Failure<T> (val e: Throwable) : DBOutcome<T>()
}
