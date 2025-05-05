package com.komekci.marketplace.features.profile.presentation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.features.auth.presentation.ui.ErrorField
import com.komekci.marketplace.features.profile.presentation.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddressDialog(
    open: Boolean = true,
    title: String,
    oldText: String = "",
    oldAddress: String = "",
    addressId: String = "-1",
    viewModel: ProfileViewModel = hiltViewModel(),
    onClose: () -> Unit = {}
) {
    val strings = LocalStrings.current
    val text = remember {
        mutableStateOf(oldText)
    }
    val address = remember {
        mutableStateOf(oldAddress)
    }
    val sheetState = rememberModalBottomSheetState()
    if (open) {
        val state = remember {
            viewModel.createAddressState
        }
        val isVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0

        LaunchedEffect(key1 = isVisible) {
            if (isVisible) {
                sheetState.expand()
            } else {
                sheetState.partialExpand()
            }
        }
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = onClose,
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
            dragHandle = {},
            windowInsets = WindowInsets.ime,
            containerColor = Color.White,

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        color = Color(0xFF2F313F),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 20.sp, fontWeight = FontWeight.W700
                        ),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier.weight(1f)
                    )
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .border(1.5.dp, Color(0xFFF2F2F5), CircleShape)
                            .clickable { onClose() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Clear, contentDescription = null, tint = Color(0xFF8B8D98)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
                state.value.error?.let {
                    ErrorField(
                        errorCode = 500
                    )
                }
                state.value.message?.let {
                    if(it.isNotEmpty()) {
                        ErrorField(
                            message = state.value.message
                        )
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = strings.addressName,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                    color = Color(0xFF1C2024)
                )
                OutlinedTextField(
                    value = text.value,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0x1C000030),
                        focusedBorderColor = Color(0x1C000030),
                        unfocusedTextColor = Color(0x7500041D),
                        focusedTextColor = Color(0xFF00041D),
                    ),
                    onValueChange = {
                        text.value = it
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
                    value = address.value,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0x1C000030),
                        focusedBorderColor = Color(0x1C000030),
                        unfocusedTextColor = Color(0x7500041D),
                        focusedTextColor = Color(0xFF00041D),
                    ),
                    onValueChange = {
                        address.value = it
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
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {


                    Button(
                        onClick = {
                            text.value = ""
                            address.value = ""
                            onClose()
                        },
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0x0D05C005)
                        ),
                        border = BorderStroke(1.dp, Color(0x99008D1A))
                    ) {
                        Text(
                            text = strings.cancel,
                            color = Color(0xD6006316),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W500
                            )
                        )
                    }

                    Button(
                        onClick = {
                            if(addressId!="-1") {
                                viewModel.updateAddress(
                                    id = addressId,
                                    title = text.value,
                                    address = address.value
                                ) {
                                    text.value = ""
                                    address.value = ""
                                    onClose()
                                }
                            } else {
                                viewModel.addAddress(
                                    title = text.value,
                                    address = address.value
                                ) {
                                    text.value = ""
                                    address.value = ""
                                    onClose()
                                }
                            }
                        },
                        enabled = text.value.isNotEmpty() && address.value.isNotEmpty(),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        if(state.value.loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(35.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                        } else {
                            Text(
                                text = strings.accept,
                                color = Color.White,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.W500
                                )
                            )
                        }
                    }
                }
                Spacer(Modifier.height(30.dp).navigationBarsPadding())
            }
        }
    }
}