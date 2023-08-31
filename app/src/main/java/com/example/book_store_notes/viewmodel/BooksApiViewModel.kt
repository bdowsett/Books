package com.example.book_store_notes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.book_store_notes.connectivity.ConnectivityMonitor
import com.example.book_store_notes.model.api.BookApiRepo
import com.example.book_store_notes.validateQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksApiViewModel @Inject constructor(private val repo: BookApiRepo, connectivityMonitor: ConnectivityMonitor): ViewModel() {

    val result = repo.books


    val queryText: MutableStateFlow<String> = MutableStateFlow("")
    private val queryInput = Channel<String>(Channel.CONFLATED)
    //private val queryInput = MutableStateFlow<String>("")
    val networkAvailable = connectivityMonitor

    val bookDetails = repo.bookDetails

    init{
        performQuery()
    }

    private fun performQuery(){
        viewModelScope.launch(Dispatchers.IO) {

            queryInput.consumeAsFlow().filter {
                validateQuery(it) }.debounce(1000)
                .collect {
                    repo.query(it)
                }

        }
    }

    fun onQueryInput(input: String){
        queryText.value = input
        queryInput.trySend(input)
    }

    fun getSingleBook(id:String) {
        repo.getBookDetails(id)
    }

   }