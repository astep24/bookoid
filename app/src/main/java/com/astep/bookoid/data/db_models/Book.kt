package com.astep.bookoid.data.db_models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = BookContract.TABLE_NAME
)
data class Book(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BookContract.Columns.ID)
    val id: Long,
    
    @ColumnInfo(name = BookContract.Columns.IMAGE_SOURCE)
    val imageSource: String?,

    @ColumnInfo(name = BookContract.Columns.TITLE)
    val title: String,

    @ColumnInfo(name = BookContract.Columns.DESCRIPTION)
    val description: String?,

    @ColumnInfo(name = BookContract.Columns.YEAR)
    val year: String?,

    @ColumnInfo(name = BookContract.Columns.PAGES_COUNT)
    val pagesCount: Int?
)
