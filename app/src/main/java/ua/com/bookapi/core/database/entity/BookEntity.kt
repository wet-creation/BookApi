package ua.com.bookapi.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import ua.com.bookapi.features.books.domain.models.ImageUrl

@Entity(
    tableName = "book",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["category_id"])]
)
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
    val author: String,
    @ColumnInfo("category_id")
    val categoryId: Int,
    val bookImage: ImageUrl,
    @ColumnInfo("product_url")
    val productUrl: String,
    val rank: Int,
    val publisher: String
)