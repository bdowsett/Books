package com.example.book_store_notes.model.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {
    @Query("SELECT * FROM ${Constants.NOTES_TABLE} ORDER BY id ASC")
    fun getAllNotes(): Flow<List<DbNote>>

    @Query("SELECT * FROM ${Constants.NOTES_TABLE} WHERE bookId = :bookId ORDER BY ID ASC")
    fun getNotes(bookId: Int): Flow<List<DbNote>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addNote(note: DbNote)

    @Update
    fun updateNote(note: DbNote)

    @Delete
    fun deleteNotes(note: DbNote)

    @Query("DELETE FROM ${Constants.NOTES_TABLE} WHERE bookId = :bookId")
    fun deleteAllNotes(bookId: Int)


}