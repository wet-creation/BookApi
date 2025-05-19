package ua.com.bookapi.features.books.presentation.list

sealed interface CategoryListAction {
    data object DismissDialog : CategoryListAction
    data class NavigateToBooks(val categoryId: Int, val categoryName: String) : CategoryListAction
}