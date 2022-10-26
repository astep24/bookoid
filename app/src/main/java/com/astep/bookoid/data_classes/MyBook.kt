package com.astep.bookoid.data_classes

// TODO: сюда надо будет добавить ID (long) для ListAdapter +
//  потому что в базе в будущем будет уникальный айди у элементов
data class MyBook(
    val imageSource: String,
    val title: String,
    val description: String?
)
