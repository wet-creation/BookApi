package ua.com.bookapi.features.auth.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ua.com.bookapi.features.auth.data.AccountService
import ua.com.bookapi.features.auth.data.FirebaseAccountService
import ua.com.bookapi.features.auth.presentation.AuthViewModel

val AuthModule = module {
    singleOf(::FirebaseAccountService).bind<AccountService>()
    viewModelOf(::AuthViewModel)
}