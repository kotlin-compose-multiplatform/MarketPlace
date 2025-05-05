package com.komekci.marketplace.features.store_chat.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.komekci.marketplace.features.store_chat.presentation.viewmodel.StoreChatsViewModel
import com.komekci.marketplace.features.store_chat.presentation.viewmodel.StoreOnChatViewModel
import com.komekci.marketplace.ui.navigation.Routes

@Composable
fun StoreChatNavigation(
    viewModel: StoreOnChatViewModel = hiltViewModel()
) {
    val chatNavHostController = rememberNavController()
    val chatsViewModel: StoreChatsViewModel = hiltViewModel()
    LaunchedEffect(true) {
        if (viewModel.socket.value == null || viewModel.socket.value!!.connected().not()) {
            viewModel.connectToSocket()
        }
    }

    NavHost(navController = chatNavHostController, startDestination = Routes.Chats) {
        composable(Routes.Chats) {
            StoreChatsScreen(
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
            StoreOnChatScreen(viewModel, roomId ?: "") {
                chatNavHostController.navigateUp()
            }
        }
    }
}