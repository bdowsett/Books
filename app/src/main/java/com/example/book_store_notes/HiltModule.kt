package com.example.book_store_notes

import android.content.Context
import androidx.room.Room
import com.example.book_store_notes.model.api.BookApiRepo
import com.example.book_store_notes.model.api.BookService
import com.example.book_store_notes.model.db.BookDao
import com.example.book_store_notes.model.db.CollectionDB
import com.example.book_store_notes.model.db.CollectionDbRepo
import com.example.book_store_notes.model.db.CollectionDbRepoImpl
import com.example.book_store_notes.model.db.Constants.DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {

    @Provides
    fun providesApiRepo() = BookApiRepo(BookService.api)

    @Provides
    fun provideBookDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CollectionDB::class.java, DB).build()

    @Provides fun provideBookDao(collectionDB: CollectionDB): BookDao {
        return collectionDB.bookDao()
    }

    @Provides
    fun provideDbRepo(bookDao: BookDao): CollectionDbRepo{
        return CollectionDbRepoImpl(bookDao)
    }



}