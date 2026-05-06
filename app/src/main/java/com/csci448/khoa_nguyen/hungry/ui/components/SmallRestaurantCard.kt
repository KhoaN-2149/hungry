package com.csci448.khoa_nguyen.hungry.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage // Crucial import
import com.csci448.khoa_nguyen.hungry.data.models.Restaurant

@Composable
fun SmallRestaurantCard(restaurant: Restaurant) {
    Card(
        modifier = Modifier
            .width(220.dp)
            .height(150.dp), // Slightly increased height to prevent text crowding
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // FIXED: Replaced the Box with AsyncImage
            AsyncImage(
                model = restaurant.imageUrl,
                contentDescription = "${restaurant.name} image",
                contentScale = ContentScale.Crop, // Fills the width perfectly
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Takes up remaining space
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(Color.LightGray)
            )

            // Info Bar (HungryRed)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFD32F2F)) // HungryRed
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = restaurant.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis // Adds "..." if name is too long
                )
                Text(
                    text = "${restaurant.rating} Stars • ${restaurant.price}",
                    color = Color.White.copy(alpha = 0.9f),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}