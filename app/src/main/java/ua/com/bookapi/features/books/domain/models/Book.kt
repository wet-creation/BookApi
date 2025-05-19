package ua.com.bookapi.features.books.domain.models

typealias ImageUrl = String?

data class Book(
    val name: String = "",
    val description: String = "",
    val author: String = "",
    val categoryId: Int,
    val bookImage: ImageUrl = null,
    val productUrl: String = "",
    val rank: Int = -1,
    val publisher: String = ""
)
