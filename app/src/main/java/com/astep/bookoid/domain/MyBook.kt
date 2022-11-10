package com.astep.bookoid.domain

data class MyBook(
    val id: Long,
    val imageSource: String?,
    val title: String,
    val description: String?
)
