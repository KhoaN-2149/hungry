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

// The main screen for swiping through the restaurant stack
@Composable
fun SwipeScreen(
    currentRestaurants: List<Restaurant>,
    onSwipeLeft: () -> Unit,
    onSwipeRight: (Restaurant) -> Unit
) {
    // Show a simple message if we've run out of restaurants to show
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
    val nextRestaurant = currentRestaurants.getOrNull(1)

    // Figure out how far the card needs to be pulled before it counts as a swipe
    val screenWidth = with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.toPx() }
    val swipeThreshold = screenWidth * 0.4f

    // Variables to track the card's movement
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    // Put the card back in the center every time the top restaurant changes
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
            // Show a preview of the next card behind the main one
            if (nextRestaurant != null) {
                Box(modifier = Modifier.graphicsLayer {
                    scaleX = 0.9f
                    scaleY = 0.9f
                    alpha = 0.6f
                }) {
                    RestaurantCard(restaurant = nextRestaurant, modifier = Modifier)
                }
            }

            // The interactive card with all the dragging and tilting logic
            Box(
                modifier = Modifier
                    .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
                    .graphicsLayer {
                        rotationZ = (offsetX.value / 60).coerceIn(-15f, 15f)
                    }
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragEnd = {
                                coroutineScope.launch {
                                    if (offsetX.value > swipeThreshold) {
                                        // Animate off-screen to the right
                                        offsetX.animateTo(screenWidth * 1.5f, tween(300))
                                        onSwipeRight(topRestaurant)
                                    } else if (offsetX.value < -swipeThreshold) {
                                        // Animate off-screen to the left
                                        offsetX.animateTo(-screenWidth * 1.5f, tween(300))
                                        onSwipeLeft()
                                    } else {
                                        // Snap back to the middle if the drag was too short
                                        launch { offsetX.animateTo(0f, tween(300)) }
                                        launch { offsetY.animateTo(0f, tween(300)) }
                                    }
                                }
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                coroutineScope.launch {
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

        // Action buttons at the bottom for people who prefer tapping over swiping
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