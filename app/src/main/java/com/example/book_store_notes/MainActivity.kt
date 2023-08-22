package com.example.book_store_notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.book_store_notes.ui.theme.Book_store_notesTheme
import com.example.book_store_notes.view.BookStoreBottomNav
import com.example.book_store_notes.view.BookstoreScreen
import com.example.book_store_notes.view.CollectionScreen


sealed class Destination(val route: String) {
    object Library : Destination(route = "library")
    object Collection : Destination(route = "collection")
    object BookDetails : Destination("book/{bookId}") {
        fun createRoute(bookId: String?) = "book/$bookId"
    }
}


class MainActivity : ComponentActivity() {
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
                    BookStoreScaffold(navController = navController)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookStoreScaffold(navController: NavHostController) {

    Scaffold(bottomBar = { BookStoreBottomNav(navHostController = navController)}) { paddingValues ->
        NavHost(navController = navController, startDestination = Destination.Library.route) {
            composable(Destination.Library.route) {
                BookstoreScreen()
            }
            composable(Destination.Collection.route) {
                CollectionScreen()
            }
            composable(Destination.BookDetails.route) {

            }
        }
    }
}


