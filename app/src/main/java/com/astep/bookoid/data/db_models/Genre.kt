package com.astep.bookoid.data.db_models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = GenreContract.TABLE_NAME
)
data class Genre(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = GenreContract.Columns.ID)
    val id: Long,
    
    @ColumnInfo(name = GenreContract.Columns.GENRE_NAME)
    val genreName: String,

    @ColumnInfo(name = GenreContract.Columns.API_KEY)
    val apiKey: String
)
