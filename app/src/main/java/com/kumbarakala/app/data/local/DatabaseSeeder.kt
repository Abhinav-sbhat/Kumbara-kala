package com.kumbarakala.app.data.local

import android.content.Context
import com.kumbarakala.app.data.model.Artisan
import com.kumbarakala.app.data.model.Product
import com.kumbarakala.app.data.model.StoryCard
import com.kumbarakala.app.util.PasswordUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Seeds the database with sample artisan, products, and story cards on first launch.
 * Checks if the database is empty before seeding to avoid duplicates.
 */
@Singleton
class DatabaseSeeder @Inject constructor(
    @ApplicationContext private val context: Context,
    private val database: AppDatabase
) {

    suspend fun seedIfEmpty() = withContext(Dispatchers.IO) {
        val artisanDao = database.artisanDao()
        val productDao = database.productDao()
        val storyCardDao = database.storyCardDao()

        // Check if data already exists
        val existingArtisan = artisanDao.getByEmail("ramesh@kumbarakala.com")
        if (existingArtisan != null) return@withContext  // Already seeded

        // ============================
        // 1. Create Demo Artisan
        // ============================
        val artisan = Artisan(
            name = "Ramesh Kumbhar",
            email = "ramesh@kumbarakala.com",
            phone = "+91 98765 43210",
            village = "Dharwad, Karnataka",
            passwordHash = PasswordUtils.hashPassword("kumbara123"),
            bio = "I am a 4th-generation clay artisan from Dharwad. My family has been crafting traditional clay products for over 100 years. I learned the art of pottery from my grandfather, who was known across North Karnataka for his exquisite water pots. Every piece I create carries the warmth of our heritage and the blessings of Mother Earth.",
            profilePhoto = "",
            experience = "25 years",
            specialty = "Traditional Water Pots & Cooking Vessels",
            createdAt = System.currentTimeMillis()
        )
        val artisanId = artisanDao.insert(artisan).toInt()

        // ============================
        // 2. Create Sample Products
        // ============================
        val products = listOf(
            Product(
                artisanId = artisanId,
                name = "Traditional Curd Setting Pot",
                category = "pots",
                description = "Handcrafted clay pot perfect for setting fresh curd. The porous clay naturally absorbs excess water, giving you thick, creamy curd every time. Used in Indian households for generations, this pot maintains the natural pH balance and adds beneficial minerals to your curd.",
                photoPath = "",
                price = "Rs. 150 - 200",
                createdAt = System.currentTimeMillis() - 86400000 * 6  // 6 days ago
            ),
            Product(
                artisanId = artisanId,
                name = "Matka Water Pot (Large)",
                category = "pots",
                description = "Large traditional clay matka for storing and naturally cooling drinking water. The micro-pores in the clay allow slow evaporation, cooling water by 5-8°C without electricity. Adds essential minerals like calcium and magnesium to your water. Capacity: 10 litres.",
                photoPath = "",
                price = "Rs. 350 - 450",
                createdAt = System.currentTimeMillis() - 86400000 * 5  // 5 days ago
            ),
            Product(
                artisanId = artisanId,
                name = "Decorative Diya Lamp Set",
                category = "lamps",
                description = "Set of 6 beautifully hand-painted clay diyas with traditional Kolam patterns. Perfect for Diwali, festivals, puja rooms, and home decoration. Each diya is uniquely crafted and painted with eco-friendly natural colours. Burns cleanly with ghee or oil.",
                photoPath = "",
                price = "Rs. 250 - 350",
                createdAt = System.currentTimeMillis() - 86400000 * 4  // 4 days ago
            ),
            Product(
                artisanId = artisanId,
                name = "Standing Oil Lamp (Agal Vilakku)",
                category = "lamps",
                description = "Elegant tall clay oil lamp inspired by the traditional South Indian Agal Vilakku design. Features a sturdy base with intricate hand-carved patterns. Ideal for temple corners, entrance halls, and festive occasions. Height: 12 inches.",
                photoPath = "",
                price = "Rs. 400 - 500",
                createdAt = System.currentTimeMillis() - 86400000 * 3  // 3 days ago
            ),
            Product(
                artisanId = artisanId,
                name = "Clay Cooking Handi",
                category = "utensils",
                description = "Traditional round-bottom clay handi for slow cooking dals, curries, and biryanis. Clay cooking retains nutrients, enhances flavour, and requires less oil. The alkaline nature of clay neutralises acidity in food, making meals healthier. Pre-seasoned and ready to use.",
                photoPath = "",
                price = "Rs. 300 - 400",
                createdAt = System.currentTimeMillis() - 86400000 * 2  // 2 days ago
            ),
            Product(
                artisanId = artisanId,
                name = "Kulhad Cup Set (12 pieces)",
                category = "utensils",
                description = "Set of 12 handmade kulhad cups — the iconic Indian clay tea cups. Drinking chai in a kulhad gives it a distinctive earthy aroma and flavour that no other cup can replicate. 100% biodegradable and eco-friendly. Single-use or reusable after washing.",
                photoPath = "",
                price = "Rs. 180 - 250",
                createdAt = System.currentTimeMillis() - 86400000  // 1 day ago
            ),
            Product(
                artisanId = artisanId,
                name = "Terracotta Wall Hanging Plate",
                category = "decor",
                description = "Hand-painted terracotta decorative plate with traditional Warli art motifs depicting village life scenes. Each plate is a unique piece of art that brings warmth and cultural richness to your walls. Comes with wall mounting hook. Diameter: 10 inches.",
                photoPath = "",
                price = "Rs. 500 - 700",
                createdAt = System.currentTimeMillis() - 43200000  // 12 hours ago
            ),
            Product(
                artisanId = artisanId,
                name = "Terracotta Garden Planter",
                category = "decor",
                description = "Beautiful hand-sculpted terracotta planter with ethnic patterns. The natural clay material provides excellent drainage and aeration for plant roots. Perfect for succulents, herbs, and small indoor plants. Chemical-free and sustainable alternative to plastic pots.",
                photoPath = "",
                price = "Rs. 250 - 350",
                createdAt = System.currentTimeMillis()
            )
        )

        val productIds = mutableListOf<Int>()
        products.forEach { product ->
            val id = productDao.insert(product).toInt()
            productIds.add(id)
        }

        // ============================
        // 3. Create Sample Story Cards
        // ============================
        val sampleCards = listOf(
            StoryCard(
                productId = productIds[0],  // Curd Pot
                artisanId = artisanId,
                benefitText = "Setting curd in a clay pot is an ancient Ayurvedic practice. The porous clay gently absorbs excess moisture, yielding thick, probiotic-rich curd. Unlike plastic or steel, clay maintains a natural pH balance that enhances the fermentation process and preserves gut-friendly bacteria.",
                healthFact = "Clay pots preserve natural probiotics in curd",
                templateStyle = "Classic",
                cardImagePath = "",
                shareCount = 12,
                createdAt = System.currentTimeMillis() - 86400000 * 3
            ),
            StoryCard(
                productId = productIds[1],  // Water Pot
                artisanId = artisanId,
                benefitText = "The ancient matka is nature's refrigerator. Its micro-porous clay body allows gradual evaporation, cooling water by up to 8°C naturally. Rich in essential minerals like calcium and magnesium, matka water boosts immunity and improves digestion — a tradition backed by modern science.",
                healthFact = "Matka water naturally adds calcium and magnesium",
                templateStyle = "Heritage",
                cardImagePath = "",
                shareCount = 8,
                createdAt = System.currentTimeMillis() - 86400000 * 2
            ),
            StoryCard(
                productId = productIds[4],  // Cooking Handi
                artisanId = artisanId,
                benefitText = "Cooking in clay is a return to our roots — and to better health. The alkaline nature of clay neutralises food acidity, while slow heat distribution retains up to 100% of nutrients lost in metal cookware. Your biryani doesn't just taste better in a handi — it IS better.",
                healthFact = "Clay cooking retains 100% more nutrients than metal",
                templateStyle = "Eco",
                cardImagePath = "",
                shareCount = 5,
                createdAt = System.currentTimeMillis() - 86400000
            )
        )

        sampleCards.forEach { card ->
            storyCardDao.insert(card)
        }
    }

    companion object {
        /**
         * Demo login credentials:
         * Email: ramesh@kumbarakala.com
         * Password: kumbara123
         */
        const val DEMO_EMAIL = "ramesh@kumbarakala.com"
        const val DEMO_PASSWORD = "kumbara123"
    }
}
