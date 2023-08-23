package com.example.book_store_notes

import com.example.book_store_notes.model.api.BookApi
import com.example.book_store_notes.model.api.BookApiRepo
import com.example.book_store_notes.model.api.BookService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {

    @Provides
    fun providesApiRepo() = BookApiRepo(BookService.api)

}