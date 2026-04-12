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
    val menu: List<MenuItem> = emptyList()
)

val dummyRestaurants = listOf(
    Restaurant(
        restId = "1",
        name = "Taco Star",
        location = "Golden, CO",
        imageUrl = "",
        rating = 4.5,
        price = "$",
        menu = listOf(
            MenuItem("Spicy Breakfast Burrito", "Eggs, potatoes, cheese, and spicy green chile.", "$6.50"),
            MenuItem("Carne Asada Fries", "Crispy fries topped with steak, guac, and cheese.", "$10.99"),
            MenuItem("3 Rolled Tacos", "Crispy rolled tacos covered in cheese and guac.", "$5.00"),
            MenuItem("Colorado Burrito", "Potatoes, steak, pico de gallo, and cheese.", "$8.50")
        )
    ),
    Restaurant("2", "Woody's Wood Fired Pizza", "Golden, CO", "", 4.8, "$$"),
    Restaurant("3", "Bonfire Burritos", "Golden, CO", "", 4.9, "$")
)