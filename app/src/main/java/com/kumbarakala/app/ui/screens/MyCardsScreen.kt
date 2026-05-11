package com.kumbarakala.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kumbarakala.app.data.model.StoryCard
import com.kumbarakala.app.ui.components.EmptyState
import com.kumbarakala.app.ui.theme.*
import com.kumbarakala.app.ui.viewmodel.MyCardsViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCardsScreen(
    viewModel: MyCardsViewModel,
    onCardClick: (Int) -> Unit
) {
    val cards by viewModel.cards.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        // Gradient header
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
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Column {
                Text(
                    "My Cards",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = WarmCream
                )
                Text(
                    "${cards.size} ${if (cards.size == 1) "card" else "cards"} created",
                    style = MaterialTheme.typography.bodySmall,
                    color = WarmCream.copy(alpha = 0.7f)
                )
            }
        }

        if (cards.isEmpty()) {
            EmptyState(
                emoji = "🎨",
                title = "No cards yet",
                subtitle = "Go to a product and generate your first story card!",
                modifier = Modifier.weight(1f)
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                itemsIndexed(cards, key = { _, card -> card.id }) { index, card ->
                    // Staggered entrance animation
                    var visible by remember { mutableStateOf(false) }
                    LaunchedEffect(Unit) { visible = true }

                    AnimatedVisibility(
                        visible = visible,
                        enter = fadeIn(tween(300, delayMillis = index * 80)) +
                                slideInVertically(tween(400, delayMillis = index * 80)) { it / 3 }
                    ) {
                        CardThumbnail(
                            card = card,
                            onClick = { onCardClick(card.id) },
                            onDelete = { viewModel.deleteCard(card.id) },
                            onShare = { viewModel.shareCard(card) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CardThumbnail(
    card: StoryCard,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onShare: () -> Unit
) {
    val dateFormat = remember { SimpleDateFormat("dd MMM yy", Locale.getDefault()) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = WarmCream)
    ) {
        Column {
            // Card image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.75f)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(SoftBeige),
                contentAlignment = Alignment.Center
            ) {
                if (card.cardImagePath.isNotEmpty() && File(card.cardImagePath).exists()) {
                    AsyncImage(
                        model = File(card.cardImagePath), contentDescription = "Card",
                        contentScale = ContentScale.Crop, modifier = Modifier.matchParentSize()
                    )
                } else {
                    Text("🎨", style = MaterialTheme.typography.displaySmall)
                }

                // Template style badge
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = Terracotta.copy(alpha = 0.85f)
                ) {
                    Text(
                        text = card.templateStyle,
                        style = MaterialTheme.typography.labelSmall,
                        color = WarmCream,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            // Info section
            Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)) {
                // Date & shares
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        dateFormat.format(Date(card.createdAt)),
                        style = MaterialTheme.typography.labelSmall,
                        color = DeepBrown.copy(alpha = 0.5f)
                    )
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Terracotta.copy(alpha = 0.1f)
                    ) {
                        Text(
                            "📤 ${card.shareCount}",
                            style = MaterialTheme.typography.labelSmall,
                            color = Terracotta,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                // Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // WhatsApp share
                    Surface(
                        onClick = onShare,
                        shape = CircleShape,
                        color = WhatsAppGreen.copy(alpha = 0.12f),
                        modifier = Modifier.size(34.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                            Text("💬", style = MaterialTheme.typography.labelMedium)
                        }
                    }
                    Spacer(Modifier.width(6.dp))
                    // Delete
                    Surface(
                        onClick = onDelete,
                        shape = CircleShape,
                        color = ErrorRed.copy(alpha = 0.1f),
                        modifier = Modifier.size(34.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                            Icon(Icons.Default.Delete, "Delete",
                                Modifier.size(16.dp), tint = ErrorRed)
                        }
                    }
                }
            }
        }
    }
}
