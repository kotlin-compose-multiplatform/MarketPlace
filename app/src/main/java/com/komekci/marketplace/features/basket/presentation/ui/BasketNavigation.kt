package com.komekci.marketplace.features.basket.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.komekci.marketplace.features.basket.presentation.ui.details.BasketDetails
import com.komekci.marketplace.features.home.presentation.ui.HomeActions
import com.komekci.marketplace.features.home.presentation.ui.search.SearchScreen
import com.komekci.marketplace.features.main.presentation.ui.commonRoutes
import com.komekci.marketplace.features.product.presentation.ui.details.DetailsScreen
import com.komekci.marketplace.ui.navigation.Routes

@Composable
fun BasketNavigation(
    homeNavHostController: NavHostController,
    mainNavHostController: NavHostController
) {
    val basketNavHostController = rememberNavController()

    val hideRoutes = listOf(
        Routes.Basket,
    )
    val navBackStackEntry by basketNavHostController.currentBackStackEntryAsState()
    val isHide = hideRoutes.contains(navBackStackEntry?.destination?.route)

    Column(modifier = Modifier.fillMaxSize()) {
        NavHost(navController = basketNavHostController, startDestination = Routes.Basket) {
            composable(Routes.Basket) {
                BasketList(basketNavHostController)
            }
            commonRoutes(basketNavHostController)
            composable(Routes.BasketDetails, arguments = listOf(
                navArgument("shopId") {
                    type = NavType.IntType
                    nullable = false
                }
            )) { navBackStackEntry->
                val shopId = navBackStackEntry.arguments?.getInt("shopId",0)?:0
                println("BASKET SHOP: $shopId")
                BasketDetails(basketNavHostController, shopId)
            }

            composable(Routes.SearchRoute) {
                SearchScreen(homeNavController = basketNavHostController, "")
            }

            composable(Routes.Details, arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    nullable = true
                }
            )) { navBackStackEntry->
                val id = navBackStackEntry.arguments?.getString("id")
                DetailsScreen(navHostController = basketNavHostController, id = id)
            }
        }
    }
}