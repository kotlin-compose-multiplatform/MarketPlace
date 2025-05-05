package com.komekci.marketplace.features.home.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.komekci.marketplace.features.home.domain.model.ShopEntity
import com.komekci.marketplace.features.home.presentation.ui.home.HomeProducts
import com.komekci.marketplace.features.home.presentation.ui.home.RealHome
import com.komekci.marketplace.features.home.presentation.ui.search.SearchScreen
import com.komekci.marketplace.features.home.presentation.ui.shops.Shops
import com.komekci.marketplace.features.home.presentation.viewmodel.ShopsViewModel
import com.komekci.marketplace.features.main.presentation.ui.commonRoutes
import com.komekci.marketplace.features.product.presentation.ui.details.DetailsScreen
import com.komekci.marketplace.state.LocalSettings
import com.komekci.marketplace.state.SetAppSettings
import com.komekci.marketplace.ui.navigation.Routes

@Composable
fun HomeNavigation(
    homeNavController: NavHostController,
    mainNavHostController: NavHostController,
    onChange: (String) -> Unit
) {

    val hideRoutes = listOf(
        Routes.Home,
        Routes.Discount,
    )

    val hideBottomSheetRoutes = listOf(
        Routes.AboutUs,
        Routes.Faq,
        Routes.Help
    )

    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
    val isHide = hideRoutes.contains(navBackStackEntry?.destination?.route).not()
    val isHideBottom = hideBottomSheetRoutes.contains(navBackStackEntry?.destination?.route)
    val shopViewModel: ShopsViewModel = hiltViewModel()
    val shops = shopViewModel.shops.collectAsState()
    val vipShops = shopViewModel.vipShops.collectAsState()
    val appSettingsState = LocalSettings.current
    val setAppSettingsState = SetAppSettings.current

    LaunchedEffect(navBackStackEntry?.destination?.route) {
        navBackStackEntry?.destination?.route?.let {
            setAppSettingsState(appSettingsState.copy(
                hideBottomNavigation = it!=Routes.Home
            ))
        }
    }

    LaunchedEffect(isHideBottom) {
        setAppSettingsState(appSettingsState.copy(hideBottomNavigation = isHideBottom))
    }

    Column(modifier = Modifier.fillMaxSize()) {
//        if (isHide.not()) {
//            HomeActions(navHostController = homeNavController, mainNavHostController, onSearch = {
//                homeNavController.navigate(Routes.SearchRoute.replace("{text}", it))
//            })
//        }
        NavHost(navController = homeNavController, startDestination = Routes.Home) {
            composable(Routes.Home) {

                RealHome(homeNavController)


            }

            composable(Routes.On_Country, arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument("c_id") {
                    type = NavType.StringType
                    nullable = false
                }
            )) { navBackStackEntry->
                val country = navBackStackEntry.arguments?.getString("name")
                val c_id = navBackStackEntry.arguments?.getString("c_id")
                HomeProducts(
                    modifier = Modifier.fillMaxSize(),
                    country = country?:"",
                    countryId = c_id?:"",
                    regionId = "",
                    navController = homeNavController,
                    onBack = {
                        homeNavController.navigateUp()
                    }
                )
            }

            commonRoutes(homeNavController)

            composable<ShopEntity> {backStack->
                val args = backStack.toRoute<ShopEntity>()
                com.komekci.marketplace.features.product.presentation.ui.category.CategoryScreen(
                    homeNavController,
                    args,
                )
            }

            composable(Routes.onCategory) {
                com.komekci.marketplace.features.product.presentation.ui.category.CategoryScreen(
                    homeNavController,
                    defaultRoute = Routes.onCategory
                )
            }

            composable(Routes.SearchRoute, arguments = listOf(
                navArgument("text") {
                    type = NavType.StringType
                    nullable = false
                }
            )) { navBackStackEntry->
                val text = navBackStackEntry.arguments?.getString("text")
                SearchScreen(homeNavController = homeNavController, text!!)
            }



            composable(Routes.Details, arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    nullable = true
                }
            )) { navBackStackEntry->
                val id = navBackStackEntry.arguments?.getString("id")
                DetailsScreen(navHostController = homeNavController, id = id)
            }
        }
    }
}