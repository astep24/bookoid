package com.astep.bookoid.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.astep.bookoid.data.db.daos.BooksDao
import com.astep.bookoid.data.db.daos.InitDao
import com.astep.bookoid.data.db_models.*

@Database(
    entities = [
        Book::class,
        Genre::class,
        Author::class,
        BookToGenre::class,
        BookToAuthor::class
    ],
    version = ApplicationDatabase.DB_VERSION
)
abstract class ApplicationDatabase : RoomDatabase() {
    
    abstract fun initDao(): InitDao
    abstract fun booksDao(): BooksDao
    
    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "application-database"
    }
    
}