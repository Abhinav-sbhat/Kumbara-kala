package com.kumbarakala.app

import android.app.Application
import com.kumbarakala.app.data.local.DatabaseSeeder
import com.kumbarakala.app.data.repository.AuthRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class KumbaraKalaApp : Application() {

    @Inject
    lateinit var databaseSeeder: DatabaseSeeder

    @Inject
    lateinit var authRepository: AuthRepository

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        seedDatabaseAndAutoLogin()
    }

    /**
     * Seeds the database with sample data on first launch and auto-logs in the demo user.
     */
    private fun seedDatabaseAndAutoLogin() {
        applicationScope.launch {
            // Seed database with sample products if empty
            databaseSeeder.seedIfEmpty()

            // Auto-login the demo user if not already logged in
            val currentUser = authRepository.getCurrentArtisanId()
            if (currentUser == null) {
                authRepository.login(
                    DatabaseSeeder.DEMO_EMAIL,
                    DatabaseSeeder.DEMO_PASSWORD
                )
            }
        }
    }
}
