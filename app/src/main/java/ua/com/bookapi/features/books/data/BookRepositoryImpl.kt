package ua.com.bookapi.features.books.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ua.com.bookapi.core.database.BookDao
import ua.com.bookapi.core.database.entity.BookEntity
import ua.com.bookapi.core.database.entity.CategoryEntity
import ua.com.bookapi.core.network.books.BookService
import ua.com.bookapi.core.network.books.dto.BooksDto
import ua.com.bookapi.core.network.books.dto.CategoryDto
import ua.com.bookapi.core.network.books.dto.ResponseResultsDto
import ua.com.bookapi.core.utils.responses.DataError
import ua.com.bookapi.core.utils.responses.EmptyDataResult
import ua.com.bookapi.core.utils.responses.Results
import ua.com.bookapi.core.utils.responses.asEmptyDataResult
import ua.com.bookapi.features.books.domain.BookRepository
import ua.com.bookapi.features.books.domain.models.Book
import ua.com.bookapi.features.books.domain.models.Category

class BookRepositoryImpl(
    private val dao: BookDao,
    private val service: BookService,
    private val applicationScope: CoroutineScope
) : BookRepository {


    override suspend fun fetchAll(): EmptyDataResult<DataError> {
        when (val res = service.getCategories()) {
            is Results.Error -> return res.asEmptyDataResult()
            is Results.Success -> {
                val categories = res.data.results.asCategories()
                val books = categories.map { it.books }.flatten()
                applicationScope.async {
                    dao.deleteAll()
                    dao.insertAllCategories(*categories.map { it.asCategoryEntity() }
                        .toTypedArray())

                    dao.insertAllBooks(*books.map { it.asBookEntity() }
                        .toTypedArray())
                }
                return res.asEmptyDataResult()
            }
        }
    }


    override fun getCategories(): Flow<List<Category>> {
        return dao.getAllCategories().map { it.map { it.asCategory() } }
    }

    override fun getBooks(categoryId: Int): Flow<List<Book>> {
        return dao.getAllBooksByCategory(categoryId).map { it.map { it.asBook() } }

    }
}

private fun CategoryEntity.asCategory(): Category {
    return Category(
        id = id,
        name = name,
        publishedDate = publishedDate,
        books = emptyList()
    )
}

private fun BookEntity.asBook(): Book {
    return Book(
        name = name,
        description = description,
        author = author,
        categoryId = categoryId,
        bookImage = bookImage,
        productUrl = productUrl,
        rank = rank,
        publisher = publisher
    )
}

private fun Book.asBookEntity(): BookEntity {
    return BookEntity(
        id = 0,
        name = name,
        description = description,
        author = author,
        categoryId = categoryId,
        bookImage = bookImage,
        productUrl = productUrl,
        rank = rank,
        publisher = publisher
    )
}

private fun Category.asCategoryEntity(): CategoryEntity {
    return CategoryEntity(
        id = id,
        name = name,
        publishedDate = publishedDate
    )
}

private fun ResponseResultsDto.asCategories(): List<Category> {
    return lists.map { category: CategoryDto ->
        Category(
            id = category.id,
            name = category.name,
            publishedDate = publishDate,
            books = category.books.map { it.asBook(category.id) }
        )
    }
}

private fun BooksDto.asBook(categoryId: Int): Book {
    return Book(
        name = title,
        description = description,
        author = author,
        categoryId = categoryId,
        bookImage = bookImage,
        productUrl = productUrl,
        rank = rank,
        publisher = publisher
    )
}

