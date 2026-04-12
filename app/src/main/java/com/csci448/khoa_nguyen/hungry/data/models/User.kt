package com.csci448.khoa_nguyen.hungry.data.models

// We provide default values ("") so Firestore can easily convert the database document into this Kotlin object.
data class User(
    val uid: String = "",
    val email: String = "",
    val displayName: String = "",
    val bio: String = "Foodie Level: Beginner"
)