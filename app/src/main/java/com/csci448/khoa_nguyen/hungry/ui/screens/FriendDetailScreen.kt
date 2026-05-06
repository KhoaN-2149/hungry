package com.csci448.khoa_nguyen.hungry.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.csci448.khoa_nguyen.hungry.data.models.Restaurant
import com.csci448.khoa_nguyen.hungry.ui.components.RestaurantCard

// The screen that shows a friend's saved restaurants and any shared matches you have
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendDetailScreen(
    friendFavorites: List<Restaurant>,
    mutualFavorites: List<Restaurant>,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            // Setting up the header with the red theme and a back arrow
            TopAppBar(
                title = { Text("Friend's Favorites") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD32F2F),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Show the restaurants both people liked first
            if (mutualFavorites.isNotEmpty()) {
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Favorite, contentDescription = null, tint = Color.Red)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Mutual Favorites (${mutualFavorites.size})",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
                items(mutualFavorites) { restaurant ->
                    RestaurantCard(restaurant = restaurant)
                }
                item { HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp)) }
            }

            // A header for all the other spots they have saved
            item {
                Text(
                    text = "All Saved Spots",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }

            // Let the user know if the friend hasn't saved anything yet
            if (friendFavorites.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) {
                        Text("This user hasn't favorited anything yet!", color = Color.Gray)
                    }
                }
            } else {
                items(friendFavorites) { restaurant ->
                    RestaurantCard(restaurant = restaurant)
                }
            }
        }
    }
}