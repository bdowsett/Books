package com.example.book_store_notes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.book_store_notes.model.api.BookApiRepo
import com.example.book_store_notes.validateQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksApiViewModel @Inject constructor(private val repo: BookApiRepo): ViewModel() {

    val result = repo.books

    val queryText: MutableStateFlow<String> = MutableStateFlow("")
    //private val queryInput = Channel<String>(Channel.CONFLATED)
    private val queryInput = MutableStateFlow<String>("")

    val bookDetails = repo.bookDetails

    init{
        performQuery()
    }

    private fun performQuery(){
        viewModelScope.launch(Dispatchers.IO) {

            queryInput.filter {
                validateQuery(it) }.debounce(1000)
                .collect {
                    repo.query(it)
                }

        }
    }

    fun onQueryInput(input: String){
        queryText.value = input
        queryInput.value = input
    }

    fun getSingleBook(id:String) {
        repo.getBookDetails(id)
    }
}