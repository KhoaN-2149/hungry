package com.csci448.khoa_nguyen.hungry.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage // Ensure this import is here
import com.csci448.khoa_nguyen.hungry.R
import com.csci448.khoa_nguyen.hungry.data.models.Restaurant
import com.csci448.khoa_nguyen.hungry.data.models.dummyRestaurants
import com.csci448.khoa_nguyen.hungry.ui.components.SmallRestaurantCard

@Composable
fun MapScreen() {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    // State to track which pin was clicked
    var selectedRestaurant by remember { mutableStateOf<Restaurant?>(null) }

    val animatedScale by animateFloatAsState(targetValue = scale, label = "zoom")
    val animatedOffset by animateOffsetAsState(targetValue = offset, label = "pan")

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .clip(RectangleShape)
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = (scale * zoom).coerceIn(1f, 5f)
                    offset += pan
                    // Dismiss overlay if user starts panning/zooming manually
                    if (pan != Offset.Zero || zoom != 1f) selectedRestaurant = null
                }
            }
    ) {
        val width = constraints.maxWidth.toFloat()
        val height = constraints.maxHeight.toFloat()

        val focusOnRestaurant: (Restaurant) -> Unit = { rest ->
            scale = 4f
            offset = Offset(
                x = (width / 2f) - (rest.xPct * width * scale),
                y = (height / 2f) - (rest.yPct * height * scale)
            )
            selectedRestaurant = rest // Trigger the popup card
        }

        // The Background Map Image
        Image(
            painter = painterResource(id = R.drawable.golden_map),
            contentDescription = "Golden CO Map",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = animatedScale,
                    scaleY = animatedScale,
                    translationX = animatedOffset.x,
                    translationY = animatedOffset.y
                )
        )

        // Draw Pins with Names
        dummyRestaurants.forEach { restaurant ->
            val xPos = restaurant.xPct * width
            val yPos = restaurant.yPct * height

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = (xPos * animatedScale + animatedOffset.x).toInt() - 60,
                            y = (yPos * animatedScale + animatedOffset.y).toInt() - 80
                        )
                    }
                    .width(120.dp)
            ) {
                IconButton(
                    onClick = { focusOnRestaurant(restaurant) },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = restaurant.name,
                        tint = if (selectedRestaurant == restaurant) Color.Blue else Color.Red,
                        modifier = Modifier.size(32.dp)
                    )
                }

                if (animatedScale > 1.5f) {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = Color.White.copy(alpha = 0.9f),
                        shadowElevation = 2.dp,
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Text(
                            text = restaurant.name,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            maxLines = 1
                        )
                    }
                }
            }
        }

        // Bottom Carousel for Quick Access
        LazyRow(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(dummyRestaurants) { restaurant ->
                Surface(
                    onClick = { focusOnRestaurant(restaurant) },
                    shape = RoundedCornerShape(16.dp),
                    color = Color.Transparent
                ) {
                    SmallRestaurantCard(restaurant)
                }
            }
        }

        // --- FIXED: Detail Popup Overlay with Image Support ---
        AnimatedVisibility(
            visible = selectedRestaurant != null,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it }),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 120.dp)
        ) {
            selectedRestaurant?.let { rest ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(16.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(12.dp)
                ) {
                    Column {
                        // Restaurant Image Section
                        AsyncImage(
                            model = rest.imageUrl,
                            contentDescription = "${rest.name} preview",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                                .background(Color.LightGray)
                        )

                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = rest.name,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = Color.Black
                                )
                                IconButton(onClick = { selectedRestaurant = null }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Close",
                                        tint = Color.Gray
                                    )
                                }
                            }

                            Text(
                                text = "Rating: ${rest.rating} ⭐ • ${rest.price}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.DarkGray
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Button(
                                onClick = { /* Logic for navigation to DetailScreen can go here */ },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                            ) {
                                Text("View Menu & Details", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}