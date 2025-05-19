package ua.com.bookapi.features.books.presentation.list

import ua.com.bookapi.core.utils.UiText
import ua.com.bookapi.core.utils.emptyUiText
import ua.com.bookapi.features.books.domain.models.Category

data class CategoryListState(
    val error: UiText = emptyUiText,
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList()
)