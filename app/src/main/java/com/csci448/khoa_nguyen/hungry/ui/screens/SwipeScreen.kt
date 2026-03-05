package com.csci448.khoa_nguyen.hungry.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.csci448.khoa_nguyen.hungry.data.models.dummyRestaurants
import com.csci448.khoa_nguyen.hungry.ui.components.RestaurantCard

@Composable
fun SwipeScreen() {
    // For now, we just grab the first restaurant in our dummy list
    val currentRestaurant = dummyRestaurants.first()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        RestaurantCard(restaurant = currentRestaurant)
    }
}