package com.komekci.marketplace.features.product.presentation.ui.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.komekci.marketplace.core.locale.translateValue
import com.komekci.marketplace.features.auth.presentation.ui.ErrorField
import com.komekci.marketplace.features.chat.presentation.viewmodel.ChatsViewModel
import com.komekci.marketplace.features.favorite.domain.model.FavoriteType
import com.komekci.marketplace.features.home.domain.model.ShopEntity
import com.komekci.marketplace.features.product.presentation.state.CategoryState
import com.komekci.marketplace.features.product.presentation.ui.product.ProductComponent
import com.komekci.marketplace.features.product.presentation.viewmodel.CategoryViewModel
import com.komekci.marketplace.state.LocalFavSettings
import com.komekci.marketplace.state.LocalProductFilter
import com.komekci.marketplace.state.LocalRouteState
import com.komekci.marketplace.state.LocalSettings
import com.komekci.marketplace.ui.app.AppLoading
import com.komekci.marketplace.ui.app.NoData
import com.komekci.marketplace.ui.navigation.Routes

@Composable
fun OnShopScreen(
    shopEntity: ShopEntity? = null,
    state: CategoryState,
    navHostController: NavHostController,
    homeNavHostController: NavHostController
) {
    val chatViewModel: ChatsViewModel = hiltViewModel()
    val createChatState = chatViewModel.createState.collectAsState()
    val globalRoute = LocalRouteState.current

    val viewModel: CategoryViewModel = hiltViewModel()
    val productState = viewModel.products.collectAsState()
    val productFilter = LocalProductFilter.current


    val appSettingsState = LocalSettings.current
    val favSettings = LocalFavSettings.current

    LaunchedEffect(productFilter.value) {
        viewModel.setRequest(productFilter.value)
        viewModel.getProducts()
    }

    val listState = rememberLazyGridState()

    LaunchedEffect(listState.canScrollForward.not()) {
        if (productState.value.loading.not() && productState.value.hasMore) {
            viewModel.getProducts()
        }
    }


    LazyVerticalGrid(
        state = listState,
        modifier = Modifier
            .fillMaxWidth(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item(span = {
            GridItemSpan(maxLineSpan)
        }) {
            ShopDescription(
                loading = createChatState.value.loading,
                title = shopEntity?.title?:"",
                countProducts = shopEntity?.totalProducts?:"0",
                modifier = Modifier.fillMaxWidth(),
                logo = shopEntity?.image?:"",
                instagram = shopEntity?.instagram,
                tiktok = shopEntity?.tiktok
            ) {
                chatViewModel.createChat(shopEntity?.id?.toInt() ?: 0) {
                    globalRoute.value = globalRoute.value.copy(
                        homeRoute = Routes.Chats,
                        chatRoomId = it
                    )
                }
            }
        }
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

//        item {
//            viewModel.getProducts()
//        }


    }


}