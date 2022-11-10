package com.astep.bookoid.data.db_models

object BookToGenreContract {
    const val TABLE_NAME = "books2genres"
    
    object Columns {
        const val ID = "id"
        const val BOOK_ID = "book_id"
        const val GENRE_ID = "genre_id"
    }
}
