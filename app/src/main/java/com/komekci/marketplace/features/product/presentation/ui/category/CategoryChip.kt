package com.komekci.marketplace.features.product.presentation.ui.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CategoryChip(
    modifier: Modifier = Modifier,
    title: String,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    val color = if (selected) {
        MaterialTheme.colorScheme.primary
    } else {
        Color(0x0D00003B)
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(3.dp))
            .background(color)
            .clickable {
                onClick()
            }
            .padding(6.dp)
    ) {
        Text(
            text = title,
            color = if (selected) Color.White else Color(0xFF1C2024),
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W400)
        )
    }
}