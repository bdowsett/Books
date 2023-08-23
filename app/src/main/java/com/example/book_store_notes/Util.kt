package com.example.book_store_notes

import retrofit2.http.Query

fun validateQuery(query: String) = query.length > 2

fun List<String>.authorsToString() = this.joinToString(separator = ", ")