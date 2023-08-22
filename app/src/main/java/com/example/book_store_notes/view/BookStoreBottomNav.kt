package com.example.book_store_notes.view

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.book_store_notes.R

@Composable
fun BookStoreBottomNav(navHostController: NavHostController){
    val selectedState = false
    NavigationBar(tonalElevation = 5.dp) {

        val iconLibrary = painterResource(id = R.drawable.ic_library)
        val iconCollection = painterResource(id = R.drawable.ic_collection)

        NavigationBarItem(selected = selectedState, onClick = { /*TODO*/ }, icon = { Icon(
            painter = iconLibrary,
            contentDescription = "library"
        ) })

        NavigationBarItem(selected = selectedState, onClick = { /*TODO*/ }, icon = { Icon(
            painter = iconCollection,
            contentDescription = "collection"
        ) })
    }
}