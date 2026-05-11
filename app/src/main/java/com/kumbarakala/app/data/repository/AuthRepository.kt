package com.kumbarakala.app.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kumbarakala.app.data.local.dao.ArtisanDao
import com.kumbarakala.app.data.model.Artisan
import com.kumbarakala.app.util.PasswordUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "kumbara_session")

@Singleton
class AuthRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val artisanDao: ArtisanDao
) {
    companion object {
        private val LOGGED_IN_ARTISAN_ID = intPreferencesKey("logged_in_artisan_id")
    }

    /**
     * Register a new artisan with email and password.
     */
    suspend fun register(
        name: String,
        email: String,
        phone: String,
        village: String,
        password: String
    ): Result<Artisan> {
        return try {
            // Check if email already exists
            if (artisanDao.emailExists(email) > 0) {
                return Result.failure(Exception("An account with this email already exists"))
            }

            val artisan = Artisan(
                name = name,
                email = email,
                phone = phone,
                village = village,
                passwordHash = PasswordUtils.hashPassword(password),
                createdAt = System.currentTimeMillis()
            )

            val id = artisanDao.insert(artisan).toInt()
            val savedArtisan = artisan.copy(id = id)

            // Auto-login after registration
            saveSession(id)

            Result.success(savedArtisan)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Login with email and password.
     */
    suspend fun login(email: String, password: String): Result<Artisan> {
        return try {
            val passwordHash = PasswordUtils.hashPassword(password)
            val artisan = artisanDao.getByEmailAndPassword(email, passwordHash)
                ?: return Result.failure(Exception("Invalid email or password"))

            saveSession(artisan.id)
            Result.success(artisan)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get the currently logged-in artisan ID as a Flow.
     */
    fun getCurrentArtisanIdFlow(): Flow<Int?> {
        return context.dataStore.data.map { prefs ->
            val id = prefs[LOGGED_IN_ARTISAN_ID]
            if (id != null && id > 0) id else null
        }
    }

    /**
     * Get the currently logged-in artisan ID (one-shot).
     */
    suspend fun getCurrentArtisanId(): Int? {
        val prefs = context.dataStore.data.first()
        val id = prefs[LOGGED_IN_ARTISAN_ID]
        return if (id != null && id > 0) id else null
    }

    /**
     * Get the currently logged-in artisan.
     */
    suspend fun getCurrentArtisan(): Artisan? {
        val id = getCurrentArtisanId() ?: return null
        return artisanDao.getById(id)
    }

    /**
     * Logout — clear the session.
     */
    suspend fun logout() {
        context.dataStore.edit { prefs ->
            prefs.remove(LOGGED_IN_ARTISAN_ID)
        }
    }

    /**
     * Check if user is logged in.
     */
    fun isLoggedIn(): Flow<Boolean> {
        return context.dataStore.data.map { prefs ->
            val id = prefs[LOGGED_IN_ARTISAN_ID]
            id != null && id > 0
        }
    }

    private suspend fun saveSession(artisanId: Int) {
        context.dataStore.edit { prefs ->
            prefs[LOGGED_IN_ARTISAN_ID] = artisanId
        }
    }
}
