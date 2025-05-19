package ua.com.bookapi.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ua.com.bookapi.core.database.entity.BookEntity
import ua.com.bookapi.core.database.entity.CategoryEntity

@Database(entities = [BookEntity::class, CategoryEntity::class], version = 1)
abstract class RoomBookApi : RoomDatabase() {
    abstract fun bookDao(): BookDao
}