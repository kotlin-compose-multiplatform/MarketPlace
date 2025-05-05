package com.komekci.marketplace.ui.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.R

@Composable
fun NoData(
    modifier: Modifier = Modifier,
    showIcon: Boolean = true,
    text: String = LocalStrings.current.noData,
    onAction: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
       if(showIcon) {
           val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_data))

           LottieAnimation(
               modifier = Modifier.size(160.dp),
               composition = composition,
               iterations = LottieConstants.IterateForever,
           )
           Spacer(modifier = Modifier.height(12.dp))
       }


        Text(
            text = text,
            color = Color(0xFF2F313F),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.W700
            ),
            modifier = Modifier.padding(horizontal = 25.dp),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )

//       if(showIcon) {
//           Spacer(modifier = Modifier.height(8.dp))
//           Button(onClick = { onAction() }, shape = RoundedCornerShape(6.dp)) {
//               Text(
//                   text = LocalStrings.current.tryAgain,
//                   color = Color.White,
//                   style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500)
//               )
//           }
//       }

    }
}