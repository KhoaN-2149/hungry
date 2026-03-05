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

@Composable
fun HungryApp() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                // Loop through our list of screens to build the tabs
                bottomNavItems.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Keeps the backstack clean so you don't build up a massive history of clicks
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
    ) { innerPadding ->
        // innerPadding makes sure our screens don't draw underneath the bottom bar!
        HungryNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}