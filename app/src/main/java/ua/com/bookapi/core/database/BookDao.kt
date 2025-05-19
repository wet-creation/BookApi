package ua.com.bookapi.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ua.com.bookapi.core.database.entity.BookEntity
import ua.com.bookapi.core.database.entity.CategoryEntity

@Dao
interface BookDao {

    @Query("DELETE FROM category")
    suspend fun deleteAll()

    @Query("SELECT * FROM category")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM book WHERE category_id=:categoryId")
    fun getAllBooksByCategory(categoryId: Int): Flow<List<BookEntity>>

    @Insert
    suspend fun insertAllCategories(vararg categoryEntities: CategoryEntity)

    @Insert
    suspend fun insertAllBooks(vararg categoryEntities: BookEntity)

}