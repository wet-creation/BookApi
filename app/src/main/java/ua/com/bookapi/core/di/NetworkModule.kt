package ua.com.bookapi.core.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ua.com.bookapi.core.network.HttpClientFactory
import ua.com.bookapi.core.network.books.BookService
import ua.com.bookapi.core.network.books.BookServiceImpl

val NetworkModule = module {
    singleOf(::HttpClientFactory)
    singleOf(::BookServiceImpl).bind<BookService>()
}