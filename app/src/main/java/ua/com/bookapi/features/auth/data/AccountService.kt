package ua.com.bookapi.features.auth.data

import kotlinx.coroutines.flow.Flow

interface AccountService {

    fun hasUser(): Boolean
    suspend fun linkAccountWithGoogle(idToken: String)
    suspend fun signInWithGoogle(idToken: String)
}