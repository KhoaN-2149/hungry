package com.csci448.khoa_nguyen.hungry.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.csci448.khoa_nguyen.hungry.ui.screens.*
import com.csci448.khoa_nguyen.hungry.ui.viewmodels.HungryViewModel

@Composable
fun HungryNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    // Create the ViewModel and keep it alive as long as the NavGraph is alive
    val viewModel: HungryViewModel = viewModel()

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
        composable(Screen.Hungry.route) {
            // Collect the restaurant list
            val restaurants by viewModel.currentRestaurants.collectAsState()

            SwipeScreen(
                currentRestaurants = restaurants,
                onSwipeLeft = { viewModel.swipeLeft() },
                onSwipeRight = { viewModel.swipeRight(it) }
            )
        }
        composable(Screen.Favorite.route) {
            // Collect the user's favorites list
            val favorites by viewModel.favorites.collectAsState()

            FavoritesScreen(favorites = favorites)
        }
        composable(Screen.Map.route) { MapScreen() }
        composable(Screen.Friend.route) { FriendsScreen() }
        composable(Screen.Profile.route) {

            // Collect the profile state
            val isVeg by viewModel.isVegetarian.collectAsState()
            val isSpicy by viewModel.isSpicyOnly.collectAsState()
            val isGF by viewModel.isGlutenFree.collectAsState()

            ProfileScreen(
                isVegetarian = isVeg,
                isSpicyOnly = isSpicy,
                isGlutenFree = isGF,
                onVegetarianChanged = { viewModel.updateVegetarian(it) },
                onSpicyOnlyChanged = { viewModel.updateSpicyOnly(it) },
                onGlutenFreeChanged = { viewModel.updateGlutenFree(it) }
            )
        }
    }
}