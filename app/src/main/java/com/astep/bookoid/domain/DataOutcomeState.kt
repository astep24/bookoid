package com.astep.bookoid.domain

sealed class DataOutcomeState<T> {
    data class Progress<T> (val data: T? = null) : DataOutcomeState<T>()
    data class Success<T> (val data: T) : DataOutcomeState<T>()
    data class Failure<T> (val e: Throwable) : DataOutcomeState<T>()
}
