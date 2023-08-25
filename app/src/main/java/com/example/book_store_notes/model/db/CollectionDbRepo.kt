package com.example.book_store_notes.model.db

import kotlinx.coroutines.flow.Flow

interface CollectionDbRepo {

    suspend fun getBooksFromRepo(): Flow<List<DbBook>>

    suspend fun getBook(bookId: String): Flow<DbBook>

    suspend fun addBookToRepo(dbBook: DbBook)

    suspend fun updateBookInRepo(dbBook: DbBook)

    suspend fun deleteBookFromRepo(dbBook: DbBook)
}