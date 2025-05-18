package ua.com.bookapi.features.books.domain

import kotlinx.coroutines.flow.Flow
import ua.com.bookapi.core.utils.responses.DataError
import ua.com.bookapi.core.utils.responses.EmptyDataResult
import ua.com.bookapi.features.books.domain.models.Book
import ua.com.bookapi.features.books.domain.models.Category

interface BookRepository {
    suspend fun fetchAll(): EmptyDataResult<DataError>
    suspend fun getCategories(): Flow<List<Category>>
    suspend fun getBooks(categoryId: Int): Flow<List<Book>>
}