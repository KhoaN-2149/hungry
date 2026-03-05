package com.csci448.khoa_nguyen.hungry.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.csci448.khoa_nguyen.hungry.ui.screens.*

@Composable
fun HungryNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginClick = {
                    navController.navigate(Screen.Hungry.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Hungry.route) { SwipeScreen() }
        composable(Screen.Favorite.route) { FavoritesScreen() }
        composable(Screen.Map.route) { MapScreen() }
        composable(Screen.Friend.route) { FriendsScreen() }
        composable(Screen.Profile.route) { ProfileScreen() }
    }
}