package com.astep.bookoid.data.db

import android.content.Context
import androidx.room.Room
import com.astep.bookoid.data.db_models.Book
import com.astep.bookoid.data.db_models.Genre
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Database {
    lateinit var instance: ApplicationDatabase
        private set
    
    fun init(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            instance = Room.databaseBuilder(
                context,
                ApplicationDatabase::class.java,
                ApplicationDatabase.DB_NAME
            ).build()
            
            // TODO: prepopulate from assets by calling .createFromAsset("database/myapp.db")
            instance.initDao().prepopulateGenres(GENRES_PREPOPULATE_VALUES)
    
            // TODO: make test data be inserted into database only when build type is 'debug' or 'qa'
            instance.initDao().insertBooksTestData(BOOKS_TEST_DATA)
        }
    }
    
    private val GENRES_PREPOPULATE_VALUES = listOf(
        "Fiction", "Nonfiction", "Drama", "Poetry", "Folktale"
    ).map {
        Genre(id = 0L, genreName = it, apiKey = "DUMMY") // TODO: make a correct api_key
    }
    
    private val BOOKS_TEST_DATA = listOf(
        Book(
            0L,
            "https://covers.openlibrary.org/b/isbn/0393955524-M.jpg",
            "abc abc abc abc abc",
            "cde",
            null,
            null
        ),
        Book(
            0L,
            "https://covers.openlibrary.org/b/isbn/1783440899-M.jpg",
            "ef%g",
            "ghi",
            null,
            null
        ),
        Book(
            0L,
            "https://covers.openlibrary.org/b/isbn/9781783440894-M.jpg",
            "ik\\l",
            "lmna",
            null,
            null
        ),
        Book(
            0L,
            "https://covers.openlibrary.org/b/isbn/9780385533225-M.jpg",
            "opq\\\\a",
            null,
            null,
            null
        ),
        Book(
            0L,
            "https://covers.openlibrary.org/b/isbn/9780385533225-M.jpg",
            "qrsb",
            null,
            null,
            null
        ),
        Book(
            0L,
            "https://covers.openlibrary.org/b/isbn/9780385533225-M.jpg",
            "tuvc",
            null,
            null,
            null
        ),
        Book(
            0L,
            "https://covers.openlibrary.org/b/isbn/9780385533225-M.jpg",
            "vwx",
            "xyzd",
            null,
            null
        ),
        Book(
            0L,
            "https://covers.openlibrary.org/b/isbn/9780385533225-M.jpg",
            "Title 8",
            "Description 8",
            null,
            null
        ),
        Book(
            0L,
            "https://covers.openlibrary.org/b/isbn/9780385533225-M.jpg",
            "Title 9",
            "Description 9",
            null,
            null
        ),
        Book(
            0L,
            "https://covers.openlibrary.org/b/isbn/9780385533225-M.jpg",
            "Title 10",
            null,
            null,
            null
        ),
    )
}