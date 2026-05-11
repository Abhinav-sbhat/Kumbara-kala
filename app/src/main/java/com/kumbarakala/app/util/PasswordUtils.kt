package com.kumbarakala.app.util

import java.security.MessageDigest

/**
 * Utility for hashing and verifying passwords using SHA-256.
 */
object PasswordUtils {

    /**
     * Hash a plain-text password using SHA-256.
     */
    fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(password.toByteArray(Charsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    /**
     * Verify a password against a stored hash.
     */
    fun verifyPassword(password: String, storedHash: String): Boolean {
        return hashPassword(password) == storedHash
    }
}
