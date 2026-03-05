package com.csci448.khoa_nguyen.hungry.data.models

data class Restaurant(
    val restId: String,
    val name: String,
    val location: String,
    val imageUrl: String,
    val rating: Double,
    val price: String
)

// A list of fake data so we can test the UI before you hook up the real Google Places SDK
val dummyRestaurants = listOf(
    Restaurant("1", "Taco Star", "Golden, CO", "", 4.5, "$"),
    Restaurant("2", "Woody's Wood Fired Pizza", "Golden, CO", "", 4.8, "$$"),
    Restaurant("3", "Bonfire Burritos", "Golden, CO", "", 4.9, "$")
)