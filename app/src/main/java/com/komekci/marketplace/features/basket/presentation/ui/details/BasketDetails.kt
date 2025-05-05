package com.komekci.marketplace.features.basket.presentation.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.R
import com.komekci.marketplace.core.utils.toFixed
import com.komekci.marketplace.features.auth.presentation.ui.ErrorField
import com.komekci.marketplace.features.basket.presentation.ui.PaymentConfirmation
import com.komekci.marketplace.features.basket.presentation.viewmodel.BasketViewModel
import com.komekci.marketplace.features.profile.presentation.ui.AddressDialog
import com.komekci.marketplace.features.profile.presentation.viewmodel.ProfileViewModel
import com.komekci.marketplace.state.LocalGuestId
import com.komekci.marketplace.state.LocalRouteState
import com.komekci.marketplace.ui.app.AppLoading
import com.komekci.marketplace.ui.app.CheckAuthScreen
import com.komekci.marketplace.ui.app.DashedDivider
import com.komekci.marketplace.ui.app.LoadingButton
import com.komekci.marketplace.ui.app.Select
import com.komekci.marketplace.ui.navigation.Routes

@Preview(showSystemUi = true)
@Composable
fun BasketDetails(
    navHostController: NavHostController = rememberNavController(),
    shopId: Int = 0
) {
    val strings = LocalStrings.current
    val viewModel: BasketViewModel = hiltViewModel()
    val addressExpanded = remember {
        mutableStateOf(false)
    }
    val selectedBank = rememberSaveable {
        mutableIntStateOf(0)
    }
    val description = remember {
        mutableStateOf("")
    }
    val openAddressDialog = remember {
        mutableStateOf(false)
    }
    val openPayment = remember {
        mutableStateOf(false)
    }
    val openSuccess = remember {
        mutableStateOf(false)
    }

    val addressViewModel: ProfileViewModel = hiltViewModel()
    val addressState = addressViewModel.address.collectAsState()

    LaunchedEffect(true) {
        println("BASKET SHOP-1: $shopId")
        addressViewModel.getAddress()
    }

    val paymentType = rememberSaveable {
        mutableStateOf("card")
    }

    val selectedAddress = rememberSaveable {
        mutableStateOf("")
    }

    val price = remember {
        viewModel.basketPrice
    }

    val bankExpanded = rememberSaveable {
        mutableStateOf(false)
    }

    val globalRoute = LocalRouteState.current
    val selectedStoreId = viewModel.shopId
    LaunchedEffect(globalRoute.value.homeRoute, shopId) {
        viewModel.setShopId(shopId)
        viewModel.getBasketProducts(shopId)
    }

    val sendState = remember {
        viewModel.sendState
    }

    val address = remember {
        mutableStateOf(false)
    }

    AddressDialog(
        open = openAddressDialog.value,
        title = strings.addAddress,
        viewModel = addressViewModel
    ) {
        openAddressDialog.value = false
    }
    BasketCompleteDialog(
        open = openSuccess.value,
        onOrdersClick = {
            navHostController.navigateUp()
            globalRoute.value = globalRoute.value.copy(
                homeRoute = Routes.Profile,
                profileRoute = Routes.Payments
            )
        }
    ) {
        openSuccess.value = false
    }

    val isAuth = remember {
        mutableStateOf(false)
    }

    val guestId = LocalGuestId.current



   sendState.value.data?.let { send->
       if(paymentType.value == "card") {
           PaymentConfirmation(
               open = openPayment.value,
               onClose = {
                   openPayment.value = false
               },
               url = send.url,
               finalUrl = send.finalUrl,
               onSuccess = {
                   openPayment.value = false
                   openSuccess.value = true
                   viewModel.deleteAll()
               }
           )
       }
   }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
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
                text = strings.orderPayment, style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.W600
                ), textAlign = TextAlign.Center,
                color = Color(0xFF1A1A1A),
                modifier = Modifier.align(Alignment.Center),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = strings.yourAddress,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                color = Color(0xFF333333)
            )
            CheckAuthScreen(
                errorContent = {
                    isAuth.value = false
                },
                successContent = {
                    isAuth.value = true
                    Text(
                        text = strings.addAddress,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF3D9A50),
                        modifier = Modifier.clickable { openAddressDialog.value = true }
                    )
                }
            )
        }


        val addressName = rememberSaveable() {
            mutableStateOf("")
        }

        val addressValue = rememberSaveable() {
            mutableStateOf("")
        }

        val phone = rememberSaveable() {
            mutableStateOf("")
        }

       CheckAuthScreen(
           errorContent = {
               Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                   Spacer(modifier = Modifier.height(6.dp))
                   Text(
                       text = strings.addressName,
                       style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                       color = Color(0xFF1C2024)
                   )
                   OutlinedTextField(
                       value = addressName.value,
                       colors = OutlinedTextFieldDefaults.colors(
                           unfocusedBorderColor = Color(0x1C000030),
                           focusedBorderColor = Color(0x1C000030),
                           unfocusedTextColor = Color(0x7500041D),
                           focusedTextColor = Color(0xFF00041D),
                       ),
                       onValueChange = {
                           addressName.value = it
                       },
                       singleLine = true,
                       keyboardOptions = KeyboardOptions(
                           imeAction = ImeAction.Next
                       ),
                       placeholder = {
                           Text(text = "...")
                       },
                       modifier = Modifier
                           .fillMaxWidth(),
                       textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
                   )
                   Spacer(modifier = Modifier.height(6.dp))
                   Text(
                       text = strings.address,
                       style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                       color = Color(0xFF1C2024)
                   )
                   OutlinedTextField(
                       value = addressValue.value,
                       colors = OutlinedTextFieldDefaults.colors(
                           unfocusedBorderColor = Color(0x1C000030),
                           focusedBorderColor = Color(0x1C000030),
                           unfocusedTextColor = Color(0x7500041D),
                           focusedTextColor = Color(0xFF00041D),
                       ),
                       onValueChange = {
                           addressValue.value = it
                       },
                       singleLine = true,
                       keyboardOptions = KeyboardOptions(
                           keyboardType = KeyboardType.Text
                       ),
                       placeholder = {
                           Text(text = "...")
                       },
                       minLines = 2,
                       modifier = Modifier
                           .fillMaxWidth()
                           .wrapContentHeight(),
                       textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
                   )
                   Spacer(modifier = Modifier.height(6.dp))
                   Text(
                       text = strings.phone,
                       style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                       color = Color(0xFF1C2024)
                   )
                   OutlinedTextField(
                       value = phone.value,
                       colors = OutlinedTextFieldDefaults.colors(
                           unfocusedBorderColor = Color(0x1C000030),
                           focusedBorderColor = Color(0x1C000030),
                           unfocusedTextColor = Color(0x7500041D),
                           focusedTextColor = Color(0xFF00041D),
                       ),
                       onValueChange = {
                           phone.value = it
                       },
                       singleLine = true,
                       keyboardOptions = KeyboardOptions(
                           keyboardType = KeyboardType.Text
                       ),
                       placeholder = {
                           Text(text = "+00 00 00 00")
                       },
                       minLines = 2,
                       modifier = Modifier
                           .fillMaxWidth()
                           .wrapContentHeight(),
                       textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
                   )
                   Spacer(modifier = Modifier.height(10.dp))
               }
           },
           successContent = {
               if (addressState.value.loading) {
                   AppLoading(modifier = Modifier.fillMaxWidth())
               } else {
                   val placeholder = addressState.value.data?.let {
                       if (it.isNotEmpty()) {
                           it[0].address
                       } else {
                           strings.myAddress
                       }
                   }
                   Select(
                       modifier = Modifier.padding(16.dp),
                       expanded = addressExpanded.value,
                       placeholder = placeholder ?: "",
                       items = addressState.value.data?.map { it.address ?: "" } ?: emptyList(),
                       onSelected = { index ->
                           addressState.value.data?.let {
                               if (it.isNotEmpty()) {
                                   selectedAddress.value = it[index].id.toString()
                               }
                           }
                       },
                       isError = address.value
                   ) {
                       addressExpanded.value = it
                   }

               }
           }
       )

//        Select(
//            modifier = Modifier.padding(horizontal = 16.dp),
//            expanded = regionExpanded.value,
//            placeholder = strings.region,
//            items = listOf("Ashgabat", "Dashoguz", "Balkan", "Lebap", "Mary"),
//            onSelected = {
//
//            }
//        ) {
//            regionExpanded.value = it
//        }

        Text(
            text = strings.paymentType,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
            color = Color(0xFF333333),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PaymentType(
                title = strings.card,
                icon = R.drawable.credit_card,
                selected = paymentType.value == "card",
                modifier = Modifier.weight(1f),
                value = "card"
            ) {
                paymentType.value = it
            }
            PaymentType(
                title = strings.cash,
                icon = R.drawable.cash,
                selected = paymentType.value == "cash",
                modifier = Modifier.weight(1f),
                value = "cash"
            ) {
                paymentType.value = it
            }
        }

        if(paymentType.value == "card") {
            Text(
                text = strings.selectBank,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                color = Color(0xFF333333),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp)
            )


            Select(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp),
                expanded = bankExpanded.value,
                placeholder = strings.selectBank,
                items = listOf("Halk bank","Senagat Bank", "Rysgal Bank"),
                selected = selectedBank.intValue,
                onSelected = { index->
                    selectedBank.intValue = index
                }
            ) {
                bankExpanded.value = it
            }
        }



        Text(
            text = strings.notes,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
            color = Color(0xFF333333),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
        )

        OutlinedTextField(
            value = description.value,
            onValueChange = { description.value = it },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFD4D4D8),
                focusedBorderColor = Color(0xFF297C3B),
                unfocusedTextColor = Color(0xFFB9BBC6),
                focusedTextColor = Color(0xFFB9BBC6)
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.outline_edit_note_24),
                    contentDescription = null
                )
            },
            placeholder = {
                Text(
                    text = strings.additionalInfo,
                    color = Color(0xFFB9BBC6)
                )
            },
            minLines = 3,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 7.dp),
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.W400,
                color = Color(0xFF333333)
            )
        )

        Column(
            modifier = Modifier
                .border(
                    1.dp,
                    Color(0xFFE4E4E7),
                    RoundedCornerShape(8.dp)
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                )
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = strings.orderTotal,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.W500,
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
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400),
                    color = Color(0xFF333333)
                )

                Text(
                    text = "${price.value.completePrice.toFixed()} TMT",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400),
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
                    text = "${strings.orderDiscount} (-${price.value.discountPercentage.toFixed()}%):",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400),
                    color = Color(0xFF333333)
                )

                Text(
                    text = "${price.value.discountPrice.toFixed()} TMT",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400),
                    color = Color(0xFF60646C)
                )
            }
//            Spacer(modifier = Modifier.height(8.dp))
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text(
//                    text = strings.orderDelivery,
//                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400),
//                    color = Color(0xFF333333)
//                )
//
//                Text(
//                    text = "0 TMT",
//                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400),
//                    color = Color(0xFF60646C)
//                )
//            }
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
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W700),
                    color = Color(0xFF333333)
                )

                Text(
                    text = "${price.value.total.toFixed()}TMT",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.W500,
                        fontSize = 20.sp
                    ),
                    color = Color(0xFF297C3B)
                )
            }


        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(Modifier.padding(horizontal = 16.dp)) {
            LoadingButton(
                loading = sendState.value.loading,
                onClick = {
                    if ((selectedAddress.value.isEmpty() || selectedAddress.value == "0") && isAuth.value) {
                        address.value = true
                    } else {
                        address.value = false
                        viewModel.sendBasket(
                            addressId = selectedAddress.value,
                            paymentType = paymentType.value,
                            bank = selectedBank.intValue,
                            addressName = addressName.value,
                            addressValue = addressValue.value,
                            addressPhone = phone.value,
                            isAuth = isAuth.value,
                            guestId = guestId.value?:""
                        ) {
                            if(paymentType.value == "card") {
                                openPayment.value = true
                            } else {
                                openSuccess.value = true
                            }
                        }
                    }

                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3D9A50)
                )
            ) {
                Text(
                    text = strings.sendOrder,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.W500,
                        fontSize = 18.sp
                    ),
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

    }
}

@Composable
fun PaymentType(
    modifier: Modifier = Modifier,
    title: String,
    icon: Int,
    selected: Boolean,
    value: String,
    onClick: (String) -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = if (selected) Color(0xFF297C3B) else Color(0xFFD4D4D8),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick(value) }
            .padding(8.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = Color(0xFF0F172A)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400),
            color = Color(0xFF333333)
        )
    }
}