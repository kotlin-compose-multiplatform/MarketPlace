package com.komekci.marketplace.features.profile.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.features.payment.presentation.ui.SelectSheet
import com.komekci.marketplace.features.profile.presentation.viewmodel.ProfileViewModel
import com.komekci.marketplace.state.LocalGuestId
import com.komekci.marketplace.ui.app.AppError
import com.komekci.marketplace.ui.app.AppLoading
import com.komekci.marketplace.ui.app.ImageLoader
import com.komekci.marketplace.ui.app.NoData
import com.komekci.marketplace.ui.navigation.Routes

enum class OrderType {
    STORE,
    USER
}

@Preview(showSystemUi = true)
@Composable
fun OrdersScreen(
    navHostController: NavHostController = rememberNavController(),
    userViewModel: ProfileViewModel = hiltViewModel(),
    type: OrderType = OrderType.USER
) {
    val strings = LocalStrings.current
    val storeOrderRequest = userViewModel.storeOrderRequest

    val localUserState = userViewModel.profile.collectAsState()

    val isLogout = remember {
        mutableStateOf(localUserState.value == null || localUserState.value?.token.isNullOrEmpty())
    }

    val guestId = LocalGuestId.current

    LaunchedEffect(storeOrderRequest.value, guestId.value, isLogout.value) {
        userViewModel.getOrders(type, guestId.value?:"", isLogout.value)
    }
    val state = userViewModel.orders.collectAsState()

    val openType = remember {
        mutableStateOf(false)
    }



    SelectSheet(
        title = strings.date,
        open = openType.value,
        items = listOf("Gunlik", "Hepdelik", "Aylyk"),
        onSelected = { index->
            val date = when(index) {
                0 -> "onedayago"
                1 -> "oneweekago"
                2 -> "onemonthago"
                else -> ""
            }
            userViewModel.setStoreOrderRequest(storeOrderRequest.value.copy(
                date = date
            ))
        }
    ) {
        openType.value = false
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null,
                tint = Color(0xFF1A1A1A),
                modifier = Modifier
                    .size(34.dp)
                    .align(Alignment.TopStart)
                    .clip(CircleShape)
                    .clickable { navHostController.navigateUp() }
            )

            Text(
                text = strings.myOrders, style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.W700
                ), textAlign = TextAlign.Center,
                color = Color(0xFF1A1A1A),
                modifier = Modifier.align(Alignment.Center),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

        }



        if (state.value.loading) {
            AppLoading(modifier = Modifier.fillMaxSize())
        } else if (state.value.error.isNullOrEmpty().not()) {
            AppError(modifier = Modifier.fillMaxSize()) {
                userViewModel.getOrders(type, guestId.value?:"", isLogout.value)
            }
        } else if (state.value.isEmpty) {
            NoData(modifier = Modifier.fillMaxSize())
        } else {
            state.value.data?.let { list ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if(type == OrderType.STORE) {
                        item {
                            Row(Modifier.fillMaxWidth()) {
                                Row(modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color.White, RoundedCornerShape(4.dp))
                                    .clickable { openType.value = true }
                                    .padding(6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = strings.date,
                                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                                        color = Color(0xFF1C2024)
                                    )
                                    Icon(
                                        Icons.Default.ArrowDropDown,
                                        contentDescription = null,
                                        tint = Color(0xFF1A1A1A)
                                    )
                                }
                            }
                        }
                    }
                    items(list.size) { index ->
                        val item = list[index]
                        OrderItem(
                            address = "${item?.address?.address}",
                            date = item.createdAt,//"21 ноября в 17:40",
                            status = item.status,
                            images = item.orderItems.map {
                                try {
                                    it.images[0].image
                                } catch (_: Exception) {
                                    ""
                                }
                            },
                            count = item.orderItems.size
                        ) {
                            userViewModel.selectOrder(item, type, guestId.value?:"")
                            navHostController.navigate(Routes.Order)
                        }
                    }
                }
            }
        }


    }
}

@Composable
fun OrderItem(
    address: String,
    date: String,
    status: String,
    images: List<String> = emptyList(),
    count: Int = 0,
    onClick: () -> Unit
) {
    val isPending = status == "waiting"
    val isCanceled = status == "cancelled"

    val strings = LocalStrings.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (isPending)
                    Color(0xFFF2F2F5)
                else if(isCanceled) Color(0xffffbed7)
                else Color(0xFFF3FCF3),
                RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                onClick()
            }
            .padding(horizontal = 24.dp, vertical = 18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = address,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W700),
                color = Color(0xFF18181B),
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        color = if (isPending) Color(0xFFF2F4F7) else if(isCanceled) Color(0xffffbed7)  else Color(0xFFECFDF3),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isPending) strings.pendingOrder else if(isCanceled) strings.canceledOrder else strings.deliveryOrder,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W500),
                    color = if (isPending) Color(0xFF344054) else Color(0xFF027A48)
                )
            }
        }

        Text(
            text = "${strings.orderDate} $date",
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W400),
            color = Color(0xFF18181B),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = strings.seeOrder,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                color = Color(0xFF2563EB)
            )


            Box(modifier = Modifier) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(x = 18.dp)
                        .background(
                            color = Color(0xCCD9D9D9),
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${count}+",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W700),
                        color = Color(0xFF333333)
                    )
                }
                ImageLoader(
                    url = try {
                        images[0]
                    } catch (_: Exception) {""}, modifier = Modifier
                        .height(42.dp)
                        .width(30.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .offset(x = -(6).dp)
                )

            }
        }

    }
}