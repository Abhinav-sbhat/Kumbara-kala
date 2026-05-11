package com.kumbarakala.app.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Repository module for Hilt.
 * Repositories use @Inject constructor directly, so no explicit bindings needed.
 * This module is kept as a placeholder for future binding needs.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule
