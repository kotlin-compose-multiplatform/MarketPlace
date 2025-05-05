package com.komekci.marketplace.ui.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.komekci.marketplace.features.profile.presentation.viewmodel.ProfileViewModel

@Composable
fun CheckAuthScreen(
    errorContent: @Composable () -> Unit = { LoginRequireScreen() },
    successContent: @Composable () -> Unit = {}
) {
    val userViewModel: ProfileViewModel = hiltViewModel()
    val state = userViewModel.profile.collectAsState()
    LaunchedEffect(true) {
        userViewModel.getProfile()
    }
    state.value?.let { user->
        if(user.token.isNullOrEmpty()) {
            errorContent()
        } else {
            successContent()
        }
    }
    if(state.value==null) {
        errorContent()
    }
}