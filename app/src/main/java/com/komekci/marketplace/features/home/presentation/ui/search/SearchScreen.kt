package com.komekci.marketplace.features.home.presentation.ui.search

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.features.favorite.domain.model.FavoriteType
import com.komekci.marketplace.features.home.presentation.ui.SearchInput
import com.komekci.marketplace.features.home.presentation.ui.SearchInput2
import com.komekci.marketplace.features.home.presentation.ui.shops.ShopItem
import com.komekci.marketplace.features.home.presentation.viewmodel.CategoryViewModel
import com.komekci.marketplace.features.home.presentation.viewmodel.ShopsViewModel
import com.komekci.marketplace.features.product.presentation.ui.product.ProductComponent
import com.komekci.marketplace.state.LocalFavSettings
import com.komekci.marketplace.state.LocalProductFilter
import com.komekci.marketplace.state.LocalSettings
import com.komekci.marketplace.state.SetAppSettings
import com.komekci.marketplace.ui.app.AppError
import com.komekci.marketplace.ui.app.AppLoading
import com.komekci.marketplace.ui.app.NoData
import com.komekci.marketplace.ui.navigation.Routes
import com.komekci.marketplace.ui.theme.newTextColor
import com.primex.core.VerticalGrid

@Composable
fun SearchScreen(homeNavController: NavHostController, text: String) {
    val viewModel: CategoryViewModel = hiltViewModel()
    val appSettingsState = LocalSettings.current
    val setAppSettingsState = SetAppSettings.current

    val favSettings = LocalFavSettings.current

    val query = rememberSaveable {
        mutableStateOf(text)
    }

    val type = rememberSaveable {
        mutableIntStateOf(0)
    }

    val shopViewModel: ShopsViewModel = hiltViewModel()
    val shops = shopViewModel.shops.collectAsState()

    LaunchedEffect(true) {
        setAppSettingsState(appSettingsState.copy(hideBottomNavigation = true))
    }

    fun back() {
        setAppSettingsState(appSettingsState.copy(hideBottomNavigation = false))
        homeNavController.navigateUp()
    }

    LaunchedEffect(query.value, type.intValue) {
        if(type.intValue==0) {
            viewModel.searchProducts(query.value, appSettingsState.languageTag)
        }
    }

    val strings = LocalStrings.current

    val state = viewModel.searchState.collectAsState()

    BackHandler {
        back()
    }


    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .border(
                        1.dp,
                        Color(0xFFD3D4DB),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable {
                        back()
                    }
                    .padding(14.dp)
                    .height(25.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = Color(0xFF60646C)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            SearchInput2(modifier = Modifier.weight(1f), initialValue = text) {
                query.value = it
            }
        }

        Row(
            Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    type.intValue = 0
                },
                shape = MaterialTheme.shapes.small,
                border = BorderStroke(1.dp, color = if(type.intValue == 0) MaterialTheme.colorScheme.primary else newTextColor),
            ) {
                Text(strings.products, color = if(type.intValue == 0) MaterialTheme.colorScheme.primary else newTextColor)
            }

            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    type.intValue = 1
                },
                shape = MaterialTheme.shapes.small,
                border = BorderStroke(1.dp, color = if(type.intValue == 1) MaterialTheme.colorScheme.primary else newTextColor),
            ) {
                Text(strings.shops, color = if(type.intValue == 1) MaterialTheme.colorScheme.primary else newTextColor)
            }
        }

        Box(Modifier.fillMaxWidth().weight(1f)) {
            if (type.intValue == 0) {

                if (state.value.loading) {
                    AppLoading(modifier = Modifier.fillMaxSize())
                } else if (state.value.error.isNullOrEmpty().not()) {
                    AppError {
                        viewModel.getCategories()
                    }
                } else if (state.value.products.isNullOrEmpty()) {
                    NoData(modifier = Modifier.fillMaxSize()) {
                        viewModel.getCategories()
                    }
                } else {
                    state.value.products?.let { list ->


                        Text(
                            text = strings.searchShop,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.W700
                            ),
                            color = Color(0xFF000000),
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(list.size) { index ->
                                ProductComponent(
                                    item = list[index],
                                    onFavoriteClick = {
                                        favSettings.value.likeProduct(
                                            list[index].id,
                                            FavoriteType.PRODUCT
                                        )
                                    }
                                ) {
                                    val route = Routes.Details.replace("{id}", list[index].id)
                                    println(route)
                                    homeNavController.navigate(route) {
                                        launchSingleTop = true
                                    }
                                }
                            }
                        }

                    }
                }
            } else {
                key(query.value) {
                    VerticalGrid(
                        columns = 2,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 16.dp)
                    ) {
                        val productFilter = LocalProductFilter.current
                        shops.value.shops?.let { original ->
                            val re = Regex("[^A-Za-z0-9 ]")
                            val list = original.filter {
                                it.title.lowercase().trim().contains(query.value.trim().lowercase())
                                        || query.value.trim().isEmpty()
                                        || re.replace(it.title.lowercase().trim(),"").contains(query.value)
                            }
                            Log.e("SHOPS", original.count().toString())
                            repeat(list.count()) {
                                ShopItem(
                                    item = list[it], modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            top = 12.dp,
                                            end = if ((it + 1) % 2 == 0) 0.dp else 8.dp
                                        ).height(220.dp),
                                    onFavoriteClick = {
                                        favSettings.value.likeProduct(list[it].id, FavoriteType.STORE)
                                    }
                                ) {
//                            productFilter.value = productFilter.value.copy(
//                                storeId = listOf(list[it].id.toInt()),
//                                categoryId = emptyList(),
//                                brandId = emptyList(),
//                                catalogId = emptyList(),
//                            )
//                            navHostController.navigate(list[it]) {
//                                launchSingleTop = true
//                            }
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}