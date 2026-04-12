package com.csci448.khoa_nguyen.hungry.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.csci448.khoa_nguyen.hungry.data.models.Restaurant

@Composable
fun SmallRestaurantCard(restaurant: Restaurant) {
    Card(
        modifier = Modifier.width(220.dp).height(140.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxWidth().weight(1f).background(Color.LightGray))
            Column(
                modifier = Modifier.fillMaxWidth().background(Color(0xFFD32F2F)).padding(12.dp)
            ) {
                Text(text = restaurant.name, color = Color.White, fontWeight = FontWeight.Bold, maxLines = 1)
                Text(text = "${restaurant.rating} Stars • ${restaurant.price}", color = Color.White, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}