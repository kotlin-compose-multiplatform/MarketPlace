package com.komekci.marketplace.features.product.presentation.ui.details

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.komekci.marketplace.ui.app.ImageLoader

@Composable
fun SmallPhoto(
    url: String,
    modifier: Modifier = Modifier.size(60.dp),
    selected: Boolean = false,
    onClick: () -> Unit
) {
    ImageLoader(
        url = url, modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .alpha(if(selected) 1f else 0.4f)
            .border(
                2.dp,
                if (selected) MaterialTheme.colorScheme.primary else Color(0xFFEBEBEF),
                RoundedCornerShape(1.dp)
            ).clickable { onClick() }.padding(4.dp),
        contentScale = ContentScale.FillBounds
    )
}