package com.komekci.marketplace.ui.navigation

import androidx.annotation.Keep
import com.komekci.marketplace.features.create_store.data.entity.store.MyStore
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class OnlinePayment(
    /**
     * type must be NEW or EXPIRE
     */
    val type: String
)

@Serializable
@Keep
data class WithCode(
    /**
     * type must be NEW or EXPIRE
     */
    val type: String
)

class Routes {
    companion object {
        const val OnBoarding = "onboarding"
        const val SplashScreen = "splash"
        const val WelcomeScreen = "welcome"
        const val AuthScreen = "auth"
        const val CreateAccountScreen = "create_account"
        const val LoginScreen = "login"
        const val Main = "main"
        const val Home = "home"
        const val Basket = "basket"
        const val Chats = "chats"
        const val Profile = "profile"
        const val Shops = "shops"
        const val Category = "category"
        const val Help = "help"
        const val Location = "location"
        const val Discount = "discount"
        const val Faq = "faq"
        const val Notifications = "notifications"
        const val onShop = "onShop"
        const val onCategory = "onCategory"
        const val AboutUs = "about_us"
        const val Details = "details/{id}"
        const val On_Country = "country/{name}/{c_id}"
        const val OnChat = "onChat/{roomId}"
        const val TermsOfUse = "termsOfUse"
        const val Address = "address"
        const val Orders = "orders"
        const val Order = "order"
        const val BasketDetails = "basket_details/{shopId}"
        const val Payments = "payments"
        const val Favs = "favorites"
        const val Promocode = "promocode"
        const val MyStore = "select_payment_type/{start}"
        const val SelectPaymentType = "select_payment_type"
        const val SelectPaymentType2 = "select_payment_type_2"
        const val Paypal = "paypal"
        const val Payme = "payme"
        const val ClickUp = "click_up"
        const val AboutStore = "about_store"
        const val MyProducts = "my_products"
        const val MyProduct = "my_product"
        const val AddProduct = "add_product"
        const val EditProduct = "edit_product"
        const val OrderProducts = "order_products"
        const val AddAddress = "add_address"
        const val StoreChats = "store_chats"
        const val HelperChats = "helper_chats"
        const val SearchRoute = "search/{text}"
    }
}