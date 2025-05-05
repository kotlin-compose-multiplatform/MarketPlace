package com.komekci.marketplace.features.product.presentation.ui.filter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.core.locale.translateValue
import com.komekci.marketplace.features.home.data.entity.GetFilter
import com.komekci.marketplace.state.LocalProductFilter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDialog(open: Boolean = true, data: GetFilter?, onDismiss: () -> Unit = {}) {
    val strings = LocalStrings.current
    val startPrice = remember {
        mutableStateOf("")
    }
    val endPrice = remember {
        mutableStateOf("")
    }
    val productFilter = LocalProductFilter.current
    if (open && data != null) {
        val state = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = state,
            shape = RoundedCornerShape(0.dp),
            containerColor = Color.White,
            scrimColor = Color.Transparent,
            dragHandle = {},
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(18.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(onClick = onDismiss) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = null,
                                tint = Color(0xFF1A1A1A),
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        Text(
                            text = strings.filter,
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.W500),
                            color = Color(0xFF1A1A1A)
                        )

                        Text(
                            text = strings.clear,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400),
                            color = Color(0xFF1A1A1A),
                            modifier = Modifier.clickable {
                                onDismiss()
                                productFilter.value = productFilter.value.copy(
                                    price_lte = 0.0,
                                    price_gte = 0.0,
                                    categoryId = emptyList(),
                                    catalogId = emptyList(),
                                    discount = false,
                                    brandId = emptyList()
                                )
                            }
                        )
                    }
                    PriceSelect(
                        startPrice = startPrice.value,
                        endPrice = endPrice.value,
                        onEndChange = {
                            endPrice.value = it
                        },
                        onStartChange = {
                            startPrice.value = it
                        }
                    )
                    FilterShops(
                        title = strings.catalog,
                        list = data.catalog?.map {
                            translateValue(
                                instance = it.name,
                                property = "",
                                prefix = ""
                            )
                        },
                        selected = productFilter.value.catalogId,
                        values = data.catalog?.map {
                            it.id.toString()
                        },
                        onChecked = { checked, value ->
                            productFilter.value = productFilter.value.copy(
                                catalogId = if (checked) productFilter.value.catalogId.plus(value) else productFilter.value.catalogId.minus(
                                    value
                                )
                            )
                        }
                    )
                    FilterShops(
                        title = strings.category,
                        list = data.category?.map {
                            translateValue(
                                instance = it.name,
                                property = "",
                                prefix = ""
                            )
                        },
                        selected = productFilter.value.categoryId,
                        values = data.category?.map {
                            it.id.toString()
                        },
                        onChecked = { checked, value ->
                            productFilter.value = productFilter.value.copy(
                                categoryId = if (checked) productFilter.value.categoryId.plus(value) else productFilter.value.categoryId.minus(
                                    value
                                )
                            )
                        }
                    )
                    FilterShops(
                        title = strings.subCategory,
                        list = data.subCatalog?.map {
                            translateValue(
                                instance = it.name,
                                property = "",
                                prefix = ""
                            )
                        },
                        values = data.subCatalog?.map {
                            it.id.toString()
                        },
                        onChecked = { checked, value ->

                        }
                    )
                    FilterShops(
                        list = data.store?.map { it.name },
                        values = data.store?.map {
                            it.id.toString()
                        },
                        selected = productFilter.value.storeId.map { it.toString() },
                        onChecked = { checked, value ->
                            productFilter.value = productFilter.value.copy(
                                storeId = if (checked) productFilter.value.storeId.plus(value.toInt()) else productFilter.value.storeId.minus(
                                    value.toInt()
                                )
                            )
                        }
                    )
//                    SizeFilter()
//                    AgeFilter()
                    BrandFilter(list = data.brand)
                    Spacer(modifier = Modifier.height(22.dp))
                    Box(Modifier.padding(horizontal = 16.dp)) {
                        Button(
                            onClick = {
                                productFilter.value = productFilter.value.copy(
                                    price_gte = try {
                                        startPrice.value.toDouble()
                                    } catch (_: Exception) {
                                        null
                                    },
                                    price_lte = try {
                                        endPrice.value.toDouble()
                                    } catch (_: Exception) {
                                        null
                                    }
                                )
                                onDismiss()
                            }, colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(
                                text = strings.acceptFilter,
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(22.dp))
                }
            }
        }
    }
}