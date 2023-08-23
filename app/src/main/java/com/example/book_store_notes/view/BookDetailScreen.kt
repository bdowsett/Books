package com.example.book_store_notes.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.book_store_notes.Destination
import com.example.book_store_notes.authorsToString
import com.example.book_store_notes.viewmodel.BooksApiViewModel


@Composable
fun BookDetailScreen(
    bvm: BooksApiViewModel,
    paddingValues: PaddingValues,
    navController: NavHostController
) {

    val book = bvm.bookDetails.value

    if (book == null)
        navController.navigate(Destination.Library.route) {
            popUpTo(Destination.Library.route)
            launchSingleTop = true
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .padding(bottom = paddingValues.calculateBottomPadding())
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (book == null) {
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator()
            }
        } else {
            val imageUrl = book.volumeInfo.imageLinks?.thumbnail
            val title = book.volumeInfo.title ?: "No title"
            val authors = book.volumeInfo.authors?.authorsToString() ?: ""
            val description = book.volumeInfo.description ?: ""

            BookImage(
                url = imageUrl,
                modifier = Modifier
                    .width(200.dp)
                    .padding(4.dp)
            )
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier.padding(4.dp)
            )
            Text(
                text = authors,
                fontStyle = FontStyle.Italic,
                fontSize = 20.sp,
                modifier = Modifier.padding(4.dp)
            )
            Text(text = description, fontSize = 16.sp, modifier = Modifier.padding(4.dp))

            Button(onClick = {
            }, modifier = Modifier.padding(bottom = 20.dp)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Text(text = "Add to collection")
                }
            }
        }
    }
}