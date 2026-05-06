package com.csci448.khoa_nguyen.hungry.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.csci448.khoa_nguyen.hungry.data.models.User
import com.csci448.khoa_nguyen.hungry.ui.viewmodels.FriendRequest

// The main screen for managing your social list and finding new people
@Composable
fun FriendsScreen(
    usersList: List<User>,
    myFriends: List<User>,
    pendingRequests: List<FriendRequest>,
    onAddFriendClick: (User) -> Unit,
    onAcceptRequest: (String) -> Unit,
    onDenyRequest: (String) -> Unit,
    onFriendClick: (User) -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    val sentRequests = remember { mutableStateListOf<String>() }

    // Filter out existing friends so you only search for new people to add
    val filteredUsers = usersList.filter { user ->
        (user.displayName.contains(searchText, ignoreCase = true) ||
                user.email.contains(searchText, ignoreCase = true)) &&
                myFriends.none { it.uid == user.uid }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search for friends...") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        )

        LazyColumn {
            // Displays any incoming requests that need an answer
            if (pendingRequests.isNotEmpty()) {
                item {
                    Text("Friend Requests", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(vertical = 8.dp))
                }
                items(pendingRequests) { request ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f))) {
                        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(text = request.fromEmail, fontWeight = FontWeight.SemiBold)
                            Row {
                                IconButton(onClick = { onAcceptRequest(request.fromUid) }) { Icon(Icons.Default.Check, "Accept", tint = Color(0xFF388E3C)) }
                                IconButton(onClick = { onDenyRequest(request.fromUid) }) { Icon(Icons.Default.Close, "Deny", tint = MaterialTheme.colorScheme.error) }
                            }
                        }
                    }
                }
                item { HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp)) }
            }

            // A list of your current friends; clicking one shows their favorites
            if (myFriends.isNotEmpty()) {
                item {
                    Text("My Friends", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))
                }
                items(myFriends) { friend ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        onClick = { onFriendClick(friend) },
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFD700))
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(friend.displayName, fontWeight = FontWeight.Bold)
                                Text("View their favorites", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                            }
                        }
                    }
                }
                item { HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp)) }
            }

            // Shows other users on the app that you can send requests to
            item {
                Text("Find Friends", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))
            }
            if (filteredUsers.isEmpty()) {
                item { Text("No other users found.", modifier = Modifier.padding(8.dp), color = Color.Gray) }
            } else {
                items(filteredUsers) { user ->
                    val isSent = sentRequests.contains(user.uid)
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(48.dp).background(MaterialTheme.colorScheme.secondaryContainer, CircleShape), contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Person, null)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = user.displayName, style = MaterialTheme.typography.titleMedium)
                                Text(text = user.bio, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                            }
                            Button(onClick = { if (!isSent) { onAddFriendClick(user); sentRequests.add(user.uid) } }, enabled = !isSent) {
                                Text(if (isSent) "Sent" else "Add")
                            }
                        }
                    }
                }
            }
        }
    }
}