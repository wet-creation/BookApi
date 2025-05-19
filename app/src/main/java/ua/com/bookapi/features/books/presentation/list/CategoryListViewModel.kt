package ua.com.bookapi.features.books.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.com.bookapi.core.utils.asErrorUiText
import ua.com.bookapi.core.utils.emptyUiText
import ua.com.bookapi.core.utils.responses.Results
import ua.com.bookapi.features.books.domain.BookRepository

class CategoryListViewModel(
    private val repository: BookRepository
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(CategoryListState())
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
            initialValue = CategoryListState()
        )

     fun onAction(action: CategoryListAction) {
        when (action) {
            CategoryListAction.DismissDialog -> _state.update { it.copy(error = emptyUiText) }
            else -> Unit
        }
    }

    private fun init() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val res = repository.fetchAll()) {
                is Results.Error -> {
                    _state.update { it.copy(error = res.asErrorUiText()) }

                }

                is Results.Success -> {
                    _state.update { it.copy(error = emptyUiText) }
                }

            }
            getAllCategories()
        }

    }

    private fun getAllCategories() {
        viewModelScope.launch {
            repository.getCategories().collect { res ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        categories = res
                    )
                }
            }
        }
    }
}