package com.komekci.marketplace.state

import androidx.annotation.Keep
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import com.komekci.marketplace.ui.navigation.Routes

@Keep
data class RouteGlobalState(
    val mainRoute: String = Routes.SplashScreen,
    val homeRoute: String = Routes.Home,
    val basketRoute: String = Routes.Basket,
    val profileRoute: String = Routes.Profile,
    val chatRoute: String = Routes.Chats,
    val chatRoomId: String? = null
)

val LocalRouteState = compositionLocalOf {
    mutableStateOf(RouteGlobalState())
}

