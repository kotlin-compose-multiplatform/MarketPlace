package com.komekci.marketplace.features.home.presentation.ui.category

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.komekci.marketplace.R
import com.komekci.marketplace.core.locale.translateValue
import com.komekci.marketplace.features.home.domain.model.CategoryEntity
import com.komekci.marketplace.features.home.domain.model.testCategoryEntity
import com.komekci.marketplace.ui.app.ImageLoader

@Preview
@Composable
fun CategoryItem(
    item: CategoryEntity = testCategoryEntity,
    modifier: Modifier = Modifier,
    isPreview: Boolean = false
) {
    Column(
        modifier = modifier
            .background(Color(0xFF767680).copy(alpha = 0.2f), shape = RoundedCornerShape(10.dp))
            .padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth().background(
            color = Color.White,
            shape = RoundedCornerShape(
                topEnd = 6.dp,
                topStart = 6.dp
            )
        ).padding(4.dp), contentAlignment = Alignment.Center) {
            ImageLoader(
                url = item.image, modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(80.dp)
                    .clip(RoundedCornerShape(4.dp))
            )

            Icon(
                painter = painterResource(R.drawable.outword),
                contentDescription = "arrow",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .background(
                        color = Color.Black.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(3.dp)
                    .size(18.dp)
            )
        }


        Column(
            modifier = Modifier.fillMaxWidth().background(
                color = Color.White,
                shape = RoundedCornerShape(
                    bottomEnd = 6.dp,
                    bottomStart = 6.dp
                )
            ).padding(4.dp)
        ) {
            Text(
                text = if(isPreview) "Test" else translateValue(instance = item, property = "title"),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.W400
                ),
                color = Color(0xFF0F1E3C),
                maxLines = 2,
                minLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "${item.totalProducts} Products",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.W400,
                    fontSize = 11.sp
                ),
                color = Color(0xFF64748B)
            )
        }
    }
}