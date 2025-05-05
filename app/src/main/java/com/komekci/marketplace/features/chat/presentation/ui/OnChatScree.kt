package com.komekci.marketplace.features.chat.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.komekci.marketplace.R
import com.komekci.marketplace.core.utils.DateHelper
import com.komekci.marketplace.features.chat.presentation.ui.bubble.ArrowAlignment
import com.komekci.marketplace.features.chat.presentation.ui.bubble.bubble
import com.komekci.marketplace.features.chat.presentation.ui.bubble.rememberBubbleState
import com.komekci.marketplace.features.chat.presentation.viewmodel.OnChatViewModel
import com.komekci.marketplace.ui.app.AppLoading
import kotlinx.coroutines.launch

@Composable
fun OnChatScreen(
    viewModel: OnChatViewModel = hiltViewModel(),
    roomId: String,
    onBack: () -> Unit
) {
    val openInfo = remember {
        mutableStateOf(false)
    }

    val selectedRoom = viewModel.selectedRoom.collectAsState()

    fun back() {
        viewModel.unselectRoom()
        onBack()
    }

    BackHandler {
       back()
    }

    LaunchedEffect(selectedRoom.value) {
        selectedRoom.value?.let {
            viewModel.getChatHistory(it.roomId)
        }
    }

    val message = rememberSaveable {
        mutableStateOf("")
    }

    val scrollState = rememberLazyListState()
    val state = viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    fun sendMessage(msg: String) {
        viewModel.sendMessage(msg) {
            message.value = ""
            coroutineScope.launch {
                scrollState.animateScrollToItem(it)
            }
        }
    }

    com.komekci.marketplace.features.store_chat.presentation.ui.ChatInfoScreen(
        open = openInfo.value,
        name = selectedRoom.value?.name ?: ""
    ) {
        openInfo.value = false
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .clickable {
                            back()
                        })
                Text(
                    text = selectedRoom.value?.name ?: "",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.W600, fontSize = 17.sp
                    ),
                    color = Color(0xFF0F1E3C),
                    modifier = Modifier.align(Alignment.Center)
                )

            }
            if (state.value.loading) {
                AppLoading(Modifier.fillMaxSize())
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    state = scrollState
                ) {
                    state.value.chats?.let {
                        coroutineScope.launch {
                            scrollState.animateScrollToItem(it.size)
                        }
                        items(it.size) { index ->
                            val isUserMessage = it[index].whoPosted == "user"
                            val item = it[index]
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = if (isUserMessage) Arrangement.End else Arrangement.Start
                            ) {
                                if (isUserMessage) {
                                    com.komekci.marketplace.features.store_chat.presentation.ui.SentMessage(
                                        text = item.message,
                                        time = DateHelper.convertDateAndTime(item.time) ?: "",
                                        status = item.status
                                    )
                                } else {
                                    com.komekci.marketplace.features.store_chat.presentation.ui.ReceivedMessage(
                                        text = item.message,
                                        time = DateHelper.convertDateAndTime(item.time) ?: "",
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        HorizontalDivider(color = Color(0xFFEDEDED))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BasicTextField(value = message.value,
                onValueChange = { message.value = it },
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(18.dp))
                    .border(
                        width = 1.dp,
                        color = Color(0xFF3C3C43).copy(alpha = 0.36f),
                        shape = RoundedCornerShape(18.dp)
                    )
                    .padding(6.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(
                    onSend = {
                        sendMessage(message.value)
                    }
                ),
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.W500,
                    color = Color(0xFF0F1828)
                ),
                decorationBox = {
                    Box(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        if (message.value.isEmpty()) {
                            Text(
                                text = "Message",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400),
                                color = Color(0xFF3C3C43).copy(alpha = 0.6f)
                            )
                        } else {
                            it()
                        }
                    }
                }
//                colors = TextFieldDefaults.colors(
//                    unfocusedContainerColor = Color(0xFFF7F7FC),
//                    focusedContainerColor = Color(0xFFF7F7FC),
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedIndicatorColor = Color.Transparent,
//                    disabledIndicatorColor = Color.Transparent
//                )
            )

            IconButton(onClick = { sendMessage(message.value) }) {
                Icon(
                    painter = painterResource(id = R.drawable.send_message),
                    contentDescription = "Send",
                    tint = Color(0xFF297C3B)
                )
            }
        }
    }
}

@Composable
fun ReceivedMessage(
    text: String, time: String,
) {
    val bubbleStateDate = rememberBubbleState(
        alignment = ArrowAlignment.LeftBottom,
        cornerRadius = 5.dp,
    )
    Column(horizontalAlignment = Alignment.End) {
        Column(
            Modifier
                .padding(horizontal = 16.dp)
                .padding(start = 22.dp)
                .bubble(
                    bubbleState = bubbleStateDate,
                    color = Color(0xFF297C3B),
                )
                .wrapContentSize()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = text,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 15.sp, fontWeight = FontWeight.W500
                    )
                )

            }
        }
        Text(
            text = time, color = Color(0xFF8A9099), style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.W500
            ), modifier = Modifier.padding(end = 16.dp, bottom = 12.dp, top = 8.dp)
        )
    }
}

@Composable
fun SentMessage(
    text: String, time: String, status: String
) {
    val bubbleStateDate = rememberBubbleState(
        alignment = ArrowAlignment.RightBottom,
        cornerRadius = 5.dp,
    )
    Column(horizontalAlignment = Alignment.Start) {
        Column(
            Modifier
                .padding(horizontal = 16.dp)
                .padding(end = 22.dp)
                .bubble(
                    bubbleState = bubbleStateDate,
                    color = Color.Transparent,
                    borderStroke = BorderStroke(1.dp, Color(0xFFE8E9EB))
                )
                .padding(end = 12.dp)
                .wrapContentSize()
        ) {
            Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.End) {
                Text(
                    text = text,
                    color = Color(0xFF595F69),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 15.sp, fontWeight = FontWeight.W500
                    )
                )
                if (status == "pending") {
                    com.komekci.marketplace.features.store_chat.presentation.ui.PendingStatus()
                } else if (status == "delivery") {
                    com.komekci.marketplace.features.store_chat.presentation.ui.DeliveryStatus()
                } else {
                    com.komekci.marketplace.features.store_chat.presentation.ui.SentStatus()
                }
//
//

            }
        }
        Text(
            text = time, color = Color(0xFF8A9099), style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.W500
            ), modifier = Modifier.padding(start = 16.dp, bottom = 12.dp, top = 8.dp)
        )
    }
}

@Composable
fun PendingStatus() {
    Icon(
        painterResource(id = R.drawable.baseline_access_time_24),
        contentDescription = null,
        tint = Color(0xFF297C3B),
        modifier = Modifier
            .size(12.dp)
            .offset(y = -(6).dp, x = 18.dp)
    )
}

@Composable
fun DeliveryStatus() {
    Row {
        com.komekci.marketplace.features.store_chat.presentation.ui.SentStatus()
        com.komekci.marketplace.features.store_chat.presentation.ui.SentStatus()
    }
}

@Composable
fun SentStatus() {
    Icon(
        painterResource(id = R.drawable.check),
        contentDescription = null,
        tint = Color(0xFF297C3B),
        modifier = Modifier
            .size(12.dp)
            .offset(y = -(6).dp, x = 18.dp)
    )
}

@Composable
fun ChatInfoScreen(
    name: String,
    address: String? = null,
    instagram: String? = null,
    phone: String? = null,
    open: Boolean, onClose: () -> Unit,
) {
    if (open) {
        Dialog(
            onDismissRequest = onClose, properties = DialogProperties()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = name,
                        color = Color(0xFF2F313F),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 20.sp, fontWeight = FontWeight.W700
                        ),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier.weight(1f)
                    )
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .border(1.5.dp, Color(0xFFF2F2F5), CircleShape)
                            .clickable { onClose() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Clear, contentDescription = null, tint = Color(0xFF8B8D98)
                        )
                    }
                }
                address?.let {
                    com.komekci.marketplace.features.store_chat.presentation.ui.ChatInfoItem(
                        icon = R.drawable.location,
                        text = it
                    ) {

                    }
                }
                instagram?.let {
                    com.komekci.marketplace.features.store_chat.presentation.ui.ChatInfoItem(
                        icon = R.drawable.instagramlogo,
                        text = it
                    ) {

                    }
                }
                phone?.let {
                    com.komekci.marketplace.features.store_chat.presentation.ui.ChatInfoItem(
                        icon = R.drawable.phone,
                        text = it
                    ) {

                    }
                }
            }
        }
    }

}

@Composable
fun ChatInfoItem(
    icon: Int, text: String, onClick: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painterResource(id = icon),
            contentDescription = null,
            tint = Color(0xFF343330),
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400),
            color = Color(0xFF1C2024)
        )
    }
}
