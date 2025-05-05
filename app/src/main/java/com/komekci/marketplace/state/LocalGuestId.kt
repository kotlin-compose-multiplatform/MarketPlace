package com.komekci.marketplace.state

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf

val LocalGuestId = compositionLocalOf {
    mutableStateOf<String?>(null)
}