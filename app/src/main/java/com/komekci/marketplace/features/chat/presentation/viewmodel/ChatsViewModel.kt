package com.komekci.marketplace.features.chat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.auth.data.local.UserDataStore
import com.komekci.marketplace.features.chat.domain.model.ChatsModel
import com.komekci.marketplace.features.chat.domain.usecase.ChatsUseCase
import com.komekci.marketplace.features.chat.presentation.state.ChatsState
import com.komekci.marketplace.features.chat.presentation.state.CreateChatState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val useCase: ChatsUseCase,
    private val userDataStore: UserDataStore
) : ViewModel() {
    private val _state = MutableStateFlow(ChatsState())
    val state: StateFlow<ChatsState> = _state.asStateFlow()

    private val _createState = MutableStateFlow(CreateChatState())
    val createState = _createState.asStateFlow()



    fun createChat(storeId: Int, onSuccess: (String)-> Unit) {
        viewModelScope.launch {
            userDataStore.getUserData {
                viewModelScope.launch {
                    useCase.createChat(it.token?:"", storeId).onEach { result->
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
                    useCase(it.token?:"").onEach {
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
}