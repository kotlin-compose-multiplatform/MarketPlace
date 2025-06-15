package com.komekci.marketplace.ui.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.R
import com.komekci.marketplace.state.LocalRouteState
import com.komekci.marketplace.ui.navigation.Routes

@Composable
fun EmptyState(
    message: String = "No data available",
    icon: ImageVector = Icons.Default.Warning, // Optional icon
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message,
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AppError(
    modifier: Modifier = Modifier,
    title: String = LocalStrings.current.somethingWentWrong,
    message: String = LocalStrings.current.errorMessage,
    actionText: String = LocalStrings.current.tryAgain,
    errorCode: Int? = 500,
    onRetry: () -> Unit
) {
    val strings = LocalStrings.current
    val codeError = if (errorCode != null) {
        if (errorCode >= 500)
            strings.serverError
        else if (errorCode >= 400)
            strings.clientError
        else if (errorCode >= 300)
            strings.warning
        else strings.somethingWentWrong
    } else {
        strings.somethingWentWrong
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error))

        LottieAnimation(
            modifier = Modifier.size(160.dp),
            composition = composition,
            iterations = LottieConstants.IterateForever,
        )

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = title,
            color = Color(0xFF2F313F),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.W700
            ),
            modifier = Modifier.padding(horizontal = 25.dp),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message.plus(if (errorCode != null) " / $codeError" else ""),
            color = Color(0xFF667085),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400),
            modifier = Modifier.padding(horizontal = 25.dp),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))
//        Button(onClick = { onRetry() }, shape = RoundedCornerShape(6.dp)) {
//            Text(
//                text = actionText,
//                color = Color.White,
//                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500)
//            )
//        }

    }
}

@Composable
fun LoginRequireScreen(
    modifier: Modifier = Modifier,
    title: String = LocalStrings.current.signIn,
    message: String = LocalStrings.current.requiredSign,
    actionText: String = LocalStrings.current.signIn
) {
    val strings = LocalStrings.current
    val globalState = LocalRouteState.current
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error))

        LottieAnimation(
            modifier = Modifier.size(160.dp),
            composition = composition,
            iterations = LottieConstants.IterateForever,
        )

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = title,
            color = Color(0xFF2F313F),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.W700
            ),
            modifier = Modifier.padding(horizontal = 25.dp),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message,
            color = Color(0xFF667085),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400),
            modifier = Modifier.padding(horizontal = 25.dp),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            globalState.value = globalState.value.copy(
                mainRoute = Routes.WelcomeScreen
            )
        }, shape = RoundedCornerShape(6.dp)) {
            Text(
                text = actionText,
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500)
            )
        }

    }
}