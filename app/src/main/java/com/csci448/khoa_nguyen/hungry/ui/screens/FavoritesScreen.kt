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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val HungryRed = Color(0xFFD32F2F)
private val CardBackground = Color(0xFFF5F5F5)
private val TextPrimary = Color(0xFF1A1A1A)
private val TextSecondary = Color(0xFF757575)
private val StarYellow = Color(0xFFFFC107)
private val PlaceholderGray = Color(0xFFCCCCCC)
private val PlaceholderIconGray = Color(0xFF9E9E9E)

// Data model for a favorited restaurant
// TODO: Replace with Viewmodel and database once made
data class FavoriteRestaurant(
    val id: Int,
    val name: String,
    val cuisine: String,
    val rating: Float,
    val priceRange: String,
    // TODO: Replace imageAsset with actual restaurant image
    val imageAsset: String = ""
)

// Main Favorites Screen
// List of restaurants the user has favorited.
// Pass emptyList() to see the empty state.
// TODO when implementing backend,
//  Replace the `favorites` parameter with a list from ViewModel:
@Composable
fun FavoritesScreen(
    favorites: List<FavoriteRestaurant> = emptyList()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // Top bar
        FavoritesTopBar()

        // empty state OR list
        if (favorites.isEmpty()) {
            EmptyFavoritesState()
        } else {
            FavoritesList(favorites = favorites)
        }
    }
}

// Top bar
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

    // Thin red divider under the header
    HorizontalDivider(
        thickness = 2.dp,
        color = HungryRed.copy(alpha = 0.15f)
    )
}

// Empty state
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
            // Heart icon :)))
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

// Favorites list
@Composable
private fun FavoritesList(favorites: List<FavoriteRestaurant>) {
    // Count label ("3 restaurants")
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

        // Bottom padding so last card isn't hidden by the bottom nav bar
        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

// Individual favorite card
/**
 * TODO
 *   Replace the placeholder Box with:
 *     AsyncImage(
 *         model = "file:///android_asset/${restaurant.imageAsset}",
 *         contentDescription = restaurant.name,
 *         contentScale = ContentScale.Crop,
 *         modifier = Modifier.fillMaxWidth().height(180.dp)
 *     )
 */
@Composable
private fun FavoriteCard(restaurant: FavoriteRestaurant) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {

            // Photo placeholder
            // TODO: Replace this Box with AsyncImage (see comment above)
            //   once we get restaurant images
            // TODO: Get restaurant images and add them in Assets (Do another day)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(PlaceholderGray),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "🏔",
                    fontSize = 40.sp,
                    color = PlaceholderIconGray
                )
            }

            // Red info strip
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
                        // Star + rating
                        Text(text = "★", fontSize = 16.sp, color = StarYellow)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${restaurant.rating}",
                            fontSize = 15.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        // Price range on the right
                        Text(
                            text = restaurant.priceRange,
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

// Preview showing Favorite Screen with no restaurants favorite (What new users see)


@Preview(showBackground = true, name = "Empty State")
@Composable
fun FavoritesScreenEmptyPreview() {
    FavoritesScreen(favorites = emptyList())
}

// Preview showing Favorite Screen with some restaurants favorite

@Preview(showBackground = true, name = "With Favorites", showSystemUi = true)
@Composable
fun FavoritesScreenWithDataPreview() {
    FavoritesScreen(
        favorites = listOf(
            FavoriteRestaurant(
                id = 1,
                name = "McDonald's",
                cuisine = "Fast Food",
                rating = 3.8f,
                priceRange = "$"
            ),
            FavoriteRestaurant(
                id = 2,
                name = "Taco Bell",
                cuisine = "Mexican Fast Food",
                rating = 3.9f,
                priceRange = "$"
            ),
            FavoriteRestaurant(
                id = 3,
                name = "Nobu",
                cuisine = "Japanese · Sushi",
                rating = 4.8f,
                priceRange = "$$$"
            )
        )
    )
}