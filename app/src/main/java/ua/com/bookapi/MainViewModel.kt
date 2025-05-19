package ua.com.bookapi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ua.com.bookapi.features.auth.data.AccountService

class MainViewModel(
    private val service: AccountService
) : ViewModel() {
    var state by mutableStateOf<Boolean?>(null)
        private set

    init {
        viewModelScope.launch {
            service.hasUser.collect { isAuth ->
                state = isAuth
            }

        }
    }
}