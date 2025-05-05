package com.komekci.marketplace.ui.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.komekci.marketplace.ui.navigation.RootNavigation

@Composable
fun AppScreen() {
    Surface(
        modifier = Modifier.safeDrawingPadding().fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        RootNavigation()
    }
}