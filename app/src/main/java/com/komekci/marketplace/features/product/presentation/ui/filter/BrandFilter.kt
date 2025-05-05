package com.komekci.marketplace.features.product.presentation.ui.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.komekci.marketplace.features.home.data.entity.Brand
import com.komekci.marketplace.state.LocalProductFilter

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BrandFilter(
    list: List<Brand>?
) {
    list?.let {
        val productFilter = LocalProductFilter.current
        val strings = LocalStrings.current
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = strings.brands, style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W700
                ),
                color = Color(0xFF2F313F)
            )
            Spacer(modifier = Modifier.height(12.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                repeat(it.size) { index->
                    val checked = remember {
                        mutableStateOf(productFilter.value.brandId.contains(it[index].id.toString()))
                    }
                    FilterChip(
                        checked = checked.value,
                        text = it[index].name
                    ) {
                        if(checked.value) {
                            productFilter.value = productFilter.value.copy(
                                brandId = productFilter.value.brandId.minus(it[index].id.toString())
                            )
                        } else {
                            productFilter.value = productFilter.value.copy(
                                brandId = productFilter.value.brandId.plus(it[index].id.toString())
                            )
                        }
                        checked.value = !checked.value
                    }
                }
            }
        }
    }
}