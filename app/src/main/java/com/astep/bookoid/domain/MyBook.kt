package com.astep.bookoid.domain

// TODO: сюда надо будет добавить ID (long) для ListAdapter +
//  потому что в базе в будущем будет уникальный айди у элементов
data class MyBook(
    val id: Long,
    val imageSource: String,
    val title: String,
    val description: String?
)
