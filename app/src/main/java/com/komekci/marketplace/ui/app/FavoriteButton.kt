package com.komekci.marketplace.ui.app

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.komekci.marketplace.R
import com.komekci.marketplace.features.favorite.domain.model.FavoriteType
import com.komekci.marketplace.features.favorite.presentation.viewmodel.FavoriteViewModel
import com.komekci.marketplace.state.LocalFavSettings
import com.komekci.marketplace.state.LocalSettings

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    size: Dp = 36.dp,
    shape: Shape = RoundedCornerShape(6.dp),
    id: String,
    type: FavoriteType,
    onClick: () -> Unit
) {

    val appSettingsState = LocalFavSettings.current
    val isLiked = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(true) {
        isLiked.value = appSettingsState.value.checkIsLiked(id, type)
    }
    Box(modifier = modifier.clickable {
        isLiked.value = isLiked.value.not()
        onClick()
        appSettingsState.value.likeProduct(id, type)
    }, contentAlignment = Alignment.Center) {
        Box(Modifier.size(size).background(
            color = Color.Black.copy(alpha = 0.1f),
            shape = shape
        ), contentAlignment = Alignment.Center) {
            Icon(
                painter = painterResource(id = if (isLiked.value) R.drawable.fav_filled else R.drawable.favorite_outline),
                contentDescription = null,
                tint = if (isLiked.value) Color(0xFFFF0505) else Color(0xFFB9BBC6),
                modifier = Modifier.size(19.dp)
            )
        }
    }
}