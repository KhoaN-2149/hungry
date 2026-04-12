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
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

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
        }

        Image(
            painter = painterResource(id = R.drawable.golden_map),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize().graphicsLayer(
                scaleX = animatedScale, scaleY = animatedScale,
                translationX = animatedOffset.x, translationY = animatedOffset.y
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
                            x = (xPos * animatedScale + animatedOffset.x).toInt() - 60, // Widened to center label
                            y = (yPos * animatedScale + animatedOffset.y).toInt() - 80
                        )
                    }
                    .width(120.dp) // Fixed width helps centering the text under the icon
            ) {
                IconButton(
                    onClick = { focusOnRestaurant(restaurant) },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = restaurant.name,
                        tint = Color.Red,
                        modifier = Modifier.size(32.dp)
                    )
                }

                // Show name label
                // Added a small alpha check so they fade in/out based on zoom
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

        // Bottom List
        LazyRow(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 20.dp),
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
    }
}