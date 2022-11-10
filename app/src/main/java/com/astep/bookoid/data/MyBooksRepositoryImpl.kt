package com.astep.bookoid.data

import com.astep.bookoid.data.db.Database
import com.astep.bookoid.domain.MyBook
import com.astep.bookoid.domain.MyBooksRepository
import com.astep.bookoid.domain.DataOutcomeState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class MyBooksRepositoryImpl : MyBooksRepository {
    
    override suspend fun getMyBooks(searchValue: String): Flow<DataOutcomeState<List<MyBook>>> =
        flow {
            emit(DataOutcomeState.Progress())
            
            Database.instance.booksDao()
                .getBooksWithSearchValue(searchValue)
                .map {
                    MyBook(
                        id = it.id,
                        imageSource = it.imageSource,
                        title = it.title,
                        description = it.description
                    )
                }
                .let {
                    emit(DataOutcomeState.Success(it))
                }
        }
            .catch {
                emit(DataOutcomeState.Failure(it))
            }
            .flowOn(Dispatchers.IO)
    
}