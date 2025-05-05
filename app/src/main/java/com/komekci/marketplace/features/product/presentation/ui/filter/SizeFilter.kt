package com.komekci.marketplace.features.product.presentation.ui.filter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.komekci.marketplace.LocalStrings
import com.primex.core.VerticalGrid

@Composable
fun SizeFilter(modifier: Modifier = Modifier) {
    val strings = LocalStrings.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = strings.sizes, style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.W700
            ),
            color = Color(0xFF2F313F)
        )

        VerticalGrid(
            columns = 6,
        ) {
            repeat(12) {
                val selected = remember {
                    mutableStateOf(false)
                }
                SizeItem(
                    modifier = Modifier.padding(end = if ((it + 1) % 6 == 0) 0.dp else 12.dp, top = 12.dp),
                    text = "XL",
                    selected = selected.value,
                    isEmpty = it == 10 || it == 5,
                    onClick = {
                        selected.value = !selected.value
                    }
                )
            }
        }
    }
}