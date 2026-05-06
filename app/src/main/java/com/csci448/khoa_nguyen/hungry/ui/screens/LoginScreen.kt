package com.csci448.khoa_nguyen.hungry.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.csci448.khoa_nguyen.hungry.ui.viewmodels.AuthState

// This is the entry point where users can log in, sign up, or just browse as a guest
@Composable
fun LoginScreen(
    authState: AuthState,
    onLoginClick: (String, String) -> Unit,
    onSignUpClick: (String, String) -> Unit,
    onGuestClick: () -> Unit
) {
    // Just keeping track of what the user types into the boxes
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            // Big red branding title at the top
            Text(
                text = "Hungry",
                style = MaterialTheme.typography.displayLarge,
                color = Color(0xFFD32F2F),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Text field that hides the characters for security
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Show a little error message if something went wrong during login
            if (authState is AuthState.Error) {
                Text(
                    text = authState.message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Switch between showing the buttons or a loading spinner while waiting
            if (authState is AuthState.Loading) {
                CircularProgressIndicator(color = Color(0xFFD32F2F))
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = { onSignUpClick(email, password) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Sign Up", color = Color(0xFFD32F2F))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = { onLoginClick(email, password) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Login")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // For users who don't want to create an account right away
                TextButton(onClick = onGuestClick) {
                    Text("Continue as Guest", color = Color.Gray)
                }
            }
        }
    }
}