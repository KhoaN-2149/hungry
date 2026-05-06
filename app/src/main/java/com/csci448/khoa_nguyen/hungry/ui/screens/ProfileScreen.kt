package com.csci448.khoa_nguyen.hungry.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import com.csci448.khoa_nguyen.hungry.ui.theme.HungryTheme

@Composable
fun ProfileScreen(
    username: String,
    currentBio: String,             // Added
    isVegetarian: Boolean,
    isSpicyOnly: Boolean,
    isGlutenFree: Boolean,
    onVegetarianChanged: (Boolean) -> Unit,
    onSpicyOnlyChanged: (Boolean) -> Unit,
    onGlutenFreeChanged: (Boolean) -> Unit,
    onUpdateBio: (String) -> Unit,  // Added
    onLogout: () -> Unit
) {
    var showBioDialog by remember { mutableStateOf(false) }
    var tempBioText by remember { mutableStateOf(currentBio) }

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
            Text(
                text = "KN",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Username
        Text(
            text = username,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        // Editable Bio
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = currentBio,
                fontSize = 16.sp,
                color = Color.Gray
            )
            IconButton(onClick = {
                tempBioText = currentBio
                showBioDialog = true
            }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Bio",
                    modifier = Modifier.size(16.dp),
                    tint = Color.Gray
                )
            }
        }

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

    if (showBioDialog) {
        AlertDialog(
            onDismissRequest = { showBioDialog = false },
            title = { Text("Edit Bio") },
            text = {
                OutlinedTextField(
                    value = tempBioText,
                    onValueChange = { tempBioText = it },
                    singleLine = true,
                    label = { Text("Short Bio") }
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    onUpdateBio(tempBioText)
                    showBioDialog = false
                }) { Text("Save") }
            },
            dismissButton = {
                TextButton(onClick = { showBioDialog = false }) { Text("Cancel") }
            }
        )
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
            currentBio = "Foodie Level: Expert",
            isVegetarian = false,
            isSpicyOnly = true,
            isGlutenFree = false,
            onVegetarianChanged = {},
            onSpicyOnlyChanged = {},
            onGlutenFreeChanged = {},
            onUpdateBio = {},
            onLogout = {}
        )
    }
}