package ua.com.bookapi.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    @ColumnInfo(name = "publishing_date")
    val publishedDate: String,
)