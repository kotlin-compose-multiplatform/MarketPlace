package com.komekci.marketplace.features.home.presentation.ui.shops

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.features.favorite.domain.model.FavoriteType
import com.komekci.marketplace.features.home.domain.model.ShopEntity
import com.komekci.marketplace.features.home.presentation.viewmodel.ShopsViewModel
import com.komekci.marketplace.state.LocalProductFilter
import com.komekci.marketplace.state.LocalSettings
import com.komekci.marketplace.state.SetAppSettings
import com.komekci.marketplace.ui.app.AppError
import com.komekci.marketplace.ui.app.AppLoading
import com.komekci.marketplace.ui.app.NoData
import com.primex.core.VerticalGrid

@Composable
fun Shops(
    navHostController: NavHostController
) {
    val strings = LocalStrings.current
    val appSettingsState = LocalSettings.current

    val shopViewModel: ShopsViewModel = hiltViewModel()
    val shops = shopViewModel.shops.collectAsState()
    val vipShops = shopViewModel.vipShops.collectAsState()

    if (shops.value.loading) {
        AppLoading(modifier = Modifier.fillMaxSize())
    } else if (shops.value.error) {
        AppError(
            modifier = Modifier.fillMaxSize(),
            title = strings.somethingWentWrong,
            message = strings.errorMessage,
            actionText = strings.tryAgain
        ) {
            shopViewModel.getShops()
            shopViewModel.getVipShops()
        }
    } else if(shops.value.isEmpty){
        NoData(modifier = Modifier.fillMaxSize()) {
            shopViewModel.getShops()
            shopViewModel.getVipShops()
        }
    } else  {
        val productFilter = LocalProductFilter.current
        VerticalGrid(
            columns = 2,
            modifier = Modifier.fillMaxSize().padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            shops.value.shops?.let { list->
                repeat(list.count()) {
                    ShopItem(
                        item = list[it], modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 12.dp,
                                end = if ((it + 1) % 2 == 0) 0.dp else 8.dp
                            ),
                        onFavoriteClick = {
//                            appSettingsState.likeProduct(list[it].id, FavoriteType.STORE)
                        }
                    ) {
                        productFilter.value = productFilter.value.copy(
                            storeId = listOf(list[it].id.toInt()),
                            categoryId = emptyList(),
                            brandId = emptyList(),
                            catalogId = emptyList(),
                        )
                        navHostController.navigate(list[it]) {
                            launchSingleTop = true
                        }
                    }
                }
            }
        }
    }
}