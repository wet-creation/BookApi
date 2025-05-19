package ua.com.bookapi.features.books.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ua.com.bookapi.features.books.data.BookRepositoryImpl
import ua.com.bookapi.features.books.domain.BookRepository
import ua.com.bookapi.features.books.presentation.detail.CategoryDetailViewModel
import ua.com.bookapi.features.books.presentation.list.CategoryListViewModel

val BookModule = module {
    singleOf(::BookRepositoryImpl).bind<BookRepository>()
    viewModelOf(::CategoryListViewModel)
    viewModelOf(::CategoryDetailViewModel)
}