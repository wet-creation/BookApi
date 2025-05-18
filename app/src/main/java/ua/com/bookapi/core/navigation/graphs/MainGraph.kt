package ua.com.bookapi.core.navigation.graphs

import kotlinx.serialization.Serializable

@Serializable
data object MainGraph {
    @Serializable
    data object Auth
    @Serializable
    data object Categories
    @Serializable
    data class Books(val id: Int)
}