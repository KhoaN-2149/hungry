package com.csci448.khoa_nguyen.hungry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.csci448.khoa_nguyen.hungry.ui.theme.HungryTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

// This is the starting point of the app where everything kicks off
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // This sets the theme and fills the screen with the main app component
            HungryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HungryApp()
                }
            }
        }
    }
}

// Just a basic placeholder function for displaying text
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

// Allows for a quick check of the greeting UI without running the whole app
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HungryTheme {
        Greeting("Android")
    }
}