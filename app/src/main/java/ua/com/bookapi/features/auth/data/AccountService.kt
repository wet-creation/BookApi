package ua.com.bookapi.features.auth.data

import kotlinx.coroutines.flow.Flow

interface AccountService {

    val hasUser: Flow<Boolean>
    suspend fun signInWithGoogle(idToken: String)
}