package com.example.book_store_notes.model.db

class CollectionDbRepoImpl(private val bookDao: BookDao) : CollectionDbRepo {

    override suspend fun getBooksFromRepo() = bookDao.getBooks()

    override suspend fun getBook(bookId: String) = bookDao.getBook(bookId)

    override suspend fun addBookToRepo(dbBook: DbBook) = bookDao.addBook(dbBook)

    override suspend fun updateBookInRepo(dbBook: DbBook) = bookDao.upDateBook(dbBook)

    override suspend fun deleteBookFromRepo(dbBook: DbBook) = bookDao.deleteBook(dbBook)
}