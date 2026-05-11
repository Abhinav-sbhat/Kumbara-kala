package com.kumbarakala.app.ui.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kumbarakala.app.ui.theme.*
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardPreviewScreen(
    cardImagePath: String,
    onBack: () -> Unit,
    onShareWhatsApp: () -> Unit,
    onShareOther: () -> Unit,
    onSaveGallery: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Card Preview", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Terracotta, titleContentColor = WarmCream,
                    navigationIconContentColor = WarmCream
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(SoftBeige, WarmCream)
                    )
                )
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Card display with shadow
            if (cardImagePath.isNotEmpty() && File(cardImagePath).exists()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .shadow(16.dp, RoundedCornerShape(20.dp))
                        .clip(RoundedCornerShape(20.dp))
                        .background(WarmCream)
                ) {
                    AsyncImage(
                        model = File(cardImagePath),
                        contentDescription = "Story Card",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                    )
                }
            } else {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🎨", style = MaterialTheme.typography.displayLarge)
                        Spacer(Modifier.height(8.dp))
                        Text("Card not found", style = MaterialTheme.typography.bodyLarge,
                            color = DeepBrown.copy(alpha = 0.6f))
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // WhatsApp Share Button — Premium green
            Button(
                onClick = onShareWhatsApp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .shadow(8.dp, RoundedCornerShape(14.dp)),
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
                    onClick = onSaveGallery,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Terracotta
                    ),
                    border = ButtonDefaults.outlinedButtonBorder(enabled = true)
                ) {
                    Icon(Icons.Default.Download, null, Modifier.size(18.dp), tint = Terracotta)
                    Spacer(Modifier.width(6.dp))
                    Text("Save", fontWeight = FontWeight.SemiBold, color = Terracotta)
                }
                Button(
                    onClick = onShareOther,
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
            Spacer(Modifier.height(16.dp))
        }
    }
}
