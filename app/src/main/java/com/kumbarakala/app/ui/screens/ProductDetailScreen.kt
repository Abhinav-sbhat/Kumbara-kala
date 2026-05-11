package com.kumbarakala.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kumbarakala.app.ui.components.ErrorDialog
import com.kumbarakala.app.ui.components.LoadingOverlay
import com.kumbarakala.app.ui.components.TemplateSelector
import com.kumbarakala.app.ui.theme.*
import com.kumbarakala.app.ui.viewmodel.ProductDetailViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel,
    productId: Int,
    onBack: () -> Unit,
    onCardPreview: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(productId) { viewModel.loadProduct(productId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.product?.name ?: "Product", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.deleteProduct(); onBack() }) {
                        Icon(Icons.Default.Delete, "Delete", tint = WarmCream)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Terracotta, titleContentColor = WarmCream,
                    navigationIconContentColor = WarmCream
                )
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                val product = uiState.product
                if (product != null) {
                    // Hero Photo with gradient overlay
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(320.dp)
                            .background(SoftBeige),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        if (product.photoPath.isNotEmpty() && File(product.photoPath).exists()) {
                            AsyncImage(
                                model = File(product.photoPath), contentDescription = product.name,
                                contentScale = ContentScale.Crop, modifier = Modifier.matchParentSize()
                            )
                        } else {
                            Box(modifier = Modifier.matchParentSize(), contentAlignment = Alignment.Center) {
                                Text("🏺", style = MaterialTheme.typography.displayLarge)
                            }
                        }

                        // Gradient overlay at bottom
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .align(Alignment.BottomCenter)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            android.graphics.Color.TRANSPARENT.let { androidx.compose.ui.graphics.Color.Transparent },
                                            DeepBrown.copy(alpha = 0.7f)
                                        )
                                    )
                                )
                        )

                        // Price badge on photo
                        if (product.price.isNotEmpty()) {
                            Surface(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(12.dp),
                                shape = RoundedCornerShape(12.dp),
                                color = Gold.copy(alpha = 0.9f)
                            ) {
                                Text(
                                    product.price,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepBrownDark
                                )
                            }
                        }
                    }

                    Column(modifier = Modifier.padding(16.dp)) {
                        // Name + Category
                        Text(
                            product.name,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = DeepBrown
                        )
                        Spacer(Modifier.height(6.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Terracotta.copy(alpha = 0.12f)
                        ) {
                            Text(
                                product.category.replaceFirstChar { it.uppercase() },
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 5.dp),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = Terracotta
                            )
                        }
                        Spacer(Modifier.height(14.dp))
                        if (product.description.isNotEmpty()) {
                            Text(
                                product.description,
                                style = MaterialTheme.typography.bodyLarge,
                                color = DeepBrown.copy(alpha = 0.8f)
                            )
                            Spacer(Modifier.height(12.dp))
                        }

                        // Divider — Story Card Generator
                        Spacer(Modifier.height(16.dp))
                        HorizontalDivider(color = SoftBeige, thickness = 2.dp)
                        Spacer(Modifier.height(20.dp))

                        // Generate section header
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("✨", style = MaterialTheme.typography.headlineSmall)
                            Spacer(Modifier.width(8.dp))
                            Column {
                                Text(
                                    "Generate Story Card",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepBrown
                                )
                                Text(
                                    "AI-powered marketing card",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = DeepBrown.copy(alpha = 0.5f)
                                )
                            }
                        }
                        Spacer(Modifier.height(16.dp))

                        // Template Selector
                        TemplateSelector(
                            selectedTemplate = uiState.selectedTemplate,
                            onTemplateSelected = { viewModel.setTemplate(it) }
                        )
                        Spacer(Modifier.height(20.dp))

                        // Generate Button
                        Button(
                            onClick = { viewModel.generateCard() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp)
                                .shadow(if (uiState.isGenerating) 0.dp else 8.dp, RoundedCornerShape(14.dp)),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Terracotta),
                            enabled = !uiState.isGenerating
                        ) {
                            if (uiState.isGenerating) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = WarmCream,
                                    strokeWidth = 3.dp
                                )
                                Spacer(Modifier.width(10.dp))
                                Text("Generating...", color = WarmCream, fontWeight = FontWeight.SemiBold)
                            } else {
                                Text("🎨", style = MaterialTheme.typography.titleMedium)
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    "Generate Card",
                                    color = WarmCream,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // Card Preview
                        val cardBitmap = uiState.cardBitmap
                        if (cardBitmap != null) {
                            Spacer(Modifier.height(24.dp))

                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                color = SoftBeige.copy(alpha = 0.5f)
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        "📋 Card Preview",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = DeepBrown
                                    )
                                    Spacer(Modifier.height(12.dp))

                                    // Card image with shadow
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .shadow(12.dp, RoundedCornerShape(14.dp))
                                            .clip(RoundedCornerShape(14.dp))
                                    ) {
                                        androidx.compose.foundation.Image(
                                            bitmap = cardBitmap.asImageBitmap(),
                                            contentDescription = "Story Card",
                                            modifier = Modifier.fillMaxWidth(),
                                            contentScale = ContentScale.FillWidth
                                        )
                                    }
                                }
                            }

                            Spacer(Modifier.height(16.dp))

                            // WhatsApp Share Button
                            Button(
                                onClick = { viewModel.shareOnWhatsApp() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(54.dp)
                                    .shadow(6.dp, RoundedCornerShape(14.dp)),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen)
                            ) {
                                Text("💬", style = MaterialTheme.typography.titleMedium)
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    "Share on WhatsApp",
                                    color = WarmCream,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(Modifier.height(10.dp))

                            // Save & Share row
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                OutlinedButton(
                                    onClick = { viewModel.saveToGallery() },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(50.dp),
                                    shape = RoundedCornerShape(14.dp)
                                ) {
                                    Icon(Icons.Default.Download, null, Modifier.size(18.dp), tint = Terracotta)
                                    Spacer(Modifier.width(6.dp))
                                    Text("Save", fontWeight = FontWeight.SemiBold, color = Terracotta)
                                }
                                Button(
                                    onClick = { viewModel.shareToOtherApps() },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(50.dp),
                                    shape = RoundedCornerShape(14.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Terracotta)
                                ) {
                                    Icon(Icons.Default.Share, null, Modifier.size(18.dp), tint = WarmCream)
                                    Spacer(Modifier.width(6.dp))
                                    Text("Share", fontWeight = FontWeight.SemiBold, color = WarmCream)
                                }
                            }
                        }
                        Spacer(Modifier.height(32.dp))
                    }
                }
            }
            LoadingOverlay(isLoading = uiState.isLoading)
            uiState.error?.let { ErrorDialog(it) { viewModel.clearError() } }
        }
    }
}
