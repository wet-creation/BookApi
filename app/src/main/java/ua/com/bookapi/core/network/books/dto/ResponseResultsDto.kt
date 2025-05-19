package ua.com.bookapi.core.network.books.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseResultsDto(
    @SerialName("published_date")
    val publishDate: String,
    val lists: List<CategoryDto>
)