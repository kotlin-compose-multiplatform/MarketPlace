package com.komekci.marketplace.features.home.presentation.ui.discount

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.komekci.marketplace.features.favorite.domain.model.FavoriteType
import com.komekci.marketplace.features.home.domain.model.DiscountEntity
import com.komekci.marketplace.state.LocalProductFilter
import com.komekci.marketplace.ui.app.FavoriteButton
import com.komekci.marketplace.ui.app.ImageLoader

@Composable
fun DiscountItem(
    modifier: Modifier = Modifier,
    item: DiscountEntity,
    onClick: () -> Unit
) {
    val productFilter = LocalProductFilter.current
    Box(modifier = modifier.clickable {
        productFilter.value = productFilter.value.copy(
            storeId = listOf(item.id.toInt()),
            discount = true
        )
        onClick()
    }) {
        ImageLoader(
            url = item.image,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .height(88.dp),
            contentScale = ContentScale.Crop
        )
        FavoriteButton(
            id = item.id, type = FavoriteType.STORE, modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(y = (-15).dp, x = 12.dp)
        ) {

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(Color.White.copy(alpha = 0.8f))
                .padding(3.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = item.title_tm,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.W700
                ),
                color = Color(0xFF1C2024)
            )
        }
    }
}