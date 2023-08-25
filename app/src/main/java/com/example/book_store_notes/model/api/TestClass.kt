package com.example.book_store_notes.model.api

import kotlinx.coroutines.flow.MutableStateFlow

class TestClass {
    fun updateNumber() {
        monies.value ++
    }

    val monies = MutableStateFlow<Int>(11)
}