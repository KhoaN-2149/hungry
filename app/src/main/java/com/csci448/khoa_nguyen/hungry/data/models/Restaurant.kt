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
        xPct = 0.42f, yPct = 0.35f,
        menu = listOf(
            MenuItem("Street Tacos", "Al Pastor or Asada.", "$3.50"),
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
        xPct = 0.41f, yPct = 0.32f,
        menu = listOf(MenuItem("Pizza Buffet", "All you can eat pizza and salad.", "$15.99"))
    ),
    Restaurant(
        restId = "3",
        name = "Bonfire Burritos",
        location = "Golden, CO",
        imageUrl = "",
        rating = 4.9,
        price = "$",
        xPct = 0.46f, yPct = 0.35f,
        menu = listOf(MenuItem("The Chupacabra", "Huge breakfast burrito.", "$9.50"))
    ),
    Restaurant(
        restId = "4",
        name = "The Golden Mill",
        location = "Golden, CO",
        imageUrl = "",
        rating = 4.7,
        price = "$$",
        xPct = 0.40f, yPct = 0.34f,
        menu = listOf(MenuItem("Self-Serve Beer Wall", "Pay by the ounce.", "Varies"))
    ),
    Restaurant(
        restId = "5",
        name = "Bob's Atomic Burgers",
        location = "Golden, CO",
        imageUrl = "",
        rating = 4.6,
        price = "$",
        xPct = 0.43f, yPct = 0.33f,
        menu = listOf(MenuItem("Atomic Burger", "Custom built with any toppings.", "$8.25"))
    ),
    // NEW SCATTERED LOCATIONS
    Restaurant(
        restId = "6",
        name = "In-N-Out Burger",
        location = "W Colfax Ave",
        imageUrl = "",
        rating = 4.5,
        price = "$",
        xPct = 0.75f, yPct = 0.82f, // Bottom Right (Denver West)
        menu = listOf(
            MenuItem("Double-Double", "Two beef patties and cheese.", "$5.90"),
            MenuItem("Animal Style Fries", "Cheese, spread, and grilled onions.", "$4.50")
        )
    ),
    Restaurant(
        restId = "7",
        name = "Taco Bell",
        location = "S Golden Rd",
        imageUrl = "",
        rating = 3.5,
        price = "$",
        xPct = 0.55f, yPct = 0.58f, // Mid-South
        menu = listOf(MenuItem("Crunchwrap Supreme", "The classic handheld.", "$4.89"))
    ),
    Restaurant(
        restId = "8",
        name = "Cannonball Creek Brewing",
        location = "N Washington Ave",
        imageUrl = "",
        rating = 4.8,
        price = "$$",
        xPct = 0.38f, yPct = 0.18f, // Top Left (North Golden)
        menu = listOf(MenuItem("Mindbender IPA", "Award-winning local brew.", "$7.00"))
    ),
    Restaurant(
        restId = "9",
        name = "Chick-fil-A",
        location = "Applewood",
        imageUrl = "",
        rating = 4.4,
        price = "$",
        xPct = 0.82f, yPct = 0.22f, // Top Right (Applewood area)
        menu = listOf(MenuItem("Spicy Chicken Sandwich", "With dill pickle chips.", "$6.25"))
    ),
    Restaurant(
        restId = "10",
        name = "Wendy's",
        location = "W 44th Ave",
        imageUrl = "",
        rating = 3.2,
        price = "$",
        xPct = 0.88f, yPct = 0.10f, // Extreme Top Right
        menu = listOf(MenuItem("Baconator", "Beef and lots of bacon.", "$8.50"))
    )
)