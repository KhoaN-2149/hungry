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
    val xPct: Float = 0.5f, // NEW: X coordinate percentage (0.0 to 1.0)
    val yPct: Float = 0.5f, // NEW: Y coordinate percentage (0.0 to 1.0)
    val menu: List<MenuItem> = emptyList()
)

val dummyRestaurants = listOf(
    Restaurant(
        restId = "1",
        name = "Taco Star",
        location = "Golden, CO",
        imageUrl = "https://images.unsplash.com/photo-1551504734-5ee1c4a1479b?q=80&w=1000&auto=format&fit=crop",
        rating = 4.5,
        price = "$",
        xPct = 0.3f, // Pin placed 30% across the screen
        yPct = 0.4f, // Pin placed 40% down the screen
        menu = listOf(
            MenuItem("Spicy Breakfast Burrito", "Eggs, potatoes, cheese, and spicy green chile.", "$6.50"),
            MenuItem("Carne Asada Fries", "Crispy fries topped with steak, guac, and cheese.", "$10.99")
        )
    ),
    Restaurant(
        restId = "2",
        name = "Woody's Wood Fired Pizza",
        location = "Golden, CO",
        imageUrl = "https://images.unsplash.com/photo-1513104890138-7c749659a591?q=80&w=1000&auto=format&fit=crop",
        rating = 4.8,
        price = "$$",
        xPct = 0.7f, // 70% across
        yPct = 0.3f  // 30% down
    ),
    Restaurant(
        restId = "3",
        name = "Bonfire Burritos",
        location = "Golden, CO",
        imageUrl = "https://images.unsplash.com/photo-1626700051175-6818013e1d4f?q=80&w=1000&auto=format&fit=crop",
        rating = 4.9,
        price = "$",
        xPct = 0.5f, // Dead center horizontally
        yPct = 0.7f  // 70% down
    )
)