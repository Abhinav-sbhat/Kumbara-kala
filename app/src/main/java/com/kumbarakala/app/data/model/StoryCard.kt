package com.kumbarakala.app.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "story_cards",
    foreignKeys = [
        ForeignKey(
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["product_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Artisan::class,
            parentColumns = ["id"],
            childColumns = ["artisan_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("product_id"), Index("artisan_id")]
)
data class StoryCard(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "product_id")
    val productId: Int = 0,

    @ColumnInfo(name = "artisan_id")
    val artisanId: Int = 0,

    @ColumnInfo(name = "benefit_text")
    val benefitText: String = "",

    @ColumnInfo(name = "health_fact")
    val healthFact: String = "",

    @ColumnInfo(name = "template_style")
    val templateStyle: String = "Classic",  // "Classic" / "Modern" / "Heritage" / "Eco"

    @ColumnInfo(name = "card_image_path")
    val cardImagePath: String = "",  // local file path

    @ColumnInfo(name = "share_count")
    val shareCount: Int = 0,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)
