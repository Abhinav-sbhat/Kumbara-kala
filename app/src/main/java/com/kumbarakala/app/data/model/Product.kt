package com.kumbarakala.app.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "products",
    foreignKeys = [
        ForeignKey(
            entity = Artisan::class,
            parentColumns = ["id"],
            childColumns = ["artisan_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("artisan_id")]
)
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "artisan_id")
    val artisanId: Int = 0,

    @ColumnInfo(name = "name")
    val name: String = "",

    @ColumnInfo(name = "category")
    val category: String = "",  // "pots" / "lamps" / "utensils" / "decor"

    @ColumnInfo(name = "description")
    val description: String = "",

    @ColumnInfo(name = "photo_path")
    val photoPath: String = "",  // local file path

    @ColumnInfo(name = "price")
    val price: String = "",

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)
