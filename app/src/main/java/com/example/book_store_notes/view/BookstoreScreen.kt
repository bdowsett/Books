package com.example.book_store_notes.view


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.book_store_notes.Destination
import com.example.book_store_notes.authorsToString
import com.example.book_store_notes.connectivity.ConnectivityObservable
import com.example.book_store_notes.model.GoogleBooksApiResponse
import com.example.book_store_notes.model.api.NetworkResult
import com.example.book_store_notes.viewmodel.BooksApiViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookstoreScreen(
    navController: NavHostController,
    vm: BooksApiViewModel,
    paddingValues: PaddingValues
) {

    val result by vm.result.collectAsState()
    val text = vm.queryText.collectAsState()
    val networkAvailable = vm.networkAvailable.observe().collectAsState(initial = ConnectivityObservable.Status.Available)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = paddingValues.calculateBottomPadding()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if(networkAvailable.value == ConnectivityObservable.Status.Unavailable)
            Row(modifier = Modifier.fillMaxWidth().background(Color.Red), horizontalArrangement = Arrangement.Center){
                Text(text = "Network Unavailable",
                    fontWeight = FontWeight.Bold, color = Color.White,
                modifier = Modifier.padding(16.dp))
            }
        OutlinedTextField(
            value = text.value,
            onValueChange = vm::onQueryInput,
            label = { Text(text = "Book search") },
            placeholder = { Text(text = "Kotlin") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (result) {
                is NetworkResult.Initial -> {
                    Text(text = "Search for books")
                }

                is NetworkResult.Success -> {
                    ShowBooksList(result, navController)
                }

                is NetworkResult.Loading -> {
                    CircularProgressIndicator()
                }

                is NetworkResult.Error -> {
                    Text(text = "Error: ${result.message}")
                }
            }
        }
    }
}

@Composable
fun ShowBooksList(result: NetworkResult<GoogleBooksApiResponse>, navController: NavHostController) {
    result.data?.items?.let { volumes ->
        LazyColumn(modifier = Modifier.background(Color.LightGray)) {
            items(volumes) { volume ->

                val imageUrl = volume.volumeInfo.imageLinks?.smallThumbnail
                val title = volume.volumeInfo.title ?: "No title"
                val authors = volume.volumeInfo.authors?.authorsToString()
                val description = volume.volumeInfo.description
                val context = LocalContext.current

                Column(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.White)
                        .padding(4.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable {
                            if (volume.id != null) {
                                navController.navigate(
                                    Destination.BookDetails.createRoute(
                                        volume.id,
                                        "chunk"
                                    )
                                )
                            } else
                                Toast
                                    .makeText(context, "Book id is null", Toast.LENGTH_SHORT)
                                    .show()
                        }
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        BookImage(
                            url = imageUrl, modifier = Modifier
                                .padding(4.dp)
                                .width(100.dp)
                        )

                        Column(modifier = Modifier.padding(4.dp)) {
                            Text(
                                text = title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            Text(
                                text = authors ?: "",
                                fontStyle = FontStyle.Italic,
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    Text(text = description ?: "", maxLines = 4, fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
fun BookImage(
    url: String?,
    modifier: Modifier,
    contentScale: ContentScale = ContentScale.FillWidth
) {
    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = modifier,
        contentScale = contentScale
    )
}
