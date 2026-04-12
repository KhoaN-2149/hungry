package com.csci448.khoa_nguyen.hungry.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.csci448.khoa_nguyen.hungry.R
import com.csci448.khoa_nguyen.hungry.data.models.Restaurant
import com.csci448.khoa_nguyen.hungry.data.models.dummyRestaurants
import com.csci448.khoa_nguyen.hungry.ui.components.SmallRestaurantCard

@Composable
fun MapScreen() {
    // 1. State for Zoom and Panning
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    // Smooth animations for when we click a restaurant
    val animatedScale by animateFloatAsState(targetValue = scale, label = "zoom")
    val animatedOffset by animateOffsetAsState(targetValue = offset, label = "pan")

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8EAE6))
            .clip(RectangleShape)
            // 2. Add Pinch-to-Zoom and Dragging logic
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = (scale * zoom).coerceIn(1f, 5f)
                    offset += pan
                }
            }
    ) {
        val width = constraints.maxWidth.toFloat()
        val height = constraints.maxHeight.toFloat()

        // 3. The Actual Map Image (Replace R.drawable.golden_map with your actual resource name)
        Image(
            painter = painterResource(id = R.drawable.golden_map),
            contentDescription = "Golden Map Background",
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

        // 4. Floating Pins
        dummyRestaurants.forEach { restaurant ->
            // Use the percentages from your data model
            val xPos = restaurant.xPct * width
            val yPos = restaurant.yPct * height

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = (xPos * animatedScale + animatedOffset.x).toInt() - 50, // Center the pin
                            y = (yPos * animatedScale + animatedOffset.y).toInt() - 100 // Offset so point touches spot
                        )
                    }
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 4.dp,
                    onClick = {
                        // 5. CLICK LOGIC: Zoom in and center on the pin
                        scale = 3.5f
                        offset = Offset(
                            x = (width / 2f) - (xPos * scale),
                            y = (height / 2f) - (yPos * scale)
                        )
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = restaurant.name,
                        tint = Color(0xFFD32F2F),
                        modifier = Modifier.padding(8.dp).size(32.dp)
                    )
                }
                // Label that stays with the pin
                Text(
                    text = restaurant.name,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(Color.White.copy(alpha = 0.8f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 4.dp)
                )
            }
        }

        // 6. Keep your bottom list for navigation
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

        // Reset Button to go back to full view
        IconButton(
            onClick = { scale = 1f; offset = Offset.Zero },
            modifier = Modifier.align(Alignment.TopEnd).padding(16.dp).background(Color.White, CircleShape)
        ) {
            Icon(Icons.Default.LocationOn, contentDescription = "Reset", tint = Color.Gray)
        }
    }
}