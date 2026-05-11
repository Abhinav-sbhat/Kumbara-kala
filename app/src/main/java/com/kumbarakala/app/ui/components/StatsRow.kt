package com.kumbarakala.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kumbarakala.app.data.model.ArtisanStats
import com.kumbarakala.app.ui.theme.GradientTerracottaEnd
import com.kumbarakala.app.ui.theme.GradientTerracottaStart
import com.kumbarakala.app.ui.theme.Terracotta
import com.kumbarakala.app.ui.theme.TerracottaLight
import com.kumbarakala.app.ui.theme.WarmCream

@Composable
fun StatsRow(
    stats: ArtisanStats,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatItem(
            value = stats.productsCount.toString(),
            label = "Products",
            emoji = "🏺",
            modifier = Modifier.weight(1f)
        )
        StatItem(
            value = stats.cardsGenerated.toString(),
            label = "Cards",
            emoji = "🎨",
            modifier = Modifier.weight(1f)
        )
        StatItem(
            value = stats.cardsShared.toString(),
            label = "Shared",
            emoji = "📤",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun StatItem(
    value: String,
    label: String,
    emoji: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .shadow(6.dp, RoundedCornerShape(16.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        GradientTerracottaStart,
                        GradientTerracottaEnd
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = emoji, fontSize = 24.sp)
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = WarmCream
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = WarmCream.copy(alpha = 0.8f)
        )
    }
}
