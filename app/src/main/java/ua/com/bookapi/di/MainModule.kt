package ua.com.bookapi.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ua.com.bookapi.MainViewModel

val MainModule = module {
    viewModelOf(::MainViewModel)
}