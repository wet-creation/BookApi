package ua.com.bookapi.features.books.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.com.bookapi.core.utils.emptyUiText
import ua.com.bookapi.features.books.domain.BookRepository

class CategoryDetailViewModel(
    private val repository: BookRepository,
    private val categoryId: Int,
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(CategoryDetailState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                init()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CategoryDetailState()
        )

    fun onAction(action: CategoryDetailAction) {
        when (action) {
            CategoryDetailAction.DismissDialog -> {
                _state.update { it.copy(error = emptyUiText) }

            }
            else -> Unit
        }
    }

    private fun init() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            repository.getBooks(categoryId).collect { res ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        books = res
                    )
                }
            }
        }

    }

}