package com.komekci.marketplace.features.profile.presentation.ui

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.core.utils.DateHelper
import com.komekci.marketplace.core.utils.toFixed
import com.komekci.marketplace.features.profile.presentation.viewmodel.ProfileViewModel
import com.komekci.marketplace.ui.app.AppError
import com.komekci.marketplace.ui.app.AppLoading
import com.komekci.marketplace.ui.app.DashedDivider
import com.komekci.marketplace.ui.app.NoData
import com.komekci.marketplace.ui.navigation.Routes


@Composable
fun OrderScreen(
    navHostController: NavHostController = rememberNavController(),
    userViewModel: ProfileViewModel = hiltViewModel(),
    type: OrderType = OrderType.USER
) {
    val strings = LocalStrings.current
    fun back() {
        userViewModel.selectOrder(null, type, "")
        navHostController.navigateUp()
    }
    val singleOrder = userViewModel.singleOrder.collectAsState()
    val updateOrderState = userViewModel.updateOrderState



    BackHandler {
        back()
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
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
                    .clickable { back() }
            )

            Text(
                text = strings.order, style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.W700
                ), textAlign = TextAlign.Center,
                color = Color(0xFF1A1A1A),
                modifier = Modifier.align(Alignment.Center),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

        }

        if(singleOrder.value.loading) {
            AppLoading(modifier = Modifier.fillMaxSize())
        } else if(singleOrder.value.error.isNullOrEmpty().not()) {
            AppError(modifier = Modifier.fillMaxSize()) {
            }
        } else if(singleOrder.value.data!=null) {
            singleOrder.value.data?.let { item->
                fun getOrderStatusLabel(status: String): String {
                    return when (status) {
                        "completed" -> {
                            strings.deliveryOrder
                        }
                        "waiting" -> {
                            strings.pendingOrder
                        }
                        else -> {
                            strings.canceledOrder
                        }
                    }
                }

                val orderStatus = rememberSaveable {
                    mutableStateOf(getOrderStatusLabel(item.status))
                }
                fun updateStatus(status: String) {
                    userViewModel.updateOrderStatus(status, item.id.toString(), onSuccess = {
                        orderStatus.value = getOrderStatusLabel(status)
                    })
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color(0xFFF9F9FB))
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "${strings.orderNumber} #${item.id}",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.W700,
                            fontSize = 18.sp
                        ),
                        color = Color(0xFF71717A),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(8.dp),
                                )
                                .padding(12.dp)
                        ) {
                            Text(
                                text = strings.orderStatus,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                                color = Color(0xFF6D6D6D)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            if(type == OrderType.USER) {
                                Text(
                                    text = orderStatus.value,
                                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                                    color = Color(0xFF1A1A1A)
                                )
                            } else {
                                val expanded = remember {
                                    mutableStateOf(false)
                                }
                                if(updateOrderState.value.loading) {
                                    LinearProgressIndicator()
                                } else {
                                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
                                        expanded.value = true
                                    }) {
                                        Text(
                                            text = orderStatus.value,
                                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                                            color = Color(0xFF1A1A1A)
                                        )
                                        Spacer(Modifier.width(4.dp))
                                        Icon(
                                            Icons.Default.ArrowDropDown,
                                            contentDescription = null,
                                            tint = Color(0xFF1A1A1A)
                                        )
                                    }
                                    DropdownMenu(
                                        expanded = expanded.value,
                                        onDismissRequest = {
                                            expanded.value = false
                                        }
                                    ) {
                                        DropdownMenuItem(
                                            text = {
                                                Text(strings.pendingOrder)
                                            },
                                            onClick = {
                                                updateStatus("waiting")
                                                expanded.value = false
                                            }
                                        )
                                        DropdownMenuItem(
                                            text = {
                                                Text(strings.cancel)
                                            },
                                            onClick = {
                                                updateStatus("cancelled")
                                                expanded.value = false
                                            }
                                        )

                                        DropdownMenuItem(
                                            text = {
                                                Text(strings.deliveryOrder)
                                            },
                                            onClick = {
                                                updateStatus("completed")
                                                expanded.value = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                        Column(
                            modifier = Modifier
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(8.dp),
                                )
                                .weight(1f)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = strings.date,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                                color = Color(0xFF6D6D6D)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = try {
                                    DateHelper.convertDateAndTime(item.createdAt?:"")
                                } catch (ex: Exception) {""},
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                                color = Color(0xFF1A1A1A)
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp
                            )
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(8.dp),
                            )
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                navHostController.navigate(Routes.OrderProducts)
                            }
                            .padding(12.dp)
                    ) {
                        Text(
                            text = strings.orderProducts,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W600),
                            color = Color(0xFF333333)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${strings.orderTotalProducts} ${item.orderItems.size} ${strings.product}",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W600),
                            color = Color(0xFF71717A)
                        )
                    }

                    Column(
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp,
                                vertical = 12.dp
                            )
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(8.dp),
                            )
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {


                        Text(
                            text = strings.orderPaymentType,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                            color = Color(0xFF6D6D6D)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if(item.paymentMethod=="card") strings.card else strings.cash,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                            color = Color(0xFF1A1A1A)
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp
                            )
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(8.dp),
                            )
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {


                        Text(
                            text = "${strings.address}:",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                            color = Color(0xFF6D6D6D)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item?.address?.address?:"",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                            color = Color(0xFF1A1A1A)
                        )
                    }

                    Column(
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp,
                                vertical = 12.dp
                            )
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(8.dp),
                            )
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {


                        Text(
                            text = strings.orderMessage,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                            color = Color(0xFF6D6D6D)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.description?:"",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                            color = Color(0xFF1A1A1A)
                        )
                    }

                    Column(
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp,
                                vertical = 12.dp
                            )
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(8.dp),
                            )
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Text(
                            text = strings.orderTotal,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.W700,
                                fontSize = 18.sp
                            ),
                            color = Color(0xFF161616)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = strings.totalProducts,
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                                color = Color(0xFF333333)
                            )

                            Text(
                                text = try {
                                    "${item.discount.price?.toDouble()?.plus(item.totalPrice?:0.0)?.toFixed()?:item.totalPrice?.toFixed()} TMT"
                                } catch (_: Exception) {
                                    "0 TMT"
                                },
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                                color = Color(0xFF60646C)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = try {
                                    "${strings.orderDiscount} (-${item.discount.percent}%):"
                                } catch (_: Exception) {
                                    "0%"
                                },
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                                color = Color(0xFFC62A2F)
                            )

                            Text(
                                text = try {
                                    "${item.discount.price} TMT"
                                } catch (_: Exception) {
                                    "0 TMT"
                                },
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                                color = Color(0xFFC62A2F)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = strings.orderDelivery,
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                                color = Color(0xFF333333)
                            )

                            Text(
                                text = "0 TMT",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                                color = Color(0xFF60646C)
                            )
                        }
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
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                                color = Color(0xFF333333)
                            )

                            Text(
                                text = "${item.totalPrice}TMT",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                                color = Color(0xFF60646C)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        val context = LocalContext.current
                       item.storePhonenumber?.let { phone->
                           Button(
                               onClick = {
                                   val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
                                   context.startActivity(intent)
                               }, shape = RoundedCornerShape(8.dp), modifier = Modifier
                                   .fillMaxWidth()
                                   .height(50.dp), colors = ButtonDefaults.buttonColors(
                                   containerColor = Color(0xFF3D9A50)
                               )
                           ) {
                               Text(
                                   text = strings.call,
                                   style = MaterialTheme.typography.bodyLarge.copy(
                                       fontWeight = FontWeight.W500,
                                       fontSize = 18.sp
                                   ),
                                   color = Color.White
                               )
                           }
                       }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

        } else {
            NoData(modifier = Modifier.fillMaxSize())
        }
    }
}