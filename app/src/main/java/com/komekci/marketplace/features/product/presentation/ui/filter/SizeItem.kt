package com.komekci.marketplace.features.product.presentation.ui.filter

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun SizeItem(
    modifier: Modifier = Modifier,
    text: String = "XL",
    selected: Boolean = false,
    isEmpty: Boolean = false,
    onClick: () -> Unit = {}
) {

    Box(
        modifier = modifier
            .height(42.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                if (selected) 1.5.dp else 0.5.dp,
                if (selected) Color(0xFF297C3B) else Color(0xFFD4D4D8),
                RoundedCornerShape(8.dp)
            )
            .clickable {
                if(isEmpty.not()) {
                    onClick()
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400),
            color = if (isEmpty) Color(0xFFB0BBC9) else Color(0xFF333333)
        )
        if(isEmpty){
            Canvas(
                modifier = modifier
                    .fillMaxSize()
            ) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                drawLine(
                    start = Offset(x = canvasWidth, y = canvasHeight),
                    end = Offset(x = 0.dp.toPx(), y = 0f),
                    color = Color(0xFFD4D4D8),
                    strokeWidth = 2.dp.toPx()
                )
            }
        }
    }

}