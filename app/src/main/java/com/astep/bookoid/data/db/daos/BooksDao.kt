package com.astep.bookoid.data.db.daos

import androidx.room.Dao
import androidx.room.Query
import com.astep.bookoid.data.db_models.Book
import com.astep.bookoid.data.db_models.BookContract
import com.astep.bookoid.utils.prepareForSql

@Dao
abstract class BooksDao {
    
    @Query("""
        SELECT *
        FROM ${BookContract.TABLE_NAME}
    """)
    protected abstract suspend fun getAllBooks(): List<Book>
    
    @Query("""
        SELECT *
        FROM ${BookContract.TABLE_NAME}
        WHERE
            ( ${BookContract.Columns.TITLE} LIKE :searchValue ESCAPE '\' )
            OR ( ${BookContract.Columns.DESCRIPTION} LIKE :searchValue ESCAPE '\' )
    """)
    protected abstract suspend fun getBooksByValue(searchValue: String): List<Book>
    
    suspend fun getBooksWithSearchValue(searchValue: String): List<Book> =
        if (searchValue.isBlank()) {
            getAllBooks()
        } else {
            getBooksByValue("%${searchValue.prepareForSql()}%")
        }
    
}