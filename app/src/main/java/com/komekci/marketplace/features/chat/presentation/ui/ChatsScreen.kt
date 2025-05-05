package com.komekci.marketplace.features.chat.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.R
import com.komekci.marketplace.features.chat.domain.model.ChatsModel
import com.komekci.marketplace.features.chat.presentation.viewmodel.ChatsViewModel
import com.komekci.marketplace.features.chat.presentation.viewmodel.OnChatViewModel
import com.komekci.marketplace.state.LocalRouteState
import com.komekci.marketplace.ui.app.AppError
import com.komekci.marketplace.ui.app.AppLoading
import com.komekci.marketplace.ui.app.NoData
import com.komekci.marketplace.ui.navigation.Routes

@Composable
fun ChatsScreen(
    chatsViewModel: ChatsViewModel = hiltViewModel(),
    chatViewModel: OnChatViewModel = hiltViewModel(),
    navHostController: NavHostController,
    onChatClick: (ChatsModel) -> Unit = {}
) {
    val strings = LocalStrings.current

    val state = chatsViewModel.state.collectAsState()

    LaunchedEffect(true) {
        chatsViewModel.initChats()
    }
    val chatsState = chatViewModel.state.collectAsState()
    LaunchedEffect(chatsState.value) {
        chatsState.value.chats?.let {
            chatsViewModel.getChats()
        }
    }
    val globalState = LocalRouteState.current
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = strings.chats,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.W700
            ),
            color = Color(0xFF2F313F),
            modifier = Modifier.padding(16.dp)
        )
        if (state.value.loading) {
            AppLoading(modifier = Modifier.fillMaxSize())
        } else if (state.value.error) {
            AppError(modifier = Modifier.fillMaxSize()) {
                chatsViewModel.getChats()
            }
        } else if (state.value.chats.isNullOrEmpty()) {
            NoData(modifier = Modifier.fillMaxSize(), onAction = {
                chatsViewModel.getChats()
            })
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(top = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.warningcircle),
                            contentDescription = null,
                            tint = Color(0xFF343330)
                        )
                        Text(
                            text = strings.chatsDescription,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400),
                            color = Color(0xFF1C2024),
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                    }
                }

                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(top = 16.dp),
                        color = Color(0xFFF2F2F5)
                    )
                }

                state.value.chats?.let { list ->
                    items(list) { item ->
                        ChatsItem(item = item) {
                            val route = Routes.OnChat.replace("{roomId}", item.roomId)
                            Log.e("Room", route)
                            globalState.value = globalState.value.copy(
                                chatRoute = route
                            )
                            onChatClick(item)
                            navHostController.navigate(route)
                        }
                    }
                }
            }
        }
    }
}