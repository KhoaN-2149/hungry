package com.csci448.khoa_nguyen.hungry.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.csci448.khoa_nguyen.hungry.data.models.Restaurant
import com.csci448.khoa_nguyen.hungry.data.models.dummyRestaurants

@Composable
fun MapScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8EAE6))
    ) {
        // Fake watermark just so the team knows it's a placeholder
        Text(
            text = "Fake Map: Golden, CO",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.LightGray,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-100).dp)
        )


        dummyRestaurants.forEachIndexed { index, restaurant ->
            // Hardcoding some random offsets so they don't stack on top of each other
            val offsetX = (index * 80 - 60).dp
            val offsetY = (index * -100 + 150).dp

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(x = offsetX, y = offsetY)
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 4.dp
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = restaurant.name,
                        tint = Color(0xFFD32F2F),
                        modifier = Modifier
                            .padding(8.dp)
                            .size(32.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                // Little label under the pin
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = Color.White.copy(alpha = 0.9f),
                    modifier = Modifier.padding(2.dp)
                ) {
                    Text(
                        text = restaurant.name,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }

        // The horizontal scroll at the bottom
        LazyRow(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(dummyRestaurants) { restaurant ->
                SmallRestaurantCard(restaurant)
            }
        }
    }
}

// A smaller version of your card just for the map view
@Composable
fun SmallRestaurantCard(restaurant: Restaurant) {
    Card(
        modifier = Modifier
            .width(220.dp)
            .height(140.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Fake image block
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.LightGray)
            )
            // Bottom red banner
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFD32F2F))
                    .padding(12.dp)
            ) {
                Text(
                    text = restaurant.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Text(
                    text = "${restaurant.rating} Stars • ${restaurant.price}",
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}