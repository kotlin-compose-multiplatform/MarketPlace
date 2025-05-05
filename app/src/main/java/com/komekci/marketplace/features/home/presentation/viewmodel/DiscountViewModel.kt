package com.komekci.marketplace.features.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.auth.data.local.UserDataStore
import com.komekci.marketplace.features.home.domain.usecase.DiscountUseCase
import com.komekci.marketplace.features.home.presentation.state.DiscountState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscountViewModel @Inject constructor (
    private val useCase: DiscountUseCase,
    private val userDataStore: UserDataStore
): ViewModel() {
    private val _state = MutableStateFlow(DiscountState())
    val state: StateFlow<DiscountState> = _state.asStateFlow()

    fun getDiscounts() {
        viewModelScope.launch {
           userDataStore.getUserData {
               viewModelScope.launch {
                   useCase(it.token?:"").onEach {
                       when(it) {
                           is Resource.Loading -> {
                               _state.value = _state.value.copy(
                                   discounts = it.data,
                                   loading = true,
                                   error = false,
                                   isEmpty = true
                               )
                           }
                           is Resource.Error -> {
                               _state.value = _state.value.copy(
                                   discounts = it.data,
                                   loading = false,
                                   error = true,
                                   isEmpty = true
                               )
                           }
                           is Resource.Success -> {
                               _state.value = _state.value.copy(
                                   discounts = it.data,
                                   loading = false,
                                   error = false,
                                   isEmpty = it.data.isNullOrEmpty()
                               )
                           }
                       }
                   }.launchIn(this)
               }
           }
        }
    }

    fun initDiscounts() {
        if(_state.value.discounts.isNullOrEmpty()) {
            getDiscounts()
        }
    }
}