package com.csci448.khoa_nguyen.hungry.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

// This sets up the different pages in the app with their names and icons
sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Hungry : Screen("hungry", "Hungry", Icons.Filled.Restaurant)
    object Favorite : Screen("favorite", "Favorite", Icons.Filled.Favorite)
    object Map : Screen("map", "Map", Icons.Filled.Place)
    object Friend : Screen("friend", "Friend", Icons.Filled.People)
    object Profile : Screen("profile", "Profile", Icons.Filled.Person)
    object Login : Screen("login", "Login", Icons.Filled.Person)
}

// A list of the specific screens that should appear in the bottom menu
val bottomNavItems = listOf(
    Screen.Hungry,
    Screen.Favorite,
    Screen.Map,
    Screen.Friend,
    Screen.Profile
)