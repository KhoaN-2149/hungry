package com.csci448.khoa_nguyen.hungry.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.csci448.khoa_nguyen.hungry.ui.theme.HungryTheme

@Composable
fun ProfileScreen(
    username: String,               // Add this
    isVegetarian: Boolean,
    isSpicyOnly: Boolean,
    isGlutenFree: Boolean,
    onVegetarianChanged: (Boolean) -> Unit,
    onSpicyOnlyChanged: (Boolean) -> Unit,
    onGlutenFreeChanged: (Boolean) -> Unit,
    onLogout: () -> Unit            // Add this
) {
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
            text = username,
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

                PreferenceToggle(
                    label = "Vegetarian",
                    isChecked = isVegetarian,
                    onCheckedChange = onVegetarianChanged
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
                )

                PreferenceToggle(
                    label = "Spicy Only",
                    isChecked = isSpicyOnly,
                    onCheckedChange = onSpicyOnlyChanged
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
                )

                PreferenceToggle(
                    label = "Gluten Free",
                    isChecked = isGlutenFree,
                    onCheckedChange = onGlutenFreeChanged
                )
                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = onLogout,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Log Out")
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
            color = MaterialTheme.colorScheme.onSurfaceVariant
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
    HungryTheme {
        ProfileScreen(
            username = "Guest",
            isVegetarian = false,
            isSpicyOnly = true,
            isGlutenFree = false,
            onVegetarianChanged = {},
            onSpicyOnlyChanged = {},
            onGlutenFreeChanged = {},
            onLogout = {} // Added empty lambda
        )
    }
}