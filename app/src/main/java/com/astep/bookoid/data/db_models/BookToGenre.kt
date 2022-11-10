package com.astep.bookoid.data.db_models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = BookToGenreContract.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = Book::class,
            parentColumns = [BookContract.Columns.ID],
            childColumns = [BookToGenreContract.Columns.BOOK_ID]
        ),
        ForeignKey(
            entity = Genre::class,
            parentColumns = [GenreContract.Columns.ID],
            childColumns = [BookToGenreContract.Columns.GENRE_ID]
        ),
    ],
    indices = [
        Index(
            value = [
                BookToGenreContract.Columns.BOOK_ID,
                BookToGenreContract.Columns.GENRE_ID
            ]
        )
    ]
)
data class BookToGenre(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BookToGenreContract.Columns.ID)
    val id: Long,
    
    @ColumnInfo(name = BookToGenreContract.Columns.BOOK_ID)
    val bookId: Long,

    @ColumnInfo(name = BookToGenreContract.Columns.GENRE_ID)
    val genreId: Long
)
