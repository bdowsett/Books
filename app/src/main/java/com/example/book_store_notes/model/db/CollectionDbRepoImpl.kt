package com.example.book_store_notes.model.db

import kotlinx.coroutines.flow.Flow

class CollectionDbRepoImpl(private val bookDao: BookDao, private val noteDao: NoteDao) : CollectionDbRepo {

    override suspend fun getBooksFromRepo() = bookDao.getBooks()

    override suspend fun getBook(bookId: String) = bookDao.getBook(bookId)

    override suspend fun addBookToRepo(dbBook: DbBook) = bookDao.addBook(dbBook)

    override suspend fun updateBookInRepo(dbBook: DbBook) = bookDao.upDateBook(dbBook)

    override suspend fun deleteBookFromRepo(dbBook: DbBook) = bookDao.deleteBook(dbBook)

    override suspend fun getAllNotes(): Flow<List<DbNote>> = noteDao.getAllNotes()


    override suspend fun getNotesFromRepo(bookId: Int): Flow<List<DbNote>> = noteDao.getNotes(bookId)

    override suspend fun addNoteToRepo(note: DbNote) = noteDao.addNote(note)

    override suspend fun updateNoteInRepo(note: DbNote) = noteDao.updateNote(note)

    override suspend fun deleteNoteFromRepo(note: DbNote) = noteDao.deleteNotes(note)

    override suspend fun deleteAllNotes(bookId: DbBook) = noteDao.deleteAllNotes(bookId.id)
}