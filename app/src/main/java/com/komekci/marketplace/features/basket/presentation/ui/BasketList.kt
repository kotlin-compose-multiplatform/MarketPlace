package com.komekci.marketplace.features.basket.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.core.locale.translateValue
import com.komekci.marketplace.features.basket.presentation.viewmodel.BasketViewModel
import com.komekci.marketplace.features.home.presentation.ui.home.CupertinoScrollableSegmentedControl
import com.komekci.marketplace.state.LocalRouteState
import com.komekci.marketplace.ui.navigation.Routes
import io.github.alexzhirkevich.cupertino.CupertinoSegmentedControl
import io.github.alexzhirkevich.cupertino.CupertinoSegmentedControlTab
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun BasketList(navHostController: NavHostController) {
    val viewModel: BasketViewModel = hiltViewModel()
    val strings = LocalStrings.current
    val positions = remember { mutableStateListOf<Float>() }
    val scrollState = rememberLazyListState()
    val products = remember {
        viewModel.basketProducts
    }
    val stores = viewModel.basketStores
    val price = remember {
        viewModel.basketPrice
    }
    val globalRoute = LocalRouteState.current
    val selectedStore = viewModel.shopId
    LaunchedEffect(globalRoute.value.homeRoute, selectedStore.intValue) {
        viewModel.getBasketProducts(selectedStore.intValue)
    }
    LaunchedEffect(positions) {
        println("${positions.toList()}")
    }


    LaunchedEffect(stores.value) {
        if (stores.value.isNotEmpty()) {
            viewModel.setShopId(stores.value[0].shopId)
        }
    }

    key(products.value, stores.value) {
        Column(modifier = Modifier.fillMaxSize()) {

            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Spacer(Modifier.height(22.dp))
                }
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = strings.myBasket,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 34.sp,
                                fontWeight = FontWeight.W700
                            ),
                            color = Color(0xFF0F1E3C)
                        )

                        Text(
                            text = "${products.value.size} ${strings.product}",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.W400
                            ),
                            color = Color(0xFFA1A1AA)
                        )
                    }
                }

                if (stores.value.isNotEmpty()) {
                    item {
                        val selectedIndex =
                            if (selectedStore.intValue == 0) 0 else stores.value.indexOfFirst { it.shopId == selectedStore.intValue }
                        CupertinoSegmentedControl(
                            modifier = Modifier.fillMaxWidth(),
                            paddingValues = PaddingValues(0.dp),
                            selectedTabIndex = if (selectedIndex != -1) selectedIndex else 0,
                        ) {
                            repeat(stores.value.count()) { index ->
                                val store = stores.value[index]
                                CupertinoSegmentedControlTab(
                                    isSelected = selectedStore.intValue == store.shopId,
                                    onClick = {
                                        viewModel.setShopId(store.shopId)
                                    }
                                ) {
                                    CupertinoText(
                                        store.shopName,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontWeight = FontWeight.W600
                                        ),
                                        color = Color(0xFF0F1E3C)
                                    )
                                }


                            }
                        }
                    }
                }

                items(products.value.size) {

                    BasketItem(
                        product = products.value[it],
                        viewModel = viewModel
                    ) {
                        navHostController.navigate(
                            Routes.Details.replace(
                                "{id}",
                                products.value[it].id
                            )
                        )
                    }


                }

                item {
                    TotalBasket(
                        price = price.value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned {
                                println("Global positioned: ${it.positionInParent().y}")
                                positions.add(it.positionInParent().y)
                            }
                    ) {
                        navHostController.navigate(Routes.BasketDetails.replace("{shopId}", selectedStore.intValue.toString()))
                    }
                }
            }

        }
    }
}