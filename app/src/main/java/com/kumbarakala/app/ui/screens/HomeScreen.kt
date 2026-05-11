package com.kumbarakala.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kumbarakala.app.ui.components.CategoryChipRow
import com.kumbarakala.app.ui.components.EmptyState
import com.kumbarakala.app.ui.components.ProductCard
import com.kumbarakala.app.ui.theme.DeepBrown
import com.kumbarakala.app.ui.theme.GradientTerracottaEnd
import com.kumbarakala.app.ui.theme.GradientTerracottaStart
import com.kumbarakala.app.ui.theme.Terracotta
import com.kumbarakala.app.ui.theme.WarmCream
import com.kumbarakala.app.ui.viewmodel.CatalogViewModel

private val categories = listOf("All", "Pots", "Lamps", "Utensils", "Decor")

@Composable
fun HomeScreen(
    viewModel: CatalogViewModel,
    onProductClick: (Int) -> Unit,
    onAddProduct: () -> Unit
) {
    val products by viewModel.products.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val artisanName by viewModel.artisanName.collectAsState()

    Scaffold(
        topBar = {
            // Custom gradient top bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                GradientTerracottaStart,
                                GradientTerracottaEnd
                            )
                        )
                    )
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "🏺",
                                fontSize = 28.sp
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "Kumbara-Kala",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = WarmCream
                            )
                        }
                        if (artisanName.isNotEmpty()) {
                            Text(
                                text = "Welcome, $artisanName ✨",
                                style = MaterialTheme.typography.bodySmall,
                                color = WarmCream.copy(alpha = 0.8f)
                            )
                        }
                    }

                    // Product count badge
                    if (products.isNotEmpty()) {
                        androidx.compose.material3.Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = WarmCream.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = "${products.size} items",
                                style = MaterialTheme.typography.labelSmall,
                                color = WarmCream,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            )
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddProduct,
                containerColor = Terracotta,
                contentColor = WarmCream,
                modifier = Modifier.shadow(12.dp, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Product", modifier = Modifier.size(28.dp))
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Category Chips
            CategoryChipRow(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { viewModel.filterByCategory(it) }
            )

            if (products.isEmpty()) {
                Box(modifier = Modifier.weight(1f)) {
                    EmptyState(
                        emoji = "🏺",
                        title = "No products yet",
                        subtitle = "Tap the + button to add your first clay product!"
                    )
                }
            } else {
                // Product Grid — 2 columns
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(products, key = { it.id }) { product ->
                        ProductCard(
                            product = product,
                            onClick = { onProductClick(product.id) }
                        )
                    }
                }
            }
        }
    }
}
