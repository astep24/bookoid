package com.astep.bookoid.data.db_models

object BookToAuthorContract {
    const val TABLE_NAME = "books2authors"
    
    object Columns {
        const val ID = "id"
        const val BOOK_ID = "book_id"
        const val AUTHOR_ID = "author_id"
    }
}
