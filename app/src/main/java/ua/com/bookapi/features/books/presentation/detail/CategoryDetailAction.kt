package ua.com.bookapi.features.books.presentation.detail

sealed interface CategoryDetailAction {
    data object DismissDialog: CategoryDetailAction
    data class NavigateToBrowser(val url: String) : CategoryDetailAction
}