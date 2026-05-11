package com.kumbarakala.app.navigation

import kotlinx.serialization.Serializable

/**
 * Type-safe navigation destinations for the app.
 */

@Serializable
object Auth

@Serializable
object Home

@Serializable
object AddProduct

@Serializable
data class ProductDetail(val productId: Int)

@Serializable
data class CardPreview(val cardId: Int)

@Serializable
object MyCards

@Serializable
object Profile
