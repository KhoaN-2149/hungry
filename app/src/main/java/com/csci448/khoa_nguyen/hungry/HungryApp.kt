package com.csci448.khoa_nguyen.hungry

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.csci448.khoa_nguyen.hungry.ui.navigation.Screen
import com.csci448.khoa_nguyen.hungry.ui.navigation.bottomNavItems
import com.csci448.khoa_nguyen.hungry.ui.navigation.HungryNavGraph

// This is the main shell of the app that holds the navigation and the bottom bar
@Composable
fun HungryApp() {
    val navController = rememberNavController()

    // Keep track of the current screen to decide what to show
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            // Only show the bottom menu if the user isn't on the login screen
            if (currentRoute != Screen.Login.route) {
                NavigationBar {
                    val currentDestination = navBackStackEntry?.destination

                    // Create a button for every item in our bottom nav list
                    bottomNavItems.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = screen.title) },
                            label = { Text(screen.title) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        // This is where the actual screen content is displayed
        HungryNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}