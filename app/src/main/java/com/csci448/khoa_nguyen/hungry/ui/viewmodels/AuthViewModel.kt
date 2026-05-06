package com.csci448.khoa_nguyen.hungry.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.csci448.khoa_nguyen.hungry.data.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// These are the possible states for the login process
sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Authenticated : AuthState()
    data class Error(val message: String) : AuthState()
}

// This manages everything related to signing in, signing up, and keeping track of the user
class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    // A quick way to grab the logged-in user's email
    val currentUserEmail: String?
        get() = auth.currentUser?.email

    init {
        // If the app opens and someone is already signed in, skip the login screen
        if (auth.currentUser != null) {
            _authState.value = AuthState.Authenticated
        }
    }

    // Handles creating a new account and saving that user's info to our database
    fun signUp(email: String, pass: String) {
        if (email.isBlank() || pass.isBlank()) {
            _authState.value = AuthState.Error("Email and Password cannot be empty.")
            return
        }
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = auth.currentUser
                    if (firebaseUser != null) {
                        val defaultName = email.substringBefore("@")
                        val newUser = User(
                            uid = firebaseUser.uid,
                            email = email,
                            displayName = defaultName
                        )

                        db.collection("users").document(firebaseUser.uid)
                            .set(newUser)
                            .addOnSuccessListener {
                                _authState.value = AuthState.Authenticated
                            }
                            .addOnFailureListener { e ->
                                _authState.value = AuthState.Error(e.message ?: "Failed to save profile")
                            }
                    }
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Sign up failed")
                }
            }
    }

    // Tries to log someone in with an existing account
    fun login(email: String, pass: String) {
        if (email.isBlank() || pass.isBlank()) {
            _authState.value = AuthState.Error("Email and Password cannot be empty.")
            return
        }
        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Login failed")
                }
            }
    }

    // Signs the user out and resets the state to idle
    fun logout() {
        auth.signOut()
        _authState.value = AuthState.Idle
    }
}