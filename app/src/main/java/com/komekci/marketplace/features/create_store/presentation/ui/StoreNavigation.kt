package com.komekci.marketplace.features.create_store.presentation.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.komekci.marketplace.features.create_store.domain.model.UpdateLocationNavParams
import com.komekci.marketplace.features.create_store.presentation.ui.address.AddAddress
import com.komekci.marketplace.features.create_store.presentation.ui.address.MyAddress
import com.komekci.marketplace.features.create_store.presentation.ui.product.AddProduct
import com.komekci.marketplace.features.create_store.presentation.ui.product.EditProduct
import com.komekci.marketplace.features.create_store.presentation.ui.product.MyProductDetails
import com.komekci.marketplace.features.create_store.presentation.ui.product.MyProducts
import com.komekci.marketplace.features.create_store.presentation.viewmodel.StoreViewModel
import com.komekci.marketplace.features.payment.presentation.ui.StorePayments
import com.komekci.marketplace.features.profile.presentation.ui.OrderProducts
import com.komekci.marketplace.features.profile.presentation.ui.OrderScreen
import com.komekci.marketplace.features.profile.presentation.ui.OrderType
import com.komekci.marketplace.features.profile.presentation.ui.OrdersScreen
import com.komekci.marketplace.features.profile.presentation.viewmodel.ProfileViewModel
import com.komekci.marketplace.features.store_chat.presentation.ui.HelperChatNavigation
import com.komekci.marketplace.features.store_chat.presentation.ui.StoreChatNavigation
import com.komekci.marketplace.ui.navigation.OnlinePayment
import com.komekci.marketplace.ui.navigation.Routes
import com.komekci.marketplace.ui.navigation.WithCode

@Composable
fun StoreNavigation(
    profileNavHostController: NavHostController,
    startDestination: String = Routes.SelectPaymentType
) {
    val navHostController = rememberNavController()
    val storeViewModel: StoreViewModel = hiltViewModel()
    val userViewModel: ProfileViewModel = hiltViewModel()
    NavHost(navController = navHostController, startDestination = startDestination) {
        composable(Routes.SelectPaymentType) {
            SelectPaymentType(
                profileNavHostController = profileNavHostController,
                navHostController = navHostController,
                type = SelectPaymentPage.NEW
            )
        }
        composable(Routes.SelectPaymentType2) {
            SelectPaymentType(
                profileNavHostController = profileNavHostController,
                navHostController = navHostController,
                type = SelectPaymentPage.EXPIRE
            )
        }
        composable<OnlinePayment> {navback ->
            val args = navback.toRoute<OnlinePayment>()
            OnlinePayment(
                storeViewModel = storeViewModel,
                navHostController = navHostController,
                profileNavHostController = profileNavHostController,
                type = args.type
            )
        }

        composable(Routes.Paypal) {
            Paypal(
                navHostController = navHostController
            )
        }

        composable(Routes.Payme) {
            Payme(
                navHostController = navHostController
            )
        }

        composable(Routes.ClickUp) {
            ClickUp(
                navHostController = navHostController
            )
        }


        composable(Routes.Promocode) {
            CreateStore(storeViewModel, profileNavHostController, navHostController)
        }

        composable<WithCode> { navBack->
            val args = navBack.toRoute<WithCode>()
            CreateStoreWithKey(
                storeViewModel = storeViewModel,
                onBack = { profileNavHostController.navigateUp() },
                goBack = true,
                type = args.type
            )
        }
        composable(Routes.AboutStore) {
            AboutStore(storeViewModel, navHostController)
        }
        composable(Routes.Payments) {
            StorePayments(navHostController)
        }
        composable(Routes.Orders) {
            OrdersScreen(navHostController, userViewModel, type = OrderType.STORE)
        }
        composable(Routes.Order) {
            OrderScreen(navHostController, userViewModel, type = OrderType.STORE)
        }
        composable(Routes.MyProducts) {
            MyProducts(storeViewModel, navHostController)
        }
        composable(Routes.MyProduct) {
            MyProductDetails(storeViewModel, navHostController)
        }
        composable(Routes.AddProduct) {
            AddProduct(storeViewModel, navHostController)
        }
        composable(Routes.EditProduct) {
            EditProduct(storeViewModel, navHostController)
        }
        composable(Routes.OrderProducts) {
            OrderProducts(navHostController, userViewModel)
        }
        composable(Routes.Address) {
            MyAddress(storeViewModel, navHostController)
        }

        composable(Routes.AddAddress) {
            AddAddress(
                storeViewModel = storeViewModel,
                navHostController = navHostController
            )
        }

        composable<UpdateLocationNavParams> { navback ->
            val args = navback.toRoute<UpdateLocationNavParams>()
            AddAddress(
                addressId = args.addressId,
                regionId = args.regionId,
                cityId = args.cityId,
                storeViewModel = storeViewModel,
                navHostController = navHostController
            )
        }

        composable(Routes.StoreChats) {
            StoreChatNavigation()
        }

        composable(Routes.HelperChats) {
            HelperChatNavigation()
        }
    }
}