package com.astep.bookoid.data.db_models

object BookContract {
    const val TABLE_NAME = "books"
    
    object Columns {
        const val ID = "id"
        const val IMAGE_SOURCE = "image_source"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val YEAR = "year"
        const val PAGES_COUNT = "pages_count"
    }
}
