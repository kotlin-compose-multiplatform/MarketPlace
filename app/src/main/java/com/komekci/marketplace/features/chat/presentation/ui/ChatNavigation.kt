package com.komekci.marketplace.features.chat.presentation.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.komekci.marketplace.features.chat.presentation.viewmodel.ChatsViewModel
import com.komekci.marketplace.features.chat.presentation.viewmodel.OnChatViewModel
import com.komekci.marketplace.state.LocalRouteState
import com.komekci.marketplace.state.LocalSettings
import com.komekci.marketplace.state.SetAppSettings
import com.komekci.marketplace.ui.navigation.Routes

@Composable
fun ChatNavigation(
    viewModel: OnChatViewModel = hiltViewModel()
) {
    val chatNavHostController = rememberNavController()
    val globalRoutes = LocalRouteState.current
    val navBackStackEntry by chatNavHostController.currentBackStackEntryAsState()
    val chatsViewModel: ChatsViewModel = hiltViewModel()
    LaunchedEffect(true) {
        if (viewModel.socket.value == null || viewModel.socket.value!!.connected().not()) {
            viewModel.connectToSocket()
        }
    }

    val appSettingsState = LocalSettings.current
    val setAppSettingsState = SetAppSettings.current




    LaunchedEffect(navBackStackEntry?.destination?.route) {
        navBackStackEntry?.destination?.route?.let {
            setAppSettingsState(appSettingsState.copy(
                hideBottomNavigation = it!=Routes.Chats
            ))
            globalRoutes.value = globalRoutes.value.copy(
                chatRoute = it
            )
        }
    }
    LaunchedEffect(globalRoutes.value.chatRoute) {
        chatNavHostController.navigate(globalRoutes.value.chatRoute) {
            launchSingleTop = true
        }
    }

    LaunchedEffect(globalRoutes.value.chatRoomId) {
        Log.e("Room-g", globalRoutes.value.chatRoomId?:"")
        globalRoutes.value.chatRoomId?.let { id->
            chatsViewModel.getChats { chats->
                chats.find { it.roomId == id }?.let {
                    viewModel.selectRoom(it)
                    val route = Routes.OnChat.replace("{roomId}", it.roomId)
                    Log.e("Room", route)
                    globalRoutes.value = globalRoutes.value.copy(
                        chatRoute = route,
                        chatRoomId = null
                    )
                    chatNavHostController.navigate(route)
                }
            }
        }
    }

    NavHost(navController = chatNavHostController, startDestination = Routes.Chats) {
        composable(Routes.Chats) {
            ChatsScreen(
                navHostController = chatNavHostController,
                chatsViewModel = chatsViewModel,
                chatViewModel = viewModel
            ) { model ->
                viewModel.selectRoom(model)
            }
        }
        composable(
            Routes.OnChat,
            arguments = listOf(
                navArgument("roomId") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { navBackStackEntry ->
            val roomId = navBackStackEntry.arguments?.getString("roomId")
            OnChatScreen(
                viewModel,
                roomId ?: ""
            ) {
                chatNavHostController.navigateUp()
            }
        }
    }
}