package com.astep.bookoid.data.db_models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = AuthorContract.TABLE_NAME
)
data class Author(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = AuthorContract.Columns.ID)
    val id: Long,
    
    @ColumnInfo(name = AuthorContract.Columns.AUTHOR)
    val author: String,

    @ColumnInfo(name = AuthorContract.Columns.API_KEY)
    val apiKey: String
)
