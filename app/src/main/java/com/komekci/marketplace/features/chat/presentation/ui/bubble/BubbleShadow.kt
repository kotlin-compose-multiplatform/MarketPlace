package com.komekci.marketplace.features.chat.presentation.ui.bubble


import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.Dp

@Immutable
@Keep
data class BubbleShadow(
    val elevation: Dp,
    val ambientColor: Color = DefaultShadowColor,
    val spotColor: Color = DefaultShadowColor,
)