# 🏺 Kumbara-Kala

> **AI-Powered Marketing Card Generator for Rural Artisans**

Kumbara-Kala (ಕುಂಬಾರ ಕಲೆ — "The Potter's Art") is an **offline-first Android application** designed to empower rural Indian potters and clay artisans. It enables them to catalog their handcrafted products, generate AI-powered marketing story cards, and share them directly via WhatsApp and social media — all without requiring a constant internet connection.

---

## ✨ Features

| Feature | Description |
|---|---|
| **🔐 Auth System** | Local registration & login with hashed passwords and DataStore session management |
| **📦 Product Catalog** | Add, browse, and manage clay products with photos across categories (Pots, Lamps, Utensils, Decor) |
| **🤖 AI Story Cards** | Generate marketing cards using **Gemini 1.5 Flash** — includes product benefits and health facts |
| **🎨 4 Card Templates** | Classic, Modern, Heritage, and Eco — each with unique visual identity (1080×1350px, social-media optimized) |
| **📤 Social Sharing** | One-tap sharing to WhatsApp, WhatsApp Business, or any installed app via Android share sheet |
| **💾 Save to Gallery** | Export generated cards directly to device gallery |
| **📊 Artisan Profile** | Dashboard with product/card stats, editable bio, and contact information |
| **🌐 Offline-First** | Fully functional without internet — Room database for all local data |

---

## 🏗️ Architecture

The app follows **MVVM (Model-View-ViewModel)** architecture with clean separation of concerns:

```
com.kumbarakala.app/
├── data/
│   ├── local/
│   │   ├── AppDatabase.kt          # Room database (Artisan, Product, StoryCard)
│   │   ├── DatabaseSeeder.kt       # Demo data seeder for first launch
│   │   └── dao/
│   │       ├── ArtisanDao.kt
│   │       ├── ProductDao.kt
│   │       └── StoryCardDao.kt
│   ├── model/
│   │   ├── Artisan.kt              # Artisan entity (name, email, village, etc.)
│   │   ├── ArtisanStats.kt         # Stats data class
│   │   ├── Product.kt              # Product entity with FK to Artisan
│   │   └── StoryCard.kt            # Story card entity with FKs to Product & Artisan
│   └── repository/
│       ├── ArtisanRepository.kt    # Artisan CRUD operations
│       ├── AuthRepository.kt       # Login/register + DataStore session
│       ├── CardRepository.kt       # Card CRUD + image storage
│       └── ProductRepository.kt    # Product CRUD + photo management
├── di/
│   ├── DatabaseModule.kt           # Hilt: Room database provider
│   ├── GeminiModule.kt             # Hilt: Gemini GenerativeModel provider
│   └── RepositoryModule.kt         # Hilt: Repository bindings
├── engine/
│   └── CardRenderer.kt             # Canvas-based card rendering engine (4 templates)
├── navigation/
│   ├── NavGraph.kt                 # Compose Navigation with bottom nav scaffold
│   └── Screen.kt                   # Type-safe route definitions (kotlinx.serialization)
├── ui/
│   ├── components/                 # Reusable Compose components
│   │   ├── CategoryChip.kt
│   │   ├── EmptyState.kt
│   │   ├── ErrorDialog.kt
│   │   ├── LoadingOverlay.kt
│   │   ├── ProductCard.kt
│   │   ├── StatsRow.kt
│   │   └── TemplateSelector.kt
│   ├── screens/                    # Full-screen composables
│   │   ├── AuthScreen.kt           # Login / Registration
│   │   ├── HomeScreen.kt           # Product catalog grid
│   │   ├── AddProductScreen.kt     # New product form with camera
│   │   ├── ProductDetailScreen.kt  # Product details + card generation
│   │   ├── MyCardsScreen.kt        # Generated cards gallery
│   │   ├── CardPreviewScreen.kt    # Full card preview + sharing
│   │   └── ProfileScreen.kt       # Artisan profile + stats dashboard
│   ├── theme/
│   │   ├── Color.kt                # Terracotta-inspired palette
│   │   ├── Theme.kt                # Material 3 theme configuration
│   │   └── Type.kt                 # Typography system
│   └── viewmodel/
│       ├── AuthViewModel.kt
│       ├── CatalogViewModel.kt
│       ├── AddProductViewModel.kt
│       ├── ProductDetailViewModel.kt
│       ├── MyCardsViewModel.kt
│       └── ProfileViewModel.kt
├── util/
│   ├── ImageUtils.kt              # Bitmap helpers (center crop, save, decode)
│   ├── PasswordUtils.kt           # SHA-256 password hashing
│   └── ShareHelper.kt             # WhatsApp + generic share intents
├── KumbaraKalaApp.kt              # Application class (Hilt entry point, auto-seeder)
└── MainActivity.kt                # Single Activity (Compose host)
```

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| **Language** | Kotlin |
| **UI** | Jetpack Compose + Material 3 |
| **Navigation** | Compose Navigation (type-safe routes via kotlinx.serialization) |
| **Database** | Room (SQLite) — offline-first |
| **DI** | Hilt (Dagger) |
| **AI** | Google Gemini 1.5 Flash (generative-ai SDK) |
| **Image Loading** | Coil |
| **Session** | DataStore Preferences |
| **Card Rendering** | Android Canvas API (custom 1080×1350px renderer) |
| **Build** | Gradle KTS + KSP |
| **Min SDK** | 24 (Android 7.0) |
| **Target SDK** | 35 |

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio** Ladybug (2024.2+) or later
- **JDK 17**
- A **Gemini API Key** from [Google AI Studio](https://aistudio.google.com/apikey)

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/Abhinav-sbhat/Kumbara-kala.git
   cd Kumbara-kala
   ```

2. **Add your Gemini API key** to `local.properties`:
   ```properties
   GEMINI_API_KEY=your_api_key_here
   ```

3. **Sync & Build** in Android Studio — Gradle will resolve all dependencies automatically.

4. **Run** on an emulator or physical device (API 24+).

> **Note:** The app auto-seeds demo data on first launch and logs in a demo artisan, so you can explore all features immediately.

---

## 🎨 Card Templates

The `CardRenderer` engine generates **1080 × 1350 px** JPEG images (4:5 aspect ratio) optimized for WhatsApp and Instagram:

| Template | Style | Color Palette |
|---|---|---|
| **Classic** | Serif typography, double border frame | Warm cream + Terracotta |
| **Modern** | Clean sans-serif, accent line, category tag | White + Dark accents |
| **Heritage** | Gold borders, corner decorations, quoted text | Parchment + Gold |
| **Eco** | Green tones, eco badge, sustainability note | Mint green + Forest green |

Each card includes:
- Product photo (center-cropped)
- AI-generated benefit text
- Health fact badge
- Artisan name & contact info
- Kumbara-Kala watermark

---

## 📂 Database Schema

```
artisans (1) ──┬──< products (N) ──< story_cards (N)
               └──────────────────< story_cards (N)
```

- **Artisans** → id, name, email, phone, village, password_hash, bio, profile_photo, experience, specialty
- **Products** → id, artisan_id (FK), name, category, description, photo_path, price
- **StoryCards** → id, product_id (FK), artisan_id (FK), benefit_text, health_fact, template_style, card_image_path, share_count

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📜 License

This project is developed as part of an academic/internship initiative. See the repository for licensing details.

---

<p align="center">
  Made with ❤️ for Indian artisans
</p>
