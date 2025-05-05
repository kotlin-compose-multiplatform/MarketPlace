package com.komekci.marketplace.features.main.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.komekci.marketplace.features.basket.presentation.ui.BasketNavigation
import com.komekci.marketplace.features.chat.presentation.ui.ChatNavigation
import com.komekci.marketplace.features.chat.presentation.viewmodel.OnChatViewModel
import com.komekci.marketplace.features.favorite.domain.model.FavoriteType
import com.komekci.marketplace.features.favorite.presentation.viewmodel.FavoriteViewModel
import com.komekci.marketplace.features.home.presentation.ui.HomeNavigation
import com.komekci.marketplace.features.profile.presentation.ui.ProfileNavigation
import com.komekci.marketplace.state.FavSettings
import com.komekci.marketplace.state.LocalFavSettings
import com.komekci.marketplace.state.LocalRouteState
import com.komekci.marketplace.state.LocalSettings
import com.komekci.marketplace.state.SetAppSettings
import com.komekci.marketplace.ui.app.CheckAuthScreen
import com.komekci.marketplace.ui.navigation.Routes

@Composable
fun HomeScreen() {
    val mainNavHostController = rememberNavController()
    val homeNavController = rememberNavController()
    val context = LocalContext.current
    val globalRoute = LocalRouteState.current
    val settingsState = LocalSettings.current
    val setSettingsState = LocalFavSettings.current

    val favoriteViewModel: FavoriteViewModel = hiltViewModel()
    val favoriteProducts = favoriteViewModel.favProducts.collectAsState()
    val favoriteStores = favoriteViewModel.favStores.collectAsState()

    val viewModel: OnChatViewModel = hiltViewModel()

    val unread = viewModel.unreadMessages.collectAsState()

    LaunchedEffect(true) {

        favoriteViewModel.initFavorites()
        println("LIKE-------init")
        setSettingsState.value = FavSettings(
            reFetchFavorites = {
                favoriteViewModel.getFavoriteProducts()
            },
            likeProduct = { id, type ->
                println("LIKE: ${id} / ${type}")
                when (type) {
                    FavoriteType.PRODUCT -> {
                        println("LIKE: ${id}")
                        favoriteViewModel.likeProduct(id) {
                            println("LIKE-2: ${id}")
                            favoriteViewModel.getFavoriteProducts()
                        }
                    }

                    FavoriteType.STORE -> {
                        favoriteViewModel.likeStore(id) {
                            favoriteViewModel.getFavoriteStores()
                        }
                    }
                }
            },
            checkIsLiked = { id, type ->
                println("CHECK-LIKE: ${id} / ${type}")
                when (type) {
                    FavoriteType.PRODUCT -> {
                        favoriteProducts.value.data.isNullOrEmpty().not() && favoriteProducts.value.data?.indexOfFirst {
                            it.id == try {
                                id.toInt()
                            } catch (_: Exception) {
                                0
                            }
                        } != -1
                    }

                    FavoriteType.STORE -> {
                        favoriteStores.value.data.isNullOrEmpty().not() && favoriteStores.value.data?.indexOfFirst {
                            it.id == try {
                                id.toInt()
                            } catch (_: Exception) {
                                0
                            }
                        } != -1
                    }
                }
            }
        )

        viewModel.connectToSocket()
        viewModel.getUnreadMessages()
    }

    LaunchedEffect(globalRoute.value.homeRoute) {
        mainNavHostController.navigate(globalRoute.value.homeRoute) {
            popUpTo(mainNavHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navBackStackEntry by mainNavHostController.currentBackStackEntryAsState()


    LaunchedEffect(navBackStackEntry?.destination?.route) {
        navBackStackEntry?.destination?.route?.let {
            globalRoute.value = globalRoute.value.copy(
                homeRoute = it
            )
        }
    }



    Scaffold(modifier = Modifier.imePadding().fillMaxSize(), bottomBar = {
        AppBottomBar(rootNavController = mainNavHostController, badge = unread.value?.size)
    },  contentWindowInsets = WindowInsets.systemBars) { padding ->
        Box(modifier = Modifier.padding(padding).consumeWindowInsets(padding)) {
            NavHost(navController = mainNavHostController, startDestination = Routes.Home) {
                composable(Routes.Home) {
                    HomeNavigation(
                        homeNavController = homeNavController,
                        mainNavHostController = mainNavHostController
                    ) {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                }
                commonRoutes(mainNavHostController)
                composable(Routes.Basket) {
//                   CheckAuthScreen {
                       BasketNavigation(
                           homeNavHostController = homeNavController,
                           mainNavHostController = mainNavHostController
                       )
//                   }
                }
                composable(Routes.Chats) {
                    CheckAuthScreen {
                        ChatNavigation(viewModel = viewModel)
                    }
                }
                composable(Routes.Profile) {
                    ProfileNavigation(
                        homeNavHostController = homeNavController,
                        mainNavHostController = mainNavHostController,
                        favoriteProductsState = favoriteProducts.value,
                        favoriteStoresState = favoriteStores.value
                    )
                }
            }
        }
    }
}

/*
1) Payment history doly maglumat gelenok(money, type(Komekci, Dukan),)
2) Payment history filter yok
3) Pay-with-komekchi-key POST error bersede 200 gelya (invalid key)
4) Chat subscribe duzedip berayda
 */