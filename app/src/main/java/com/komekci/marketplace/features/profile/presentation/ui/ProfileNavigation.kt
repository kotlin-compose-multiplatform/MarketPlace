package com.komekci.marketplace.features.profile.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.komekci.marketplace.features.create_store.presentation.ui.StoreNavigation
import com.komekci.marketplace.features.favorite.presentation.state.FavoriteProductsState
import com.komekci.marketplace.features.favorite.presentation.state.FavoriteStoresState
import com.komekci.marketplace.features.favorite.presentation.ui.Favorites
import com.komekci.marketplace.features.home.presentation.ui.HomeActions
import com.komekci.marketplace.features.home.presentation.ui.search.SearchScreen
import com.komekci.marketplace.features.main.presentation.ui.commonRoutes
import com.komekci.marketplace.features.payment.presentation.ui.MyPayments
import com.komekci.marketplace.features.product.presentation.ui.details.DetailsScreen
import com.komekci.marketplace.features.profile.presentation.viewmodel.ProfileViewModel
import com.komekci.marketplace.state.LocalRouteState
import com.komekci.marketplace.state.LocalSettings
import com.komekci.marketplace.state.SetAppSettings
import com.komekci.marketplace.ui.app.CheckAuthScreen
import com.komekci.marketplace.ui.navigation.Routes

@Composable
fun ProfileNavigation(
    homeNavHostController: NavHostController,
    mainNavHostController: NavHostController,
    favoriteProductsState: FavoriteProductsState,
    favoriteStoresState: FavoriteStoresState
) {
    val navHostController = rememberNavController()
    val hideRoutes = listOf(
        Routes.Profile,
    )
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val isHide = hideRoutes.contains(navBackStackEntry?.destination?.route)
    val globalRoute = LocalRouteState.current
    val appSettingsState = LocalSettings.current
    val setAppSettingsState = SetAppSettings.current

    val userViewModel: ProfileViewModel = hiltViewModel()

    /* Error code navigating */
    LaunchedEffect(navBackStackEntry?.destination?.route) {

        navBackStackEntry?.destination?.route?.let {
            setAppSettingsState(
                appSettingsState.copy(
                    hideBottomNavigation = it != Routes.Profile
                )
            )
            globalRoute.value = globalRoute.value.copy(
                profileRoute = it
            )
        }
    }

    LaunchedEffect(globalRoute.value.profileRoute) {
        Log.e("System", globalRoute.value.profileRoute)
        if (globalRoute.value.profileRoute != Routes.Details) {
            navHostController.navigate(globalRoute.value.profileRoute) {
                launchSingleTop = true
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        NavHost(navController = navHostController, startDestination = Routes.Profile) {
            composable(Routes.Profile) {
                ProfileScreen(navHostController)
            }
            commonRoutes(navHostController)
            composable(Routes.TermsOfUse) {
                TermsOfUse(navHostController)
            }
            composable(Routes.Address) {
                CheckAuthScreen {
                    AddressScreen(navHostController)
                }
            }
            composable(Routes.Orders) {
                OrdersScreen(navHostController, userViewModel)
            }
            composable(Routes.Order) {
                    OrderScreen(navHostController, userViewModel)
            }
            composable(Routes.Payments) {
                CheckAuthScreen {
                    MyPayments(navHostController)
                }
            }
            composable(Routes.Favs) {
                CheckAuthScreen {
                    Favorites(navHostController, favoriteProductsState, favoriteStoresState)
                }
            }
            composable(
                Routes.MyStore, arguments = listOf(
                navArgument("start") {
                    type = NavType.StringType
                    nullable = false
                }
            )) { navBackStackEntry ->
                val start = navBackStackEntry.arguments?.getString("start")
//                CheckAuthScreen {
                StoreNavigation(
                    profileNavHostController = navHostController,
                    startDestination = start!!
                )
//                }
            }
            composable(Routes.OrderProducts) {
                    OrderProducts(navHostController, userViewModel)
            }



            composable(
                Routes.SearchRoute, arguments = listOf(
                navArgument("text") {
                    type = NavType.StringType
                    nullable = false
                }
            )) { navBackStackEntry ->
                val text = navBackStackEntry.arguments?.getString("text")
                SearchScreen(homeNavController = navHostController, text!!)
            }

            composable(
                Routes.Details, arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    nullable = false
                }
            )) { navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getString("id")
                DetailsScreen(navHostController = navHostController, id = id)

            }

        }
    }
}