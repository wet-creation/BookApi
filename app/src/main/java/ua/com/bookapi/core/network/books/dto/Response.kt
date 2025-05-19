package ua.com.bookapi.core.network.books.dto

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val results: ResponseResultsDto
)