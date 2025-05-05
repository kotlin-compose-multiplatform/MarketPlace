package com.komekci.marketplace.state

import androidx.annotation.Keep
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import com.komekci.marketplace.core.locale.Locales
import com.komekci.marketplace.features.favorite.domain.model.FavoriteType

@Keep
data class AppSettingsState(
    val languageTag: String = Locales.TK,
    val hideBottomNavigation: Boolean = false
)

val LocalSettings = compositionLocalOf { AppSettingsState() }

val SetAppSettings = compositionLocalOf<(AppSettingsState) -> Unit> { {} }

@Keep
data class FavSettings(
    val reFetchFavorites: () -> Unit = {},
    val likeProduct: (String, FavoriteType) -> Unit = { _, _ -> },
    val checkIsLiked: (String, FavoriteType) -> Boolean = { _, _ -> false}
)

val LocalFavSettings = compositionLocalOf {
    mutableStateOf(FavSettings())
}