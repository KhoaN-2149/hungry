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
import com.csci448.khoa_nguyen.hungry.ui.screens.FriendDetailScreen

// This sets up all the different screens and how to move between them
@Composable
fun HungryNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    val hungryViewModel: HungryViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier
    ) {
        // The first screen where users sign in
        composable(Screen.Login.route) {
            val authState by authViewModel.authState.collectAsState()

            // If the user is already logged in, skip the login screen
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

        // The main swiping screen for finding food
        composable(Screen.Hungry.route) {
            val restaurants by hungryViewModel.currentRestaurants.collectAsState()
            SwipeScreen(
                currentRestaurants = restaurants,
                onSwipeLeft = { hungryViewModel.swipeLeft() },
                onSwipeRight = { hungryViewModel.swipeRight(it) }
            )
        }

        // A list of all the places the user has liked
        composable(Screen.Favorite.route) {
            val favorites by hungryViewModel.favorites.collectAsState()
            FavoritesScreen(favorites = favorites)
        }

        // The map view for seeing restaurant locations
        composable(Screen.Map.route) { MapScreen() }

        // Handles searching for people and managing friend requests
        composable(Screen.Friend.route) {
            val usersList by hungryViewModel.allUsers.collectAsState()
            val pendingRequests by hungryViewModel.pendingRequests.collectAsState()
            val myFriends by hungryViewModel.myFriends.collectAsState()

            FriendsScreen(
                usersList = usersList,
                myFriends = myFriends,
                pendingRequests = pendingRequests,
                onAddFriendClick = { targetUser ->
                    hungryViewModel.sendFriendRequest(targetUser)
                },
                onAcceptRequest = { requestUid ->
                    hungryViewModel.acceptFriendRequest(requestUid)
                },
                onDenyRequest = { requestUid ->
                    hungryViewModel.denyFriendRequest(requestUid)
                },
                onFriendClick = { friend ->
                    hungryViewModel.loadFriendData(friend.uid)
                    navController.navigate("friend_detail")
                }
            )
        }

        // User settings, dietary preferences, and the logout button
        composable(Screen.Profile.route) {
            val isVeg by hungryViewModel.isVegetarian.collectAsState()
            val isSpicy by hungryViewModel.isSpicyOnly.collectAsState()
            val isGF by hungryViewModel.isGlutenFree.collectAsState()
            val currentBio by hungryViewModel.userBio.collectAsState()

            val email = authViewModel.currentUserEmail
            val displayName = email?.substringBefore("@") ?: "Guest"

            ProfileScreen(
                username = displayName,
                currentBio = currentBio,
                isVegetarian = isVeg,
                isSpicyOnly = isSpicy,
                isGlutenFree = isGF,
                onVegetarianChanged = { hungryViewModel.updateVegetarian(it) },
                onSpicyOnlyChanged = { hungryViewModel.updateSpicyOnly(it) },
                onGlutenFreeChanged = { hungryViewModel.updateGlutenFree(it) },
                onUpdateBio = { newBio ->
                    hungryViewModel.updateBio(newBio)
                },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // Shows specific favorites and shared matches with a friend
        composable("friend_detail") {
            val friendFaves by hungryViewModel.friendFavorites.collectAsState()
            val mutualFaves by hungryViewModel.mutualFavorites.collectAsState()

            FriendDetailScreen(
                friendFavorites = friendFaves,
                mutualFavorites = mutualFaves,
                onBack = { navController.popBackStack() }
            )
        }
    }
}