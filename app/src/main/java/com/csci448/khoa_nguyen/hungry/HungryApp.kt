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

    // Find out exactly what screen we are currently looking at
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            // THE TRICK: Only draw the NavigationBar if we are NOT on the Login screen
            if (currentRoute != Screen.Login.route) {
                NavigationBar {
                    val currentDestination = navBackStackEntry?.destination

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
        HungryNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}