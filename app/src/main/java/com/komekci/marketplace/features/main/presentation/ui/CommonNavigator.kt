package com.komekci.marketplace.features.main.presentation.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.komekci.marketplace.features.home.presentation.ui.about.AboutUsScreen
import com.komekci.marketplace.features.home.presentation.ui.category.CategoryScreen
import com.komekci.marketplace.features.home.presentation.ui.discount.DiscountScreen
import com.komekci.marketplace.features.home.presentation.ui.faq.FaqScreen
import com.komekci.marketplace.features.home.presentation.ui.help.KomekciScreen
import com.komekci.marketplace.features.home.presentation.ui.location.SelectLocation
import com.komekci.marketplace.features.home.presentation.ui.notification.NotificationScreen
import com.komekci.marketplace.features.product.presentation.ui.details.DetailsScreen
import com.komekci.marketplace.ui.navigation.Routes

fun NavGraphBuilder.commonRoutes(navHostController: NavHostController) {
    composable(Routes.Location) {
        SelectLocation(navHostController)
    }

    composable(Routes.Category) {
        CategoryScreen(navHostController)
    }

    composable(Routes.Discount) {
        DiscountScreen(navHostController)
    }

    composable(Routes.Help) {
        KomekciScreen(navHostController)
    }

    composable(Routes.Faq) {
        FaqScreen(navHostController)
    }

    composable(Routes.Notifications) {
        NotificationScreen(navHostController)
    }

    composable(Routes.AboutUs) {
        AboutUsScreen(navHostController)
    }

    composable(Routes.Details, arguments = listOf(
        navArgument("id") {
            type = NavType.StringType
            nullable = true
        }
    )) { navBackStackEntry->
        val id = navBackStackEntry.arguments?.getString("id")
        DetailsScreen(navHostController = navHostController, id = id)
    }


}