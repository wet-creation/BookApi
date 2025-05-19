package ua.com.bookapi.core.network.books.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ua.com.bookapi.features.books.domain.models.ImageUrl

@Serializable
data class BooksDto(
    val title: String = "",
    val description: String = "",
    val author: String = "",
    @SerialName("book_image")
    val bookImage: ImageUrl = null,
    @SerialName("amazon_product_url")
    val productUrl: String = "",
    val rank: Int = -1,
    val publisher: String = ""
)
