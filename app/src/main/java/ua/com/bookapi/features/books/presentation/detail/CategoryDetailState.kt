package ua.com.bookapi.features.books.presentation.detail

import ua.com.bookapi.core.utils.UiText
import ua.com.bookapi.core.utils.emptyUiText
import ua.com.bookapi.features.books.domain.models.Book

data class CategoryDetailState(
    val error: UiText = emptyUiText,
    val isLoading: Boolean = false,
    val books: List<Book> = emptyList()
)