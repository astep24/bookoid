package com.astep.bookoid.data.db_models

import androidx.room.*

@Entity(
    tableName = BookToAuthorContract.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = Book::class,
            parentColumns = [BookContract.Columns.ID],
            childColumns = [BookToAuthorContract.Columns.BOOK_ID]
        ),
        ForeignKey(
            entity = Author::class,
            parentColumns = [AuthorContract.Columns.ID],
            childColumns = [BookToAuthorContract.Columns.AUTHOR_ID]
        )
    ],
    indices = [
        Index(
            value = [
                BookToAuthorContract.Columns.BOOK_ID,
                BookToAuthorContract.Columns.AUTHOR_ID
            ]
        )
    ]
)
data class BookToAuthor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BookToAuthorContract.Columns.ID)
    val id: Long,
    
    @ColumnInfo(name = BookToAuthorContract.Columns.BOOK_ID)
    val bookId: Long,

    @ColumnInfo(name = BookToAuthorContract.Columns.AUTHOR_ID)
    val authorId: Long
)
