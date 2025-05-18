package ua.com.bookapi.features.books.domain

interface BookRepository {
    suspend fun fetchAll()

}