package ua.com.bookapi.features.books.list

data class CategoryListState(
    val paramOne: String = "default",
    val paramTwo: List<String> = emptyList(),
)