package com.komekci.marketplace.features.basket.presentation.ui

import android.webkit.WebSettings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.kevinnzou.web.LoadingState
import com.kevinnzou.web.WebView
import com.kevinnzou.web.rememberWebViewState



@Composable
fun PaymentConfirmation(
    open: Boolean,
    onClose: ()-> Unit,
    url: String,
    finalUrl: String,
    onSuccess: ()-> Unit
) {
    if(open) {
        Dialog(
            onDismissRequest = {
                onClose()
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            Column(Modifier.fillMaxSize().background(
                color = MaterialTheme.colorScheme.background
            )) {
                val state = rememberWebViewState(url)

                LaunchedEffect(state.lastLoadedUrl) {
                    if(state.lastLoadedUrl == finalUrl) {
                        onSuccess()
                    }
                }
                val loadingState = state.loadingState
                if (loadingState is LoadingState.Loading) {
                    LinearProgressIndicator(
                        progress = { loadingState.progress },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                WebView(
                    modifier = Modifier.fillMaxSize(),
                    state = state,
                    onCreated = {
                        it.settings.javaScriptEnabled = true
                        it.settings.javaScriptCanOpenWindowsAutomatically = true
                        it.settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
                        it.settings.builtInZoomControls = false
                        it.settings.loadWithOverviewMode = true
                        it.settings.loadsImagesAutomatically = true
                        it.settings.useWideViewPort = true
                        it.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
                    }
                )
            }
        }
    }
}