package ua.com.bookapi.core.network.books

import ua.com.bookapi.core.network.books.dto.Response
import ua.com.bookapi.core.utils.responses.DataError
import ua.com.bookapi.core.utils.responses.Results

interface BookService {
    suspend fun getCategories() : Results<Response, DataError.Network>

}