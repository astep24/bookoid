package com.astep.bookoid.data

import com.astep.bookoid.domain.MyBook
import com.astep.bookoid.domain.MyBooksRepository
import com.astep.bookoid.domain.DataOutcomeState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlin.random.Random

class MyBooksRepositoryImpl : MyBooksRepository {
    
    override suspend fun getMyBooks(searchValue: String): Flow<DataOutcomeState<List<MyBook>>> =
        flow {
            
            // TODO: fetch from database
            
            emit(DataOutcomeState.Progress())
            delay(5000)
            
            if (Random.nextInt(10) == 2) emit(DataOutcomeState.Failure(Error("DB failure"))) else
                listOf(
                    MyBook(
                        0L,
                        "https://covers.openlibrary.org/b/isbn/0393955524-M.jpg",
                        "abc abc abc abc abc",
                        "cde"
                    ),
                    MyBook(
                        1L,
                        "https://covers.openlibrary.org/b/isbn/1783440899-M.jpg",
                        "efg",
                        "ghi"
                    ),
                    MyBook(
                        2L,
                        "https://covers.openlibrary.org/b/isbn/9781783440894-M.jpg",
                        "ikl",
                        "lmna"
                    ),
                    MyBook(
                        3L,
                        "https://covers.openlibrary.org/b/isbn/9780385533225-M.jpg",
                        "opqa",
                        null
                    ),
                    MyBook(
                        4L,
                        "https://covers.openlibrary.org/b/isbn/9780385533225-M.jpg",
                        "qrsb",
                        null
                    ),
                    MyBook(
                        5L,
                        "https://covers.openlibrary.org/b/isbn/9780385533225-M.jpg",
                        "tuvc",
                        null
                    ),
                    MyBook(
                        6L,
                        "https://covers.openlibrary.org/b/isbn/9780385533225-M.jpg",
                        "vwx",
                        "xyzd"
                    ),
                    MyBook(
                        7L,
                        "https://covers.openlibrary.org/b/isbn/9780385533225-M.jpg",
                        "Title 8",
                        "Description 8"
                    ),
                    MyBook(
                        8L,
                        "https://covers.openlibrary.org/b/isbn/9780385533225-M.jpg",
                        "Title 9",
                        "Description 9"
                    ),
                    MyBook(
                        9L,
                        "https://covers.openlibrary.org/b/isbn/9780385533225-M.jpg",
                        "Title 10",
                        null
                    ),
                )
                    .filter {
                        it.title.contains(searchValue.trim(), ignoreCase = true) ||
                                it.description?.contains(
                                    searchValue.trim(),
                                    ignoreCase = true
                                ) ?: false
                    }
                    .let {
                        emit(DataOutcomeState.Success(it))
                    }
        }
            //        .onStart {
            //            DebugLogger.d(msg = "Starting db flow")
            //        }
            //        .onEach {
            //            val msg = when (it) {
            //                is DBOutcome.Progress -> "progress->"
            //                is DBOutcome.Failure -> "failure(${it.e})->"
            //                is DBOutcome.Success -> "list(${it.data.joinToString(";")})->"
            //                else -> "error->"
            //            }
            //            DebugLogger.d(msg = msg)
            //        }
            .catch {
                emit(DataOutcomeState.Failure(it))
            }
            .flowOn(Dispatchers.IO)
    
}