package com.komekci.marketplace.features.favorite.presentation.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.features.favorite.domain.model.FavoriteType
import com.komekci.marketplace.features.favorite.presentation.state.FavoriteProductsState
import com.komekci.marketplace.features.favorite.presentation.state.FavoriteStoresState
import com.komekci.marketplace.features.home.presentation.ui.shops.ShopItem
import com.komekci.marketplace.features.product.presentation.ui.product.ProductComponent
import com.komekci.marketplace.state.LocalFavSettings
import com.komekci.marketplace.state.LocalSettings
import com.komekci.marketplace.ui.app.AppError
import com.komekci.marketplace.ui.app.AppLoading
import com.komekci.marketplace.ui.app.NoData

@Composable
fun Favorites(
    navHostController: NavHostController = rememberNavController(),
    favoriteProductsState: FavoriteProductsState,
    favoriteStore: FavoriteStoresState
) {
    val strings = LocalStrings.current
    val type = rememberSaveable {
        mutableIntStateOf(0)
    }
    val appSettingsState = LocalFavSettings.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9FB))
    ) {


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null,
                tint = Color(0xFF1A1A1A),
                modifier = Modifier
                    .size(34.dp)
                    .align(Alignment.TopStart)
                    .clip(CircleShape)
                    .clickable { navHostController.navigateUp() }
            )

            Text(
                text = strings.favorites, style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.W600
                ), textAlign = TextAlign.Center,
                color = Color(0xFF1A1A1A),
                modifier = Modifier.align(Alignment.Center),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

        }

//        Spacer(modifier = Modifier.height(12.dp))

//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            FavoriteTab(
//                title = strings.products,
//                modifier = Modifier.weight(1f),
//                selected = type.intValue == 0
//            ) {
//                type.intValue = 0
//            }
//            FavoriteTab(
//                title = strings.stores,
//                modifier = Modifier.weight(1f),
//                selected = type.intValue == 1
//            ) {
//                type.intValue = 1
//            }
//        }

        Spacer(modifier = Modifier.height(12.dp))

        AnimatedContent(targetState = type.intValue, label = "") {
            if (it == 0) {
                if(favoriteProductsState.loading) {
                    AppLoading()
                } else if(favoriteProductsState.error.isNullOrEmpty().not() || favoriteProductsState.message.isNullOrEmpty().not()) {
                    AppError(modifier = Modifier.fillMaxSize()) {

                    }
                } else if(favoriteProductsState.data.isNullOrEmpty()) {
                    NoData(modifier = Modifier.fillMaxSize())
                } else {
                    favoriteProductsState.data.let { list->
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
                        ) {
                            items(list.size) { index->
                                val item = list[index]
                                ProductComponent(
                                    item = item.toProductEntity(),
                                    onFavoriteClick = {
                                        appSettingsState.value.likeProduct(item.id.toString(),FavoriteType.PRODUCT)
                                    }
                                )
                            }
                        }
                    }
                }
            } else {
                if(favoriteStore.loading) {
                    AppLoading()
                } else if(favoriteStore.error.isNullOrEmpty().not() || favoriteStore.message.isNullOrEmpty().not()) {
                    AppError(modifier = Modifier.fillMaxSize()) {

                    }
                } else if(favoriteStore.data.isNullOrEmpty()) {
                    NoData(modifier = Modifier.fillMaxSize())
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        items(favoriteStore.data.size) {index->
                            val item = favoriteStore.data[index]
                            ShopItem(
                                item = item.toUiEntity()
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun FavoriteTab(
    modifier: Modifier = Modifier,
    title: String,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .border(
                width = if (selected) 0.dp else 1.dp,
                Color(0x2B01062F),
                shape = RoundedCornerShape(4.dp)
            )
            .background(
                color = if (selected) Color(0xFF297C3B) else Color.Transparent,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable {
                onClick()
            }
            .padding(10.dp)
            .height(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
            color = if (selected) Color(0xFFFCFCFD) else Color(0xFF1C2024)
        )
    }
}