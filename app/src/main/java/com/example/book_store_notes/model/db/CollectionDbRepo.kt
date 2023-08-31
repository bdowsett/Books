package com.example.book_store_notes.model.db

import kotlinx.coroutines.flow.Flow

interface CollectionDbRepo {

    suspend fun getBooksFromRepo(): Flow<List<DbBook>>

    suspend fun getBook(bookId: String): Flow<DbBook>

    suspend fun addBookToRepo(dbBook: DbBook)

    suspend fun updateBookInRepo(dbBook: DbBook)

    suspend fun deleteBookFromRepo(dbBook: DbBook)

    suspend fun getAllNotes(): Flow<List<DbNote>>

    suspend fun getNotesFromRepo(bookId: Int): Flow<List<DbNote>>

    suspend fun addNoteToRepo(note: DbNote)

    suspend fun updateNoteInRepo(note: DbNote)

    suspend fun deleteNoteFromRepo(note: DbNote)

    suspend fun deleteAllNotes(bookId: DbBook)
}