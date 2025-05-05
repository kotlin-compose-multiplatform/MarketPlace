package com.komekci.marketplace.features.product.presentation.ui.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.komekci.marketplace.core.locale.translateValue
import com.komekci.marketplace.features.product.domain.model.CategoryEntity
import com.komekci.marketplace.features.product.presentation.ui.product.ProductComponent
import com.komekci.marketplace.ui.navigation.Routes
import com.primex.core.VerticalGrid

@Composable
fun CategoryProduct(
    modifier: Modifier = Modifier,
    item: CategoryEntity,
    navHostController: NavHostController,
    onClick: () -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = translateValue(instance = item, property = "name"), style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W700
                ),
                color = Color(0xFF1C2024)
            )

            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color(0xFF60646C),
                modifier = Modifier.size(35.dp)
            )


        }

//        Spacer(modifier = Modifier.height(12.dp))
        VerticalGrid(
            columns = 2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            item.products.forEachIndexed { index, productsEntity ->
                ProductComponent(
                    modifier = Modifier.padding(start = if (index % 2 == 1) 12.dp else 0.dp),
                    item = productsEntity
                ) {
                    navHostController.navigate(Routes.Details.replace("{id}", productsEntity.id))
                }
            }
        }
    }
}