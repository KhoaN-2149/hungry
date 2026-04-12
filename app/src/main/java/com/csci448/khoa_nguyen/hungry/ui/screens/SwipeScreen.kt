package com.csci448.khoa_nguyen.hungry.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.csci448.khoa_nguyen.hungry.data.models.Restaurant
import com.csci448.khoa_nguyen.hungry.ui.components.RestaurantCard
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SwipeScreen(
    currentRestaurants: List<Restaurant>,
    onSwipeLeft: () -> Unit,
    onSwipeRight: (Restaurant) -> Unit
) {
    if (currentRestaurants.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "No more restaurants nearby!",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Gray
            )
        }
        return
    }

    val topRestaurant = currentRestaurants.first()
    // Grab the next restaurant to create a "stack" visual effect behind the top card
    val nextRestaurant = currentRestaurants.getOrNull(1)

    // Calculate how far the user needs to drag to trigger a swipe (40% of screen width)
    val screenWidth = with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.toPx() }
    val swipeThreshold = screenWidth * 0.4f

    // These hold the current X and Y position of the card being dragged
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    // Whenever the top restaurant changes (i.e., we finished a swipe), instantly reset the card back to center
    LaunchedEffect(topRestaurant) {
        offsetX.snapTo(0f)
        offsetY.snapTo(0f)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.weight(1f).padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            // Draw the next card BEHIND the top card, scaled down slightly
            if (nextRestaurant != null) {
                Box(modifier = Modifier.graphicsLayer {
                    scaleX = 0.9f
                    scaleY = 0.9f
                    alpha = 0.6f
                }) {
                    // Make sure we pass the modifier so the padding doesn't double up
                    RestaurantCard(restaurant = nextRestaurant, modifier = Modifier)
                }
            }

            // Draw the TOP card with gesture detection
            Box(
                modifier = Modifier
                    .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
                    .graphicsLayer {
                        // Tilt the card slightly as it is dragged left or right
                        rotationZ = (offsetX.value / 60).coerceIn(-15f, 15f)
                    }
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragEnd = {
                                coroutineScope.launch {
                                    if (offsetX.value > swipeThreshold) {
                                        // User dragged far enough right -> Swipe Right
                                        offsetX.animateTo(screenWidth * 1.5f, tween(300))
                                        onSwipeRight(topRestaurant)
                                    } else if (offsetX.value < -swipeThreshold) {
                                        // User dragged far enough left -> Swipe Left
                                        offsetX.animateTo(-screenWidth * 1.5f, tween(300))
                                        onSwipeLeft()
                                    } else {
                                        // Didn't drag far enough -> Snap back to center
                                        launch { offsetX.animateTo(0f, tween(300)) }
                                        launch { offsetY.animateTo(0f, tween(300)) }
                                    }
                                }
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                coroutineScope.launch {
                                    // Update the offset as the finger moves
                                    offsetX.snapTo(offsetX.value + dragAmount.x)
                                    offsetY.snapTo(offsetY.value + dragAmount.y)
                                }
                            }
                        )
                    }
            ) {
                RestaurantCard(restaurant = topRestaurant, modifier = Modifier)
            }
        }

        // We kept the buttons so users can tap OR swipe!
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp, start = 32.dp, end = 32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        offsetX.animateTo(-screenWidth * 1.5f, tween(300))
                        onSwipeLeft()
                    }
                },
                containerColor = Color.White,
                contentColor = Color.Black
            ) {
                Icon(Icons.Default.Close, contentDescription = "Pass", modifier = Modifier.size(32.dp))
            }

            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        offsetX.animateTo(screenWidth * 1.5f, tween(300))
                        onSwipeRight(topRestaurant)
                    }
                },
                containerColor = Color.White,
                contentColor = Color(0xFFD32F2F)
            ) {
                Icon(Icons.Default.Favorite, contentDescription = "Favorite", modifier = Modifier.size(32.dp))
            }
        }
    }
}