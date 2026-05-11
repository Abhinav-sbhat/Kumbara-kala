package com.kumbarakala.app.data.model

/**
 * Non-entity data class for displaying artisan statistics on the profile screen.
 */
data class ArtisanStats(
    val productsCount: Int = 0,
    val cardsGenerated: Int = 0,
    val cardsShared: Int = 0
)
