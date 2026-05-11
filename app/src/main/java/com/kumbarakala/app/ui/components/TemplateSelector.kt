package com.kumbarakala.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kumbarakala.app.ui.theme.ClassicBorder
import com.kumbarakala.app.ui.theme.EcoGreen
import com.kumbarakala.app.ui.theme.HeritagGold
import com.kumbarakala.app.ui.theme.ModernAccent
import com.kumbarakala.app.ui.theme.Terracotta

data class TemplateOption(
    val name: String,
    val color: Color,
    val emoji: String,
    val description: String
)

val templateOptions = listOf(
    TemplateOption("Classic", ClassicBorder, "🏺", "Earthy tones"),
    TemplateOption("Modern", ModernAccent, "✨", "Clean & minimal"),
    TemplateOption("Heritage", HeritagGold, "🪷", "Gold accents"),
    TemplateOption("Eco", EcoGreen, "🌿", "Green & natural")
)

@Composable
fun TemplateSelector(
    selectedTemplate: String,
    onTemplateSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        templateOptions.forEach { option ->
            val isSelected = option.name == selectedTemplate

            Surface(
                onClick = { onTemplateSelected(option.name) },
                modifier = Modifier
                    .weight(1f)
                    .size(90.dp),
                shape = RoundedCornerShape(12.dp),
                color = if (isSelected) option.color.copy(alpha = 0.15f)
                else MaterialTheme.colorScheme.surface,
                border = BorderStroke(
                    width = if (isSelected) 2.dp else 1.dp,
                    color = if (isSelected) option.color
                    else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = option.emoji,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = option.name,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) option.color
                        else MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = option.description,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}
