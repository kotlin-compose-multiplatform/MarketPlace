package com.komekci.marketplace.features.chat.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.util.fastAny
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.komekci.marketplace.core.NotificationHelper
import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.core.common.Constant
import com.komekci.marketplace.features.auth.data.local.UserDataStore
import com.komekci.marketplace.features.chat.data.entity.Post
import com.komekci.marketplace.features.chat.data.entity.unread.GetUnreadMessagesItem
import com.komekci.marketplace.features.chat.data.repository.ChatsRepositoryImpl
import com.komekci.marketplace.features.chat.domain.model.ChatHistoryModel
import com.komekci.marketplace.features.chat.domain.model.ChatsModel
import com.komekci.marketplace.features.chat.domain.model.SubscribeChat
import com.komekci.marketplace.features.chat.domain.usecase.ChatsUseCase
import com.komekci.marketplace.features.chat.presentation.state.ChatHistoryState
import com.komekci.marketplace.features.chat.presentation.state.SendState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import io.socket.engineio.client.transports.WebSocket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.StringReader
import java.net.URI
import javax.inject.Inject
import kotlin.random.Random


@HiltViewModel
class OnChatViewModel @Inject constructor(
    private val useCase: ChatsUseCase,
    private val userDataStore: UserDataStore
) : ViewModel() {
    private val _state = MutableStateFlow(ChatHistoryState())
    val state: StateFlow<ChatHistoryState> = _state.asStateFlow()

    private val messageEvent = "message"
    private val notificationEvent = "notification"

    private val _sendState = MutableStateFlow(SendState())
    val sendState = _sendState.asStateFlow()

    private val _selectedRoom = MutableStateFlow<ChatsModel?>(null)
    val selectedRoom = _selectedRoom.asStateFlow()

    private val _unreadMessages = MutableStateFlow<List<GetUnreadMessagesItem>?>(null)
    val unreadMessages = _unreadMessages.asStateFlow()

    fun selectRoom(room: ChatsModel) {
        _selectedRoom.value = room
        subscribe(roomId = room.roomId)
    }

    fun unselectRoom() {
        _selectedRoom.value?.roomId?.let { unsubscribe(it) }
        _selectedRoom.value = null
    }

    val socket = mutableStateOf(getSocket("", ""))

    fun connectToSocket() {
        viewModelScope.launch {
            userDataStore.getUserData {
                if (it.token.isNullOrEmpty().not()) {
                    socket.value = getSocket(Constant.SOCKET_URL, it.token ?: "")
                    connectWebSocket()
                }
            }
        }
    }

    fun subscribe(roomId: String) {
        try {
            Log.e("Room-id", roomId)
            val gson = Gson()
            val data = SubscribeChat(roomId)
            socket.value?.emit("subscribe", gson.toJson(data).toString())
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun unsubscribe(roomId: String) {
        try {
            val gson = Gson()
            val data = SubscribeChat(roomId)
            socket.value?.emit("unsubscribe", gson.toJson(data).toString())
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun getUnreadMessages() {
        viewModelScope.launch {
            userDataStore.getUserData { user ->
                viewModelScope.launch {
                    useCase.getUnreadMessages(user.token ?: "").onEach {
                        when (it) {
                            is Resource.Error -> {}
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                _unreadMessages.value = it.data
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    private fun getSocket(url: String, token: String): Socket? {
        try {
            val queries: MutableMap<String, List<String>> =
                emptyMap<String, List<String>>().toMutableMap()
            queries["authorization"] = listOf(token)
            val options = IO.Options()
            options.forceNew = false
            options.multiplex = true
            options.transports = arrayOf("polling", WebSocket.NAME)
            options.upgrade = true
            options.rememberUpgrade = false
            options.query = null
            options.reconnection = true
            options.reconnectionAttempts = Int.MAX_VALUE
            options.reconnectionDelay = 1000
            options.reconnectionDelayMax = 5000
            options.randomizationFactor = 0.5
            options.timeout = 20000
            options.extraHeaders = queries
            return IO.socket(URI.create(url), options)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private fun disconnect() {
        socket.value!!.disconnect()
        socket.value!!.off(Socket.EVENT_CONNECT_ERROR, onConnectError)
        socket.value!!.off(Socket.EVENT_CONNECT, onConnect)
        socket.value!!.off(Socket.EVENT_DISCONNECT, onDisconnect)
        socket.value!!.off(messageEvent, onMessage)
        socket.value!!.off(notificationEvent, onNotification)
    }

    private fun connectWebSocket() {
        socket.value!!.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
        socket.value!!.on(Socket.EVENT_CONNECT, onConnect)
        socket.value!!.on(Socket.EVENT_DISCONNECT, onDisconnect)
        socket.value!!.on(messageEvent, onMessage)
        socket.value!!.on(notificationEvent, onNotification)
        socket.value!!.connect()
    }

    var onConnect = Emitter.Listener {
        Log.d("TAG", "Socket Connected!")
//        socket.value!!.emit("joinRoom", "otp_room")
        setStatus("Connected")
    }

    fun upsertMessage(message: ChatHistoryModel) {

        NotificationHelper.INSTANCE.sendNotification(message.message, message.message, Random.nextInt())


        _state.value.chats?.let { history ->
            if (history.fastAny { it.message == message.message && it.time == message.time }) {
                Log.e("History", "Update")
                val index =
                    history.indexOfLast { it.message == message.message && it.time == message.time }
                val start = history.subList(0, index).plus(message)
                val end = history.subList(index + 1, history.size)
                _state.value = _state.value.copy(
                    chats = start.plus(end)
                )
            } else {
                Log.e("History", "Insert")
                _state.value = _state.value.copy(
                    chats = _state.value.chats?.plus(
                        message
                    )
                )
            }
        }
    }

    private val onMessage =
        Emitter.Listener { args ->
            val reader = StringReader(args[0].toString())
            val data = Gson().fromJson(reader, Post::class.java)
            upsertMessage(data.toUiEntity())
            getUnreadMessages()
        }

    private val onNotification =
        Emitter.Listener { args ->
            val reader = StringReader(args[0].toString())
            val data = Gson().fromJson(reader, Post::class.java)
            upsertMessage(data.toUiEntity())
            getUnreadMessages()
        }

    private val onConnectError =
        Emitter.Listener { args ->
            setStatus("Connection error!")
        }
    private val onDisconnect = Emitter.Listener {
        setStatus("Disconnected")
    }

    private fun setStatus(status: String) {
        Log.e("TAG", status)
    }

    fun getChatHistory(roomId: String) {
        viewModelScope.launch {
            userDataStore.getUserData { user ->
                viewModelScope.launch {
                    useCase.getHistory(user.token ?: "", roomId).onEach {
                        when (it) {
                            is Resource.Loading -> {
                                _state.value = _state.value.copy(
                                    chats = it.data,
                                    loading = true,
                                    error = false
                                )
                            }

                            is Resource.Success -> {
                                _state.value = _state.value.copy(
                                    chats = it.data,
                                    loading = false,
                                    error = false
                                )
                                getUnreadMessages()
                            }

                            is Resource.Error -> {
                                _state.value = _state.value.copy(
                                    chats = it.data,
                                    loading = false,
                                    error = true
                                )
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    fun initChatHistory(roomId: String) {
        if (_state.value.chats.isNullOrEmpty()) {
            getChatHistory(roomId)
        }

        getUnreadMessages()
    }

    fun sendMessage(text: String, onSuccess: (Int) -> Unit = {}) {
        viewModelScope.launch {
            userDataStore.getUserData {
                viewModelScope.launch {
                    useCase.sendMessage(it.token ?: "", text, _selectedRoom.value?.roomId ?: "")
                        .onEach { result ->
                            when (result) {
                                is Resource.Error -> {
                                    _sendState.value = _sendState.value.copy(
                                        loading = false,
                                        error = result.message,
                                        message = result.errorMessage,
                                        code = result.code,
                                        data = result.data
                                    )
                                }

                                is Resource.Loading -> {
                                    _sendState.value = _sendState.value.copy(
                                        loading = true,
                                        error = result.message,
                                        message = result.errorMessage,
                                        code = result.code,
                                        data = result.data
                                    )
                                }

                                is Resource.Success -> {
                                    _sendState.value = _sendState.value.copy(
                                        loading = false,
                                        error = result.message,
                                        message = result.errorMessage,
                                        code = result.code,
                                        data = result.data
                                    )
                                    result.data?.let { msg ->
                                        val count = _state.value.chats?.size ?: 0
                                        upsertMessage(msg.post.toUiEntity())
                                        onSuccess(count.plus(1))
                                    }
                                }
                            }
                        }.launchIn(this)
                }
            }
        }
    }
}