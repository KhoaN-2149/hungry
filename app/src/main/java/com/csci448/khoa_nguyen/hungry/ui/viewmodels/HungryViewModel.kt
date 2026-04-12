package com.csci448.khoa_nguyen.hungry.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.csci448.khoa_nguyen.hungry.data.models.Restaurant
import com.csci448.khoa_nguyen.hungry.data.models.dummyRestaurants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HungryViewModel : ViewModel() {
    // --- Profile State ---
    private val _isVegetarian = MutableStateFlow(false)
    val isVegetarian: StateFlow<Boolean> = _isVegetarian.asStateFlow()

    private val _isSpicyOnly = MutableStateFlow(true)
    val isSpicyOnly: StateFlow<Boolean> = _isSpicyOnly.asStateFlow()

    private val _isGlutenFree = MutableStateFlow(false)
    val isGlutenFree: StateFlow<Boolean> = _isGlutenFree.asStateFlow()

    // --- Favorites State ---
    // We are using your main Restaurant model here so we can easily pass
    // full restaurant data from the Swipe screen to the Favorites screen later.
    private val _favorites = MutableStateFlow<List<Restaurant>>(emptyList())
    val favorites: StateFlow<List<Restaurant>> = _favorites.asStateFlow()

    // --- Swipe State ---
    private val _currentRestaurants = MutableStateFlow(dummyRestaurants)
    val currentRestaurants: StateFlow<List<Restaurant>> = _currentRestaurants.asStateFlow()

    // --- Actions ---
    fun updateVegetarian(isVeg: Boolean) { _isVegetarian.value = isVeg }
    fun updateSpicyOnly(isSpicy: Boolean) { _isSpicyOnly.value = isSpicy }
    fun updateGlutenFree(isGluten: Boolean) { _isGlutenFree.value = isGluten }

    // Placeholder actions for when we implement the swipe logic
    fun swipeRight(restaurant: Restaurant) {
        // Add to favorites if not already there
        if (!_favorites.value.contains(restaurant)) {
            _favorites.update { currentList -> currentList + restaurant }
        }
        moveToNextRestaurant()
    }

    fun swipeLeft() {
        moveToNextRestaurant()
    }

    private fun moveToNextRestaurant() {
        _currentRestaurants.update { currentList ->
            if (currentList.isNotEmpty()) currentList.drop(1) else emptyList()
        }
    }
}