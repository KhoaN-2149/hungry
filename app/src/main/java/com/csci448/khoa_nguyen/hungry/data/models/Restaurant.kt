////com/csci448/khoa_nguyen/hungry/data/models/Restaurant.kt

package com.csci448.khoa_nguyen.hungry.data.models
data class MenuItem(
    val name: String,
    val description: String,
    val price: String
)

data class Restaurant(
    val restId: String,
    val name: String,
    val location: String,
    val imageUrl: String,
    val rating: Double,
    val price: String,
    val menu: List<MenuItem> = emptyList(),
    // Add these for the Map logic
    val xPct: Float = 0f,
    val yPct: Float = 0f
)

val dummyRestaurants = listOf(
    Restaurant(
        restId = "1",
        name = "Xicamiti La Taquería",
        location = "Golden, CO",
        imageUrl = "",
        rating = 4.9,
        price = "$",
        xPct = 0.42f, yPct = 0.35f, // Downtown Golden
        menu = listOf(
            MenuItem("Street Tacos", "Choice of Al Pastor, Asada, or Pollo.", "$3.50"),
            MenuItem("Quesabirria", "Slow-cooked beef with consomé.", "$12.00")
        )
    ),
    Restaurant(
        restId = "2",
        name = "Woody's Wood Fired Pizza",
        location = "Golden, CO",
        imageUrl = "",
        rating = 4.8,
        price = "$$",
        xPct = 0.41f, yPct = 0.32f
    ),
    Restaurant(
        restId = "3",
        name = "Bonfire Burritos",
        location = "Golden, CO",
        imageUrl = "",
        rating = 4.9,
        price = "$",
        xPct = 0.46f, yPct = 0.35f
    ),
    Restaurant(
        restId = "4",
        name = "The Golden Mill",
        location = "Golden, CO",
        imageUrl = "",
        rating = 4.7,
        price = "$$",
        xPct = 0.40f, yPct = 0.34f
    ),
    Restaurant(
        restId = "5",
        name = "Bob's Atomic Burgers",
        location = "Golden, CO",
        imageUrl = "",
        rating = 4.6,
        price = "$",
        xPct = 0.43f, yPct = 0.33f
    )
)