package com.csci448.khoa_nguyen.hungry.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage // Ensure this is imported
import com.csci448.khoa_nguyen.hungry.data.models.Restaurant
import com.csci448.khoa_nguyen.hungry.data.models.dummyRestaurants

private val HungryRed = Color(0xFFD32F2F)
private val TextPrimary = Color(0xFF1A1A1A)
private val TextSecondary = Color(0xFF757575)
private val StarYellow = Color(0xFFFFC107)
private val PlaceholderGray = Color(0xFFCCCCCC)

@Composable
fun FavoritesScreen(
    favorites: List<Restaurant> = emptyList()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        FavoritesTopBar()

        if (favorites.isEmpty()) {
            EmptyFavoritesState()
        } else {
            FavoritesList(favorites = favorites)
        }
    }
}

@Composable
private fun FavoritesTopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 18.dp)
    ) {
        Text(
            text = "Favorites",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
    }

    HorizontalDivider(thickness = 2.dp, color = HungryRed.copy(alpha = 0.15f))
}

@Composable
private fun EmptyFavoritesState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 40.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        color = HungryRed.copy(alpha = 0.08f),
                        shape = RoundedCornerShape(40.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "♡", fontSize = 36.sp, color = HungryRed)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Discover Your\nFavorite Restaurants",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                textAlign = TextAlign.Center,
                lineHeight = 30.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Swipe right on restaurants you love\nand they'll show up here.",
                fontSize = 15.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )
        }
    }
}

@Composable
private fun FavoritesList(favorites: List<Restaurant>) {
    Text(
        text = "${favorites.size} restaurant${if (favorites.size != 1) "s" else ""}",
        fontSize = 13.sp,
        color = TextSecondary,
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(favorites) { _, restaurant ->
            FavoriteCard(restaurant = restaurant)
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

@Composable
private fun FavoriteCard(restaurant: Restaurant) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            // FIXED: Replaced the Box with AsyncImage
            AsyncImage(
                model = restaurant.imageUrl,
                contentDescription = "${restaurant.name} image",
                contentScale = ContentScale.Crop, // Crop ensures the food fills the frame
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(PlaceholderGray)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(HungryRed)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Column {
                    Text(
                        text = restaurant.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "★", fontSize = 16.sp, color = StarYellow)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${restaurant.rating}",
                            fontSize = 15.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = restaurant.price,
                            fontSize = 15.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Empty State")
@Composable
fun FavoritesScreenEmptyPreview() {
    FavoritesScreen(favorites = emptyList())
}

@Preview(showBackground = true, name = "With Favorites", showSystemUi = true)
@Composable
fun FavoritesScreenWithDataPreview() {
    FavoritesScreen(favorites = dummyRestaurants.take(2))
}