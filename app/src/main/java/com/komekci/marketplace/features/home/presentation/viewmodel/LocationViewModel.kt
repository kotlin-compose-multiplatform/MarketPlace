package com.komekci.marketplace.features.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.features.home.domain.usecase.LocationUseCase
import com.komekci.marketplace.features.home.presentation.state.LocationStata
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val useCase: LocationUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(LocationStata())
    val state: StateFlow<LocationStata> = _state.asStateFlow()


    fun getLocations(region: Boolean = false) {
        viewModelScope.launch {
            useCase(region).onEach {
                when (it) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            locations = it.data,
                            loading = true,
                            error = false,
                            isEmpty = true
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            locations = it.data,
                            loading = false,
                            error = true,
                            isEmpty = true
                        )
                    }

                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            locations = it.data,
                            loading = false,
                            error = false,
                            isEmpty = it.data.isNullOrEmpty()
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    fun initLocations(region: Boolean = false) {
        if(_state.value.locations.isNullOrEmpty()) {
            getLocations(region)
        }
    }
}