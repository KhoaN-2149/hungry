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

// Basic info for a friend request
data class FriendRequest(
    val fromUid: String = "",
    val fromEmail: String = "",
    val status: String = ""
)

// This manages the app's data, like friends, favorites, and user settings
class HungryViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _allUsers = MutableStateFlow<List<User>>(emptyList())
    val allUsers: StateFlow<List<User>> = _allUsers.asStateFlow()

    private val _myFriends = MutableStateFlow<List<User>>(emptyList())
    val myFriends: StateFlow<List<User>> = _myFriends.asStateFlow()

    private val _friendFavorites = MutableStateFlow<List<Restaurant>>(emptyList())
    val friendFavorites: StateFlow<List<Restaurant>> = _friendFavorites.asStateFlow()

    private val _mutualFavorites = MutableStateFlow<List<Restaurant>>(emptyList())
    val mutualFavorites: StateFlow<List<Restaurant>> = _mutualFavorites.asStateFlow()

    private val _pendingRequests = MutableStateFlow<List<FriendRequest>>(emptyList())
    val pendingRequests: StateFlow<List<FriendRequest>> = _pendingRequests.asStateFlow()

    private val _userBio = MutableStateFlow("Foodie Level: Beginner")
    val userBio: StateFlow<String> = _userBio.asStateFlow()

    private val _isVegetarian = MutableStateFlow(false)
    val isVegetarian: StateFlow<Boolean> = _isVegetarian.asStateFlow()

    private val _isSpicyOnly = MutableStateFlow(true)
    val isSpicyOnly: StateFlow<Boolean> = _isSpicyOnly.asStateFlow()

    private val _isGlutenFree = MutableStateFlow(false)
    val isGlutenFree: StateFlow<Boolean> = _isGlutenFree.asStateFlow()

    private val _favorites = MutableStateFlow<List<Restaurant>>(emptyList())
    val favorites: StateFlow<List<Restaurant>> = _favorites.asStateFlow()

    private val _currentRestaurants = MutableStateFlow(dummyRestaurants)
    val currentRestaurants: StateFlow<List<Restaurant>> = _currentRestaurants.asStateFlow()

    init {
        fetchUsers()

        // Automatically load or clear data when the user logs in or out
        auth.addAuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                fetchFavorites(user.uid)
                fetchPendingRequests(user.uid)
                fetchUserBio(user.uid)
                fetchMyFriends(user.uid)
            } else {
                _favorites.value = emptyList()
                _pendingRequests.value = emptyList()
                _myFriends.value = emptyList()
                _userBio.value = "Foodie Level: Beginner"
            }
        }
    }

    // Get a list of everyone else using the app
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

    // Load the user's specific friends list
    private fun fetchMyFriends(uid: String) {
        db.collection("users").document(uid).collection("friends")
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val friendIds = snapshot.documents.mapNotNull { it.getString("friendUid") }
                    _myFriends.value = _allUsers.value.filter { it.uid in friendIds }
                }
            }
    }

    // Load the restaurants the user has swiped right on
    private fun fetchFavorites(uid: String) {
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

    // Check for any new friend requests
    private fun fetchPendingRequests(uid: String) {
        db.collection("users").document(uid).collection("friend_requests")
            .whereEqualTo("status", "pending")
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                if (snapshot != null) {
                    val requests = snapshot.documents.mapNotNull { it.toObject(FriendRequest::class.java) }
                    _pendingRequests.value = requests
                }
            }
    }

    // Get the user's bio description
    private fun fetchUserBio(uid: String) {
        db.collection("users").document(uid).addSnapshotListener { snapshot, error ->
            if (error == null && snapshot != null && snapshot.exists()) {
                val bio = snapshot.getString("bio") ?: "Foodie Level: Beginner"
                _userBio.value = bio
            }
        }
    }

    // Get a friend's favorites and see which ones you both liked
    fun loadFriendData(friendUid: String) {
        db.collection("users").document(friendUid).collection("favorites")
            .get()
            .addOnSuccessListener { snapshot ->
                val faves = snapshot.documents.mapNotNull { it.toObject(Restaurant::class.java) }
                _friendFavorites.value = faves

                val myIds = _favorites.value.map { it.restId }.toSet()
                _mutualFavorites.value = faves.filter { it.restId in myIds }
            }
    }

    fun updateBio(newBio: String) {
        val uid = auth.currentUser?.uid ?: return
        db.collection("users").document(uid).update("bio", newBio)
    }

    // Update dietary preference toggles
    fun updateVegetarian(isVeg: Boolean) { _isVegetarian.value = isVeg }
    fun updateSpicyOnly(isSpicy: Boolean) { _isSpicyOnly.value = isSpicy }
    fun updateGlutenFree(isGluten: Boolean) { _isGlutenFree.value = isGluten }

    // Save a restaurant to favorites when swiping right
    fun swipeRight(restaurant: Restaurant) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            db.collection("users").document(uid).collection("favorites")
                .document(restaurant.restId)
                .set(restaurant)
        } else {
            if (!_favorites.value.contains(restaurant)) {
                _favorites.update { currentList -> currentList + restaurant }
            }
        }
        moveToNextRestaurant()
    }

    fun swipeLeft() {
        moveToNextRestaurant()
    }

    // Remove the top card from the deck
    private fun moveToNextRestaurant() {
        _currentRestaurants.update { currentList ->
            if (currentList.isNotEmpty()) currentList.drop(1) else emptyList()
        }
    }

    // Send a request to another user
    fun sendFriendRequest(targetUser: User) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val requestData = hashMapOf(
                "fromUid" to currentUser.uid,
                "fromEmail" to currentUser.email,
                "status" to "pending"
            )
            db.collection("users").document(targetUser.uid)
                .collection("friend_requests").document(currentUser.uid)
                .set(requestData)
        }
    }

    // Add someone as a friend and make sure they have you added too
    fun acceptFriendRequest(requestUid: String) {
        val uid = auth.currentUser?.uid ?: return

        db.collection("users").document(uid).collection("friend_requests").document(requestUid)
            .update("status", "accepted")

        val friendDataForMe = hashMapOf("friendUid" to requestUid, "addedAt" to System.currentTimeMillis())
        db.collection("users").document(uid).collection("friends").document(requestUid).set(friendDataForMe)

        val friendDataForThem = hashMapOf("friendUid" to uid, "addedAt" to System.currentTimeMillis())
        db.collection("users").document(requestUid).collection("friends").document(uid).set(friendDataForThem)
    }

    // Remove a pending request
    fun denyFriendRequest(requestUid: String) {
        val uid = auth.currentUser?.uid ?: return
        db.collection("users").document(uid).collection("friend_requests").document(requestUid).delete()
    }
}