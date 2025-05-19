package ua.com.bookapi.core.network.books.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    @SerialName("list_id")
    val id: Int,
    @SerialName("display_name")
    val name: String,
    val books: List<BooksDto>
)
