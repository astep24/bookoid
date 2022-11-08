package com.astep.bookoid.domain

import kotlinx.coroutines.flow.Flow

interface MyBooksRepository {
    suspend fun getMyBooks(searchValue: String): Flow<DataOutcomeState<List<MyBook>>>
}