package com.kumbarakala.app.navigation

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.kumbarakala.app.ui.screens.*
import com.kumbarakala.app.ui.theme.DeepBrown
import com.kumbarakala.app.ui.theme.Terracotta
import com.kumbarakala.app.ui.theme.WarmCream
import com.kumbarakala.app.ui.viewmodel.*
import com.kumbarakala.app.util.ShareHelper

data class BottomNavItem(val label: String, val icon: ImageVector, val route: Any)

val bottomNavItems = listOf(
    BottomNavItem("Catalog", Icons.Default.Home, Home),
    BottomNavItem("My Cards", Icons.Default.Style, MyCards),
    BottomNavItem("Profile", Icons.Default.Person, Profile)
)

@Composable
fun KumbaraNavGraph(context: Context, isLoggedIn: Boolean, onLogout: () -> Unit) {
    val navController = rememberNavController()
    val startDestination: Any = if (isLoggedIn) Home else Auth

    NavHost(navController = navController, startDestination = startDestination) {
        composable<Auth> {
            val viewModel: AuthViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            AuthScreen(viewModel = viewModel)
            LaunchedEffect(uiState.isLoggedIn) {
                if (uiState.isLoggedIn) {
                    navController.navigate(Home) { popUpTo<Auth> { inclusive = true } }
                }
            }
        }

        composable<Home> {
            val viewModel: CatalogViewModel = hiltViewModel()
            BottomNavScaffold(currentRoute = "home", navController = navController) {
                HomeScreen(viewModel = viewModel,
                    onProductClick = { navController.navigate(ProductDetail(it)) },
                    onAddProduct = { navController.navigate(AddProduct) })
            }
        }

        composable<MyCards> {
            val viewModel: MyCardsViewModel = hiltViewModel()
            BottomNavScaffold(currentRoute = "my_cards", navController = navController) {
                MyCardsScreen(viewModel = viewModel,
                    onCardClick = { navController.navigate(CardPreview(it)) })
            }
        }

        composable<Profile> {
            val viewModel: ProfileViewModel = hiltViewModel()
            BottomNavScaffold(currentRoute = "profile", navController = navController) {
                ProfileScreen(viewModel = viewModel, onLogout = {
                    navController.navigate(Auth) { popUpTo(0) { inclusive = true } }
                })
            }
        }

        composable<AddProduct> {
            val viewModel: AddProductViewModel = hiltViewModel()
            AddProductScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }

        composable<ProductDetail> { entry ->
            val route: ProductDetail = entry.toRoute()
            val viewModel: ProductDetailViewModel = hiltViewModel()
            ProductDetailScreen(viewModel = viewModel, productId = route.productId,
                onBack = { navController.popBackStack() },
                onCardPreview = { navController.navigate(CardPreview(it)) })
        }

        composable<CardPreview> { entry ->
            val route: CardPreview = entry.toRoute()
            val viewModel: MyCardsViewModel = hiltViewModel()
            val cards by viewModel.cards.collectAsStateWithLifecycle()
            val card = cards.find { it.id == route.cardId }
            CardPreviewScreen(
                cardImagePath = card?.cardImagePath ?: "",
                onBack = { navController.popBackStack() },
                onShareWhatsApp = {
                    card?.let { ShareHelper.shareToWhatsApp(context, it.cardImagePath, "Handcrafted with love by Kumbara-Kala! 🏺") }
                },
                onShareOther = {
                    card?.let { ShareHelper.shareToOtherApps(context, it.cardImagePath, "Handcrafted with love by Kumbara-Kala! 🏺") }
                },
                onSaveGallery = {
                    card?.let { ShareHelper.saveToGallery(context, it.cardImagePath, "KumbaraKala") }
                }
            )
        }
    }
}

@Composable
fun BottomNavScaffold(
    currentRoute: String,
    navController: androidx.navigation.NavHostController,
    content: @Composable () -> Unit
) {
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = WarmCream,
                tonalElevation = 8.dp,
                modifier = Modifier.shadow(8.dp)
            ) {
                val items = listOf(
                    Triple("Catalog", Icons.Default.Home, "home"),
                    Triple("My Cards", Icons.Default.Style, "my_cards"),
                    Triple("Profile", Icons.Default.Person, "profile")
                )
                items.forEach { (label, icon, route) ->
                    val isSelected = currentRoute == route
                    NavigationBarItem(
                        icon = { Icon(icon, label) },
                        label = {
                            Text(
                                label,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        selected = isSelected,
                        onClick = {
                            if (currentRoute != route) {
                                val dest: Any = when (route) {
                                    "home" -> Home
                                    "my_cards" -> MyCards
                                    "profile" -> Profile
                                    else -> Home
                                }
                                navController.navigate(dest) {
                                    popUpTo<Home> { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Terracotta,
                            selectedTextColor = Terracotta,
                            unselectedIconColor = DeepBrown.copy(alpha = 0.4f),
                            unselectedTextColor = DeepBrown.copy(alpha = 0.4f),
                            indicatorColor = Terracotta.copy(alpha = 0.12f)
                        )
                    )
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier.padding(padding)
        ) {
            content()
        }
    }
}
