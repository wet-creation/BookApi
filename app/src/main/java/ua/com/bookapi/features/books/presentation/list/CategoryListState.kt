package ua.com.bookapi.features.books.presentation.list

data class CategoryListState(
    val paramOne: String = "default",
    val paramTwo: List<String> = emptyList(),
)