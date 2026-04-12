package com.csci448.khoa_nguyen.hungry.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.csci448.khoa_nguyen.hungry.data.models.Restaurant
import com.csci448.khoa_nguyen.hungry.ui.components.RestaurantCard

@Composable
fun SwipeScreen(
    currentRestaurants: List<Restaurant>,
    onSwipeLeft: () -> Unit,
    onSwipeRight: (Restaurant) -> Unit
) {
    // 1. Handle the empty state when all restaurants have been swiped
    if (currentRestaurants.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No more restaurants nearby!",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Gray
            )
        }
        return
    }

    // 2. Grab the restaurant at the top of the stack
    val topRestaurant = currentRestaurants.first()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // The Card (taking up most of the space)
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            RestaurantCard(restaurant = topRestaurant)
        }

        // Temporary buttons to test the ViewModel logic until we build gesture swipes in Phase 3
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp, start = 32.dp, end = 32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Pass Button (Left Swipe)
            FloatingActionButton(
                onClick = onSwipeLeft,
                containerColor = Color.White,
                contentColor = Color.Black
            ) {
                Icon(Icons.Default.Close, contentDescription = "Pass", modifier = Modifier.size(32.dp))
            }

            // Favorite Button (Right Swipe)
            FloatingActionButton(
                onClick = { onSwipeRight(topRestaurant) },
                containerColor = Color.White,
                contentColor = Color(0xFFD32F2F) // HungryRed
            ) {
                Icon(Icons.Default.Favorite, contentDescription = "Favorite", modifier = Modifier.size(32.dp))
            }
        }
    }
}