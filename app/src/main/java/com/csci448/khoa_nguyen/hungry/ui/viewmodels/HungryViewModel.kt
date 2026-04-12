package com.csci448.khoa_nguyen.hungry.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.csci448.khoa_nguyen.hungry.data.models.Restaurant
import com.csci448.khoa_nguyen.hungry.data.models.User
import com.csci448.khoa_nguyen.hungry.data.models.dummyRestaurants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HungryViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // --- Friends State ---
    private val _allUsers = MutableStateFlow<List<User>>(emptyList())
    val allUsers: StateFlow<List<User>> = _allUsers.asStateFlow()

    // --- Profile State ---
    private val _isVegetarian = MutableStateFlow(false)
    val isVegetarian: StateFlow<Boolean> = _isVegetarian.asStateFlow()

    private val _isSpicyOnly = MutableStateFlow(true)
    val isSpicyOnly: StateFlow<Boolean> = _isSpicyOnly.asStateFlow()

    private val _isGlutenFree = MutableStateFlow(false)
    val isGlutenFree: StateFlow<Boolean> = _isGlutenFree.asStateFlow()

    // --- Favorites & Swipe State ---
    private val _favorites = MutableStateFlow<List<Restaurant>>(emptyList())
    val favorites: StateFlow<List<Restaurant>> = _favorites.asStateFlow()

    private val _currentRestaurants = MutableStateFlow(dummyRestaurants)
    val currentRestaurants: StateFlow<List<Restaurant>> = _currentRestaurants.asStateFlow()

    init {
        // 1. Fetch the list of all users to search for
        fetchUsers()

        // 2. Listen for Logins/Logouts to fetch user-specific favorites
        auth.addAuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                // User logged in -> Go get their saved favorites from the cloud!
                fetchFavorites(user.uid)
            } else {
                // User logged out -> Clear the screen
                _favorites.value = emptyList()
            }
        }
    }

    private fun fetchUsers() {
        db.collection("users").addSnapshotListener { snapshot, error ->
            if (error != null) return@addSnapshotListener
            if (snapshot != null) {
                val usersList = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(User::class.java)
                }.filter { it.uid != auth.currentUser?.uid }
                _allUsers.value = usersList
            }
        }
    }

    private fun fetchFavorites(uid: String) {
        // Add a real-time listener to the user's personal favorites folder
        db.collection("users").document(uid).collection("favorites")
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                if (snapshot != null) {
                    val favsList = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(Restaurant::class.java)
                    }
                    _favorites.value = favsList
                }
            }
    }

    // --- Actions ---
    fun updateVegetarian(isVeg: Boolean) { _isVegetarian.value = isVeg }
    fun updateSpicyOnly(isSpicy: Boolean) { _isSpicyOnly.value = isSpicy }
    fun updateGlutenFree(isGluten: Boolean) { _isGlutenFree.value = isGluten }

    fun swipeRight(restaurant: Restaurant) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            // OPTION 1 LOGIC: Save the swiped restaurant straight to Firestore!
            // Because we set up a snapshot listener above, the UI will automatically update.
            db.collection("users").document(uid).collection("favorites")
                .document(restaurant.restId) // Use the restaurant ID as the document name
                .set(restaurant)
        } else {
            // Fallback for "Guest" users who aren't logged in
            if (!_favorites.value.contains(restaurant)) {
                _favorites.update { currentList -> currentList + restaurant }
            }
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

    // OPTION 2 LOGIC: Send a Friend Request
    fun sendFriendRequest(targetUser: User) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // Create a little package of data to send to the other user
            val requestData = hashMapOf(
                "fromUid" to currentUser.uid,
                "fromEmail" to currentUser.email,
                "status" to "pending"
            )

            // Save it in the TARGET user's database profile
            db.collection("users").document(targetUser.uid)
                .collection("friend_requests").document(currentUser.uid)
                .set(requestData)
        }
    }
}