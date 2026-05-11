package com.kumbarakala.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kumbarakala.app.data.local.dao.ArtisanDao
import com.kumbarakala.app.data.local.dao.ProductDao
import com.kumbarakala.app.data.local.dao.StoryCardDao
import com.kumbarakala.app.data.model.Artisan
import com.kumbarakala.app.data.model.Product
import com.kumbarakala.app.data.model.StoryCard

@Database(
    entities = [Artisan::class, Product::class, StoryCard::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun artisanDao(): ArtisanDao
    abstract fun productDao(): ProductDao
    abstract fun storyCardDao(): StoryCardDao
}
