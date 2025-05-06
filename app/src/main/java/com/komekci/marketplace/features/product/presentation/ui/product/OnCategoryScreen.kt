package com.komekci.marketplace.features.product.presentation.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.komekci.marketplace.R
import com.komekci.marketplace.core.locale.translateValue
import com.komekci.marketplace.features.auth.presentation.ui.ErrorField
import com.komekci.marketplace.features.favorite.domain.model.FavoriteType
import com.komekci.marketplace.features.product.presentation.state.CategoryState
import com.komekci.marketplace.features.product.presentation.ui.category.CategoryChip
import com.komekci.marketplace.features.product.presentation.viewmodel.CategoryViewModel
import com.komekci.marketplace.state.LocalFavSettings
import com.komekci.marketplace.state.LocalProductFilter
import com.komekci.marketplace.state.LocalSettings
import com.komekci.marketplace.ui.app.AppLoading
import com.komekci.marketplace.ui.app.NoData
import com.komekci.marketplace.ui.navigation.Routes

@Composable
fun OnCategoryScreen(navHostController: NavHostController, state: CategoryState) {

    val viewModel: CategoryViewModel = hiltViewModel()
    val productState = viewModel.products.collectAsState()
    val productFilter = LocalProductFilter.current

    val listState = rememberLazyListState()

    val appSettingsState = LocalSettings.current
    val favSettings = LocalFavSettings.current

    LaunchedEffect(productFilter.value) {
        viewModel.setRequest(productFilter.value)
        viewModel.getProducts()
    }

    LaunchedEffect(listState.canScrollForward.not()) {
        if(productState.value.loading.not() && productState.value.hasMore) {
            viewModel.getProducts()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        state.categories?.let { list ->
            LazyRow(
                state = listState,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFFF2F2F5))
                            .clickable {
                                navHostController.navigate(Routes.Category) {
                                    launchSingleTop = true
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.category),
                            contentDescription = null,
                            tint = Color(0xFF60646C)
                        )
                    }
                }
                items(list.size) { index ->
                    val item = list[index]
                    CategoryChip(
                        title = translateValue(instance = item, property = "name"),
                        selected = productFilter.value.catalogId.contains(item.id)
                    ) {
                        productFilter.value = productFilter.value.copy(
                            catalogId = listOf(item.id)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }


        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            productState.value.products?.let { list ->
                items(list.size) { index ->
                    ProductComponent(
                        item = list[index],
                        onFavoriteClick = {
                            favSettings.value.likeProduct(it, FavoriteType.PRODUCT)
                        }
                    ) {
                        navHostController.navigate(Routes.Details.replace("{id}", list[index].id))
                    }
                }
            }

            if (productState.value.products.isNullOrEmpty() && productState.value.loading.not()) {
                item(
                    span = {
                        GridItemSpan(maxLineSpan)
                    }
                ) {
                    NoData(modifier = Modifier.fillMaxSize()) {
                        viewModel.getProducts()
                    }
                }
            } else if (productState.value.error.isNullOrEmpty().not()) {
                item(
                    span = {
                        GridItemSpan(maxLineSpan)
                    }
                ) {
                    ErrorField(modifier = Modifier.fillMaxWidth())
                }
            } else {
                item(span = {
                    GridItemSpan(maxLineSpan)
                }) {

                    if (productState.value.loading && productState.value.hasMore) {
                        AppLoading(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )
                    }
                }
            }


        }


    }


}