package com.komekci.marketplace.ui.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AskPermissions(
    permissions: List<String>,
    show: Boolean,
    onSuccess: ()-> Unit
) {
    if(show) {
        val permissionState = rememberMultiplePermissionsState(permissions)
        LaunchedEffect(permissions) {
            permissionState.launchMultiplePermissionRequest()
        }

        LaunchedEffect(permissionState.allPermissionsGranted) {
            if(permissionState.allPermissionsGranted) {
                onSuccess()
            }
        }
    }
}