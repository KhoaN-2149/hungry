package com.csci448.khoa_nguyen.hungry.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
// Make sure to import your screen files here!
import com.csci448.khoa_nguyen.hungry.ui.screens.*

@Composable
fun HungryNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    // We set startDestination to "hungry" so the SwipeScreen loads first
    NavHost(
        navController = navController,
        startDestination = Screen.Hungry.route,
        modifier = modifier
    ) {
        composable(Screen.Hungry.route) { SwipeScreen() }
        composable(Screen.Favorite.route) { FavoritesScreen() }
        composable(Screen.Map.route) { MapScreen() }
        composable(Screen.Friend.route) { FriendsScreen() }
        composable(Screen.Profile.route) { ProfileScreen() }
    }
}