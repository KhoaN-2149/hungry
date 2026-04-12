package com.csci448.khoa_nguyen.hungry.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.csci448.khoa_nguyen.hungry.ui.screens.*
import com.csci448.khoa_nguyen.hungry.ui.viewmodels.AuthViewModel
import com.csci448.khoa_nguyen.hungry.ui.viewmodels.AuthState
import com.csci448.khoa_nguyen.hungry.ui.viewmodels.HungryViewModel

@Composable
fun HungryNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    val hungryViewModel: HungryViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel() // Added our new ViewModel

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier
    ) {
        composable(Screen.Login.route) {
            val authState by authViewModel.authState.collectAsState()

            // If the user logs in successfully, immediately navigate them to the main app
            LaunchedEffect(authState) {
                if (authState is AuthState.Authenticated) {
                    navController.navigate(Screen.Hungry.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            }

            LoginScreen(
                authState = authState,
                onLoginClick = { email, pass -> authViewModel.login(email, pass) },
                onSignUpClick = { email, pass -> authViewModel.signUp(email, pass) },
                onGuestClick = {
                    navController.navigate(Screen.Hungry.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Hungry.route) {
            val restaurants by hungryViewModel.currentRestaurants.collectAsState()
            SwipeScreen(
                currentRestaurants = restaurants,
                onSwipeLeft = { hungryViewModel.swipeLeft() },
                onSwipeRight = { hungryViewModel.swipeRight(it) }
            )
        }

        composable(Screen.Favorite.route) {
            val favorites by hungryViewModel.favorites.collectAsState()
            FavoritesScreen(favorites = favorites)
        }

        composable(Screen.Map.route) { MapScreen() }
        composable(Screen.Friend.route) { FriendsScreen() }

        composable(Screen.Profile.route) {
            val isVeg by hungryViewModel.isVegetarian.collectAsState()
            val isSpicy by hungryViewModel.isSpicyOnly.collectAsState()
            val isGF by hungryViewModel.isGlutenFree.collectAsState()
            val authState by authViewModel.authState.collectAsState()
            val displayName = if (authState is AuthState.Guest) {
                "Guest"
            } else {
                authViewModel.currentUserEmail ?: "User"
            }
            ProfileScreen(
                username = displayName,
                isVegetarian = isVeg,
                isSpicyOnly = isSpicy,
                isGlutenFree = isGF,
                onVegetarianChanged = { hungryViewModel.updateVegetarian(it) },
                onSpicyOnlyChanged = { hungryViewModel.updateSpicyOnly(it) },
                onGlutenFreeChanged = { hungryViewModel.updateGlutenFree(it) },
                onLogout = {
                    // 3. Call the logout function you already have!
                    authViewModel.logout()

                    // 4. Send them back to Login and clear the history
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }}
            )
        }
    }
}