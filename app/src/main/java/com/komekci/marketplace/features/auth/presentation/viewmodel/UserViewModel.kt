package com.komekci.marketplace.features.auth.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.auth.data.entity.LocalUserEntity
import com.komekci.marketplace.features.auth.data.local.UserDataStore
import com.komekci.marketplace.features.auth.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor (
    private val authUseCase: AuthUseCase,
    private val userDataStore: UserDataStore
): ViewModel() {

    private val _localUser = MutableStateFlow<LocalUserEntity?>(null)
    val localUser: StateFlow<LocalUserEntity?> = _localUser.asStateFlow()

    var guestId = mutableStateOf<String?>(null)
        private set

    fun getGuestId() {
        viewModelScope.launch {
            authUseCase.getGuestId().onEach {
                when(it) {
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        guestId.value = it.data
                    }
                }
            }.launchIn(this)
        }
    }

    fun logout() {
        viewModelScope.launch {
            userDataStore.logout()
            getUserData()
        }
    }

    fun clearToken() {
        viewModelScope.launch {
            userDataStore.clearToken()
            getUserData()
        }
    }

    fun saveLocation(countryId: String, regionId: String) {
        viewModelScope.launch {
            userDataStore.saveCountryId(countryId)
            userDataStore.saveRegionId(regionId)
            getUserData()
        }
    }

    suspend fun isLocationSelected(): Boolean {
        val countryId = userDataStore.getCountryId()
        val regionId = userDataStore.getRegionId()
        return countryId.isNullOrEmpty().not() && regionId.isNullOrEmpty().not() && countryId!="1"
    }

    fun getUserData(onResult: (LocalUserEntity) -> Unit = {}) {
        viewModelScope.launch {
            userDataStore.getUserData { user->
                Log.e("TAG", "Three: "+user)
                _localUser.update { user }
                onResult(user)
            }

        }
    }

    fun saveUserData(user: LocalUserEntity) {
        viewModelScope.launch {
            userDataStore.saveUserData(user)
            getUserData()
        }
    }

    fun changeIsFirst(isFirst: String) {
        viewModelScope.launch {
            userDataStore.saveIsFirstLaunch(isFirst)
            getUserData()
        }
    }

    fun changeSkipAuth(skip: String) {
        viewModelScope.launch {
            userDataStore.saveSkipAuth(skip)
            getUserData()
        }
    }

    init {
        viewModelScope.launch {
            userDataStore.getUserData {
                _localUser.update { it }
            }

            getGuestId()

        }
    }


}