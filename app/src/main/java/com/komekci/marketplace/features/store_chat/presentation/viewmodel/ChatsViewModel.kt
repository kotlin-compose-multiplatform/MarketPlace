package com.komekci.marketplace.features.store_chat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.auth.data.local.UserDataStore
import com.komekci.marketplace.features.chat.data.entity.CreateKomekchiChatRequest
import com.komekci.marketplace.features.chat.domain.model.ChatsModel
import com.komekci.marketplace.features.chat.presentation.state.ChatsState
import com.komekci.marketplace.features.chat.presentation.state.CreateChatState
import com.komekci.marketplace.features.store_chat.domain.usecase.StoreChatsUseCase
import com.komekci.marketplace.features.store_chat.presentation.state.KomekchiListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreChatsViewModel @Inject constructor(
    private val useCase: StoreChatsUseCase,
    private val userDataStore: UserDataStore
) : ViewModel() {
    private val _state = MutableStateFlow(ChatsState())
    val state: StateFlow<ChatsState> = _state.asStateFlow()

    private val _createState = MutableStateFlow(CreateChatState())
    val createState = _createState.asStateFlow()

    private val _komekchiList = MutableStateFlow(KomekchiListState())
    val komekchiListState = _komekchiList.asStateFlow()

    fun getHelperList() {
        viewModelScope.launch {
            userDataStore.getUserData {
                viewModelScope.launch {
                    useCase.getKomekchiList(it.store_token?:"").onEach {
                        when(it) {
                            is Resource.Error -> {
                                _komekchiList.value = _komekchiList.value.copy(
                                    loading = false,
                                    error = it.message,
                                    list = it.data
                                )
                            }
                            is Resource.Loading -> {
                                _komekchiList.value = _komekchiList.value.copy(
                                    loading = true,
                                    error = it.message,
                                    list = it.data
                                )
                            }
                            is Resource.Success -> {
                                _komekchiList.value = _komekchiList.value.copy(
                                    loading = false,
                                    error = it.message,
                                    list = it.data
                                )
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    fun createChat(storeId: Int, onSuccess: (String)-> Unit) {
        viewModelScope.launch {
            userDataStore.getUserData {
                viewModelScope.launch {
                    useCase.createChat(it.store_token?:"", storeId).onEach { result->
                        when(result) {
                            is Resource.Error -> {
                                _createState.value = _createState.value.copy(
                                    loading = false,
                                    error = result.message,
                                    message = result.errorMessage,
                                    code = result.code,
                                    data = result.data
                                )
                            }
                            is Resource.Loading -> {
                                _createState.value = _createState.value.copy(
                                    loading = true,
                                    error = result.message,
                                    message = result.errorMessage,
                                    code = result.code,
                                    data = result.data
                                )
                            }
                            is Resource.Success -> {
                                _createState.value = _createState.value.copy(
                                    loading = false,
                                    error = result.message,
                                    message = result.errorMessage,
                                    code = result.code,
                                    data = result.data
                                )
                                result.data?.let { room->
                                    onSuccess(room.roomId)
                                }
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    fun createHelperChat(komekchiId: Int, onSuccess: (String)-> Unit) {
        viewModelScope.launch {
            userDataStore.getUserData {
                viewModelScope.launch {
                    useCase.createKomekchiChat(it.store_token?:"", CreateKomekchiChatRequest(komekchiId)).onEach { result->
                        when(result) {
                            is Resource.Error -> {
                                _createState.value = _createState.value.copy(
                                    loading = false,
                                    error = result.message,
                                    message = result.errorMessage,
                                    code = result.code,
                                    data = result.data
                                )
                            }
                            is Resource.Loading -> {
                                _createState.value = _createState.value.copy(
                                    loading = true,
                                    error = result.message,
                                    message = result.errorMessage,
                                    code = result.code,
                                    data = result.data
                                )
                            }
                            is Resource.Success -> {
                                _createState.value = _createState.value.copy(
                                    loading = false,
                                    error = result.message,
                                    message = result.errorMessage,
                                    code = result.code,
                                    data = result.data
                                )
                                result.data?.let { room->
                                    onSuccess(room.roomId)
                                }
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    fun getChats(onSuccess: (List<ChatsModel>) -> Unit = {}) {
        viewModelScope.launch {
            userDataStore.getUserData {
                viewModelScope.launch {
                    useCase(it.store_token?:"").onEach {
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
                                it.data?.let { it1 -> onSuccess(it1) }

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

    fun initChats() {
        if(_state.value.chats.isNullOrEmpty()) {
            getChats()
        }
    }

    fun initHelperList() {
        if(_komekchiList.value.list.isNullOrEmpty()) {
            getHelperList()
        }
    }
}