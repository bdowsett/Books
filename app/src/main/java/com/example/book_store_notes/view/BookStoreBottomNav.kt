package com.example.book_store_notes.view

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.book_store_notes.Destination
import com.example.book_store_notes.R

@Composable
fun BookStoreBottomNav(navController: NavHostController) {
    val selectedState = false
    NavigationBar(tonalElevation = 10.dp) {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry.value?.destination

        val iconLibrary = painterResource(id = R.drawable.ic_library)
        val iconCollection = painterResource(id = R.drawable.ic_collection)

        NavigationBarItem(
            selected = currentDestination?.route == Destination.Library.route,
            onClick = { navController.navigate(Destination.Library.route) {
                    popUpTo(Destination.Library.route)
                     launchSingleTop = true
            } },
            icon = {
                Icon(
                    painter = iconLibrary,
                    contentDescription = "library"
                )
            }, label = { Text( text = Destination.Library.route)})

        NavigationBarItem(
            selected = currentDestination?.route == Destination.Collection.route,
            onClick = { navController.navigate(Destination.Collection.route)},
            icon = {
                Icon(
                    painter = iconCollection,
                    contentDescription = "collection"
                )
            }, label = { Text( text = Destination.Collection.route)})
    }
}