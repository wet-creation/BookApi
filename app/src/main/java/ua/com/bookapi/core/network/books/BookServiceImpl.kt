package ua.com.bookapi.core.network.books

import ua.com.bookapi.core.Config
import ua.com.bookapi.core.network.HttpClientFactory
import ua.com.bookapi.core.network.books.dto.Response
import ua.com.bookapi.core.utils.get
import ua.com.bookapi.core.utils.responses.DataError
import ua.com.bookapi.core.utils.responses.Results

const val getCategories = "/overview.json"

class BookServiceImpl(private val httpClientFactory: HttpClientFactory) : BookService {
    override suspend fun getCategories(): Results<Response, DataError.Network> {
        return httpClientFactory.getClient().get(
            getCategories, queryParameters = mapOf(
                "api-key" to Config.token
            )
        )
    }
}