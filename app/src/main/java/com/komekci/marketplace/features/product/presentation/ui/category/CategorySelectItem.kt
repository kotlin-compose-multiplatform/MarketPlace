package com.komekci.marketplace.features.product.presentation.ui.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.komekci.marketplace.R

@Composable
fun CategorySelectItem(
    modifier: Modifier = Modifier,
    text: String = "",
    selected: Boolean = false,
    onClick: () -> Unit
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .clickable { onClick() }.padding(horizontal = 16.dp)) {
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if(selected) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_check_circle_outline_24),
                    contentDescription = null,
                    tint = Color(0xFF297C3B)
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                color = if (selected) Color(0xFF297C3B) else Color(0xFF3D3D3D)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(
            color = Color(0xFFE7E7E7),
        )
    }
}