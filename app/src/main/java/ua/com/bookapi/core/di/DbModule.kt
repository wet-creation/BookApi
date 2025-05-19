package ua.com.bookapi.core.di

import androidx.room.Room
import org.koin.dsl.module
import ua.com.bookapi.core.database.BookDao
import ua.com.bookapi.core.database.RoomBookApi

val DbModule = module {
    single {
        Room.databaseBuilder(
            get(),
            RoomBookApi::class.java,
            "book_api_db"
        ).build()
    }
    single<BookDao> {
        val database = get<RoomBookApi>()
        database.bookDao()
    }
}