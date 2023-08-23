package com.example.book_store_notes

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.book_store_notes.ui.theme.Book_store_notesTheme
import com.example.book_store_notes.view.BookDetailScreen
import com.example.book_store_notes.view.BookStoreBottomNav
import com.example.book_store_notes.view.BookstoreScreen
import com.example.book_store_notes.view.CollectionScreen
import com.example.book_store_notes.viewmodel.BooksApiViewModel
import dagger.hilt.android.AndroidEntryPoint


sealed class Destination(val route: String) {
    object Library : Destination(route = "library")
    object Collection : Destination(route = "collection")
    object BookDetails : Destination("book/{bookId}") {
        fun createRoute(bookId: String?) = "book/$bookId"
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val booksViewModel by viewModels<BooksApiViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Book_store_notesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    
                    val navController = rememberNavController()
                    BookStoreScaffold(navController = navController, viewModel = booksViewModel)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookStoreScaffold(navController: NavHostController, viewModel: BooksApiViewModel) {

    val ctx = LocalContext.current


    Scaffold(bottomBar = { BookStoreBottomNav(navController = navController)}) { paddingValues ->
        NavHost(navController = navController, startDestination = Destination.Library.route) {
            composable(Destination.Library.route) {
                BookstoreScreen(navController = navController, vm = viewModel, paddingValues = paddingValues)
            }
            composable(Destination.Collection.route) {
                CollectionScreen()
            }
            composable(Destination.BookDetails.route) {navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getString("bookId")
                if (id == null)
                    Toast.makeText(ctx, "Book id is required", Toast.LENGTH_SHORT ).show()
                else {
                    viewModel.getSingleBook(id)
                    BookDetailScreen(bvm = viewModel, paddingValues = paddingValues, navController = navController)
                }
            }
        }
    }
}


