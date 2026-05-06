package com.csci448.khoa_nguyen.hungry.data.models

// A simple way to track individual food or drink items
data class MenuItem(
    val name: String = "",
    val description: String = "",
    val price: String = ""
)

// The main object for holding all the info about a restaurant
data class Restaurant(
    val restId: String = "",
    val name: String = "",
    val location: String = "",
    val imageUrl: String = "",
    val rating: Double = 0.0,
    val price: String = "",
    val menu: List<MenuItem> = emptyList(),
    val xPct: Float = 0f,
    val yPct: Float = 0f
)

// Some sample data to use for testing the app
val dummyRestaurants = listOf(
    Restaurant(
        restId = "1",
        name = "Xicamiti La Taquería",
        location = "Golden, CO",
        imageUrl = "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38",
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
        imageUrl = "https://images.unsplash.com/photo-1513104890138-7c749659a591",
        rating = 4.8,
        price = "$$",
        xPct = 0.41f, yPct = 0.32f,
        menu = listOf(MenuItem("Pizza Buffet", "All you can eat pizza and salad.", "$15.99"))
    ),
    Restaurant(
        restId = "3",
        name = "Bonfire Burritos",
        location = "Golden, CO",
        imageUrl = "https://images.unsplash.com/photo-1528735602780-2552fd46c7af",
        rating = 4.9,
        price = "$",
        xPct = 0.46f, yPct = 0.35f,
        menu = listOf(MenuItem("The Chupacabra", "Huge breakfast burrito.", "$9.50"))
    ),
    Restaurant(
        restId = "4",
        name = "The Golden Mill",
        location = "Golden, CO",
        imageUrl = "https://images.unsplash.com/photo-1550950158-d0d960dff51b",
        rating = 4.7,
        price = "$$",
        xPct = 0.40f, yPct = 0.34f,
        menu = listOf(MenuItem("Self-Serve Beer Wall", "Pay by the ounce.", "Varies"))
    ),
    Restaurant(
        restId = "5",
        name = "Bob's Atomic Burgers",
        location = "Golden, CO",
        imageUrl = "https://images.unsplash.com/photo-1568901346375-23c9450c58cd",
        rating = 4.6,
        price = "$",
        xPct = 0.43f, yPct = 0.33f,
        menu = listOf(MenuItem("Atomic Burger", "Custom built with any toppings.", "$8.25"))
    ),
    Restaurant(
        restId = "6",
        name = "In-N-Out Burger",
        location = "W Colfax Ave",
        imageUrl = "https://images.unsplash.com/photo-1551782450-a2132b4ba21d",
        rating = 4.5,
        price = "$",
        xPct = 0.75f, yPct = 0.82f,
        menu = listOf(
            MenuItem("Double-Double", "Two beef patties and cheese.", "$5.90"),
            MenuItem("Animal Style Fries", "Cheese, spread, and grilled onions.", "$4.50")
        )
    ),
    Restaurant(
        restId = "8",
        name = "Cannonball Creek Brewing",
        location = "N Washington Ave",
        imageUrl = "https://images.unsplash.com/photo-1535958636474-b021ee887b13",
        rating = 4.8,
        price = "$$",
        xPct = 0.38f, yPct = 0.18f,
        menu = listOf(MenuItem("Mindbender IPA", "Award-winning local brew.", "$7.00"))
    ),
    Restaurant(
        restId = "9",
        name = "Chick-fil-A",
        location = "Applewood",
        imageUrl = "https://images.unsplash.com/photo-1626082927389-6cd097cdc6ec",
        price = "$",
        xPct = 0.82f, yPct = 0.22f,
        menu = listOf(MenuItem("Spicy Chicken Sandwich", "With dill pickle chips.", "$6.25"))
    ),
    Restaurant(
        restId = "7",
        name = "Taco Bell",
        location = "S Golden Rd",
        imageUrl = "https://images.unsplash.com/photo-1599974579688-8dbdd335c77f",
        rating = 3.5,
        price = "$",
        xPct = 0.55f, yPct = 0.58f,
        menu = listOf(MenuItem("Crunchwrap Supreme", "The classic handheld.", "$4.89"))
    ),
    Restaurant(
        restId = "10",
        name = "Wendy's",
        location = "W 44th Ave",
        imageUrl = "https://images.unsplash.com/photo-1550547660-d9450f859349",
        rating = 3.2,
        price = "$",
        xPct = 0.88f, yPct = 0.10f,
        menu = listOf(MenuItem("Baconator", "Beef and lots of bacon.", "$8.50"))
    )
)