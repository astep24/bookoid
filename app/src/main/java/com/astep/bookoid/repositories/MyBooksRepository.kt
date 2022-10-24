package com.astep.bookoid.repositories

import com.astep.bookoid.data_classes.MyBook
import com.astep.bookoid.sealed_classes.DBOutcome
import com.astep.bookoid.utils.DebugLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlin.random.Random

class MyBooksRepository {

    suspend fun getMyBooks(searchValue: String): Flow<DBOutcome<List<MyBook>>> = flow {

        // TODO: fetch from database

        emit(DBOutcome.Progress())
        delay(5000)

        if (Random.nextInt(10) == 2) emit(DBOutcome.Failure(Error("DB failure"))) else
            listOf(
                MyBook("https://covers.openlibrary.org/b/isbn/0393955524-M.jpg", "abc", "cde"),
                MyBook("https://covers.openlibrary.org/b/isbn/1783440899-M.jpg", "efg", "ghi"),
                MyBook("https://covers.openlibrary.org/b/isbn/9781783440894-M.jpg", "ikl", "lmna"),
                MyBook("https://covers.openlibrary.org/b/isbn/9780385533225-M.jpg", "opqa", null),
                MyBook("https://covers.openlibrary.org/b/isbn/9780385533225-M.jpg", "qrsb", null),
                MyBook("https://covers.openlibrary.org/b/isbn/9780385533225-M.jpg", "tuvc", null),
                MyBook("https://covers.openlibrary.org/b/isbn/9780385533225-M.jpg", "vwx", "xyzd"),
                MyBook("https://covers.openlibrary.org/b/isbn/9780385533225-M.jpg", "Title 8", "Description 8"),
                MyBook("https://covers.openlibrary.org/b/isbn/9780385533225-M.jpg", "Title 9", "Description 9"),
                MyBook("https://covers.openlibrary.org/b/isbn/9780385533225-M.jpg", "Title 10", null),
            )
                .filter {
                    it.title.contains(searchValue.trim(), ignoreCase = true) ||
                            it.description?.contains(searchValue.trim(), ignoreCase = true)?:false
                }
                .let {
                    emit(DBOutcome.Success(it))
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
        .catch { emit(DBOutcome.Failure(it)) }
        .flowOn(Dispatchers.IO)

}