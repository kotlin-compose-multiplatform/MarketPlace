package com.komekci.marketplace.features.basket.presentation.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.core.utils.toFixed
import com.komekci.marketplace.features.basket.presentation.state.BasketPrice
import com.komekci.marketplace.ui.app.DashedDivider
import com.komekci.marketplace.ui.theme.newTextColor

@Preview(showSystemUi = true)
@Composable
fun TotalBasket(
    modifier: Modifier = Modifier,
    price: BasketPrice = BasketPrice(),
    onClick: () -> Unit = {}
) {
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val lifecycleEventObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> {
                    Log.d("DisposableEffect", "Calling onCreate...!")
                }

                Lifecycle.Event.ON_START -> {
                    Log.d("DisposableEffect", "Calling onStart...!")
                }

                Lifecycle.Event.ON_RESUME -> {
                    Log.d("DisposableEffect", "Calling onResume...!")
                }

                Lifecycle.Event.ON_PAUSE -> {
                    Log.d("DisposableEffect", "Calling onPause...!")
                }

                Lifecycle.Event.ON_DESTROY -> {
                    Log.d("DisposableEffect", "Calling onDestroy...!")
                }

                Lifecycle.Event.ON_STOP -> {
                    Log.d("DisposableEffect", "Calling onStop...!")
                }

                Lifecycle.Event.ON_ANY -> {
                    Log.d("DisposableEffect", "Calling onAny...!")
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifecycleEventObserver)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleEventObserver)
        }
    }
    val strings = LocalStrings.current
    val open = rememberSaveable {
        mutableStateOf(true)
    }
    Column(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .border(
                    width = 1.dp,
                    color = Color(0xFFD1D1D4),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp)
        ) {
            if (open.value) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = strings.totalProducts,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400),
                            color = Color(0xFF95959B)
                        )

                        Text(
                            text = "${price.total.toFixed()} TMT",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W600),
                            color = newTextColor
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${strings.orderDiscount} (-${price.discountPercentage.toFixed()}%):",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400),
                            color = Color(0xFF95959B)
                        )

                        Text(
                            text = "${price.discountPrice.toFixed()} TMT",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W600),
                            color = newTextColor
                        )
                    }
//                    Spacer(modifier = Modifier.height(8.dp))
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Text(
//                            text = strings.orderDelivery,
//                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400),
//                            color = Color(0xFF95959B)
//                        )
//
//                        Text(
//                            text = "${price.deliveryPrice.toFixed()} TMT",
//                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W600),
//                            color = newTextColor
//                        )
//                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    DashedDivider(
                        thickness = 1.2.dp,
                        modifier = Modifier.fillMaxWidth(),
                        color = Color(0xFFD4D4D8)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = strings.totalPrice,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400),
                            color = Color(0xFF95959B)
                        )

                        Text(
                            text = "${price.completePrice.toFixed()}TMT",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W700),
                            color = newTextColor
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }


//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//            Column(modifier = Modifier.weight(1f)) {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.spacedBy(12.dp)
//                ) {
//                    Text(
//                        text = "${strings.total} (${price.count} ${strings.product})",
//                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W700),
//                        color = Color(0xFF71717A)
//                    )
//
//                    Icon(
//                        if (open.value) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
//                        contentDescription = null,
//                        tint = if (open.value) Color(0xFF55A73B) else Color(0xFFA1A1AA)
//                    )
//                }
//                Text(
//                    text = "${price.completePrice.toFixed()}TMT",
//                    style = MaterialTheme.typography.bodyLarge.copy(
//                        fontWeight = FontWeight.W700,
//                        fontSize = 18.sp
//                    ),
//                    color = Color(0xFF326625)
//                )
//            }
//
//        }


        }

        Button(
            onClick = { onClick() },
            enabled = price.count > 0,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(6.dp)
        ) {
            Text(
                text = strings.makeOrder,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                color = Color.White
            )
        }
    }
}