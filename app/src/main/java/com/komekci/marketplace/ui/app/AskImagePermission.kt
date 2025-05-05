package com.komekci.marketplace.ui.app

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AskImagePermission(
    show: Boolean,
    onPermissionGranted: (String) -> Unit,
    onPermissionDenied: (String) -> Unit
) {
    if(show) {
        var permissionsToRequest = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
        )
        val context = LocalContext.current as Activity
        val viewModel = viewModel<PermissionViewModel>()
        val dialogQueue = viewModel.visiblePermissionDialogQueue

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionsToRequest = permissionsToRequest.plus(Manifest.permission.READ_MEDIA_IMAGES)
        }
        val permissionResultLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = { perms ->
                permissionsToRequest.forEach { permission ->
                    val isGranted = perms[permission] == true
                    if(isGranted) {
                        onPermissionGranted(permission)
                    } else {
                        onPermissionDenied(permission)
                    }
                }
            }
        )
        LaunchedEffect(permissionsToRequest) {
            permissionResultLauncher.launch(permissionsToRequest)
        }

        dialogQueue
            .reversed()
            .forEach { permission->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    PermissionDialog(
                        permissionTextProvider = ImagePermissionTextProvider(),
                        isPermanentlyDeclined = !context.shouldShowRequestPermissionRationale(
                            permission
                        ),
                        onDismiss = viewModel::dismissDialog,
                        onOkClick = {
                            viewModel.dismissDialog()
                            permissionResultLauncher.launch(
                                arrayOf(permission)
                            )
                        },
                        onGoToAppSettingsClick = {
                            context.openAppSettings()
                        }
                    )
                }
            }
    }
}

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}