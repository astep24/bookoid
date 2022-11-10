package com.astep.bookoid.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.astep.bookoid.data.db_models.Book
import com.astep.bookoid.data.db_models.BookContract
import com.astep.bookoid.data.db_models.Genre
import com.astep.bookoid.data.db_models.GenreContract

@Dao
abstract class InitDao {
    
    @Query("""
        SELECT
            COUNT(*)
        FROM ${GenreContract.TABLE_NAME}
    """)
    protected abstract suspend fun getCountGenres(): List<Int>

    @Query("""
        SELECT
            COUNT(*)
        FROM ${BookContract.TABLE_NAME}
    """)
    protected abstract suspend fun getCountBooks(): List<Int>
    
    @Insert
    protected abstract suspend fun insertGenres(data: List<Genre>)
    
    @Insert
    protected abstract suspend fun insertBooks(data: List<Book>)
    
    @Transaction
    open suspend fun prepopulateGenres(list: List<Genre>) {
        val count = getCountGenres().getOrNull(0) ?: 0
        if (count == 0) insertGenres(list)
    }
    
    @Transaction
    open suspend fun insertBooksTestData(list: List<Book>) {
        val count = getCountBooks().getOrNull(0) ?: 0
        if (count == 0) insertBooks(list)
    }
    
}