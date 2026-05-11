package com.kumbarakala.app.di

import android.content.Context
import androidx.room.Room
import com.kumbarakala.app.data.local.AppDatabase
import com.kumbarakala.app.data.local.dao.ArtisanDao
import com.kumbarakala.app.data.local.dao.ProductDao
import com.kumbarakala.app.data.local.dao.StoryCardDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "kumbara_kala_db"
        ).build()
    }

    @Provides
    fun provideArtisanDao(database: AppDatabase): ArtisanDao {
        return database.artisanDao()
    }

    @Provides
    fun provideProductDao(database: AppDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    fun provideStoryCardDao(database: AppDatabase): StoryCardDao {
        return database.storyCardDao()
    }
}
