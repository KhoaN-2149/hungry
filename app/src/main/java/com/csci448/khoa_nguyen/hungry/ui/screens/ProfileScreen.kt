package com.csci448.khoa_nguyen.hungry.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val HungryRed = Color(0xFFE53935)      // Main primary red
private val HungryOnRed = Color.White           // Text on top of red
private val HungryRedVariant = Color(0xFFFFEBEE) // Very light pink-red
private val HungryDarkRed = Color(0xFFB71C1C)  // Darker text
private val HungrySurface = Color.White        // Main background
private val HungryOnSurface = Color(0xFF212121)// Main text color

private val hungryLightColorScheme = lightColorScheme(
    primary = HungryRed,
    onPrimary = HungryOnRed,
    primaryContainer = HungryRedVariant,
    onPrimaryContainer = HungryDarkRed,
    surface = HungrySurface,
    onSurface = HungryOnSurface,
    surfaceVariant = HungryRedVariant,
    onSurfaceVariant = HungryDarkRed,
    background = HungrySurface,
    onBackground = HungryOnSurface
)

@Composable
fun ProfileScreen() {
    var isVegetarian by remember { mutableStateOf(false) }
    var isSpicyOnly by remember { mutableStateOf(true) }
    var isGlutenFree by remember { mutableStateOf(false) }

    MaterialTheme(colorScheme = hungryLightColorScheme) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            // Avatar Image
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Dummy Profile Picture",
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Username & Stuff
            Text(
                text = "HungryFatHippo67",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "Foodie Level: Expert",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Dietary Preferences
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Dietary Preferences",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Reusable Toggle Components
                    PreferenceToggle(
                        label = "Vegetarian",
                        isChecked = isVegetarian,
                        onCheckedChange = { isVegetarian = it }
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f) // Light red divider
                    )

                    PreferenceToggle(
                        label = "Spicy Only",
                        isChecked = isSpicyOnly,
                        onCheckedChange = { isSpicyOnly = it }
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
                    )

                    PreferenceToggle(
                        label = "Gluten Free",
                        isChecked = isGlutenFree,
                        onCheckedChange = { isGlutenFree = it }
                    )
                }
            }
        }
    }
}

@Composable
fun PreferenceToggle(
    label: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant // Dark Red
        )
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Hungry Profile Screen")
@Composable
fun ProfileScreenPreview() {
    MaterialTheme(colorScheme = hungryLightColorScheme) {
        ProfileScreen()
    }
}