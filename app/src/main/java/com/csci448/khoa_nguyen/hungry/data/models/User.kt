//// com/csci448/khoa_nguyen/hungry/data/models/User.kt

package com.csci448.khoa_nguyen.hungry.data.models

data class User(
    val uid: String = "",
    val email: String = "",
    val displayName: String = "",
    val bio: String = "Foodie Level: Beginner"
)