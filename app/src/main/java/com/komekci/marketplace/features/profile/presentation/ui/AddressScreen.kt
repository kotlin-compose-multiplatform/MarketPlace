package com.komekci.marketplace.features.profile.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.R
import com.komekci.marketplace.features.profile.data.entity.AddressResponse
import com.komekci.marketplace.features.profile.presentation.viewmodel.ProfileViewModel
import com.komekci.marketplace.ui.app.AppError
import com.komekci.marketplace.ui.app.AppLoading
import com.komekci.marketplace.ui.app.NoData
import com.komekci.marketplace.ui.theme.errorColor

@Preview(showSystemUi = true)
@Composable
fun AddressScreen(
    navHostController: NavHostController = rememberNavController()
) {
    val viewModel: ProfileViewModel = hiltViewModel()
    val strings = LocalStrings.current

    val openAddressDialog = remember {
        mutableStateOf(false)
    }
    val openEditDialog = remember {
        mutableStateOf(false)
    }
    val state = viewModel.address.collectAsState()
    val selectedAddress = remember {
        mutableStateOf<AddressResponse?>(null)
    }
    key(selectedAddress.value){
        selectedAddress.value?.let { item->
            AddressDialog(
                addressId = item.id.toString(),
                oldAddress = item.address?:"",
                oldText = item.title?:"",
                open = openEditDialog.value,
                title = strings.editAddress,
                viewModel = viewModel
            ) {
                openEditDialog.value = false
            }
        }
    }
    AddressDialog(
        open = openAddressDialog.value,
        title = strings.addAddress
    ) {
        openAddressDialog.value = false
    }

    LaunchedEffect(true) {
        viewModel.initAddress()
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
                text = strings.myAddress, style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.W700
                ), textAlign = TextAlign.Center,
                color = Color(0xFF1A1A1A),
                modifier = Modifier.align(Alignment.Center),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (state.value.loading) {
                item {
                    AppLoading(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    )
                }
            } else if (state.value.error.isNullOrEmpty().not()) {
                item {
                    AppError(modifier = Modifier.fillMaxWidth()) {
                        viewModel.getAddress()
                    }
                }
            } else if (state.value.isEmpty) {

            } else {
                state.value.data?.let { list->
                    items(list.size) {
                        val selected = remember {
                            mutableStateOf(false)
                        }
                        val item = list[it]
                        AddressItem(
                            name = item.title?:"",
                            address = item.address?:"",
                            selected = selected.value,
                            onEditClick = {
                                selectedAddress.value = item
                                openEditDialog.value = true
                            },
                            onDeleteClick = {
                                viewModel.deleteAddress(item.id.toString()) {

                                }
                            }
                        ) {
                            selected.value = selected.value.not()
                        }
                    }
                }
            }

            item {
                Button(
                    onClick = { openAddressDialog.value = true },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEBF9EB)
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .height(56.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFF297C3B))
                    Spacer(modifier = Modifier.width(18.dp))
                    Text(
                        text = strings.addNewAddress,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W600),
                        color = Color(0xFF297C3B)
                    )
                }
            }
        }
    }
}

@Composable
fun AddressItem(
    name: String,
    address: String,
    selected: Boolean = false,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(if (selected) 0xFFFFFFFF else 0xFFF6F6F6), RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = if (selected) 1.dp else 0.dp,
                color = if (selected) Color(0xFF297C3B) else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.location),
            contentDescription = null,
            tint = Color(0xFF3D3D3D),
            modifier = Modifier.size(30.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W600),
                color = if (selected) Color(0xFF297C3B) else Color(0xFF3D3D3D),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = address,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400),
                color = if (selected) Color(0xFF297C3B) else Color(0xFF4F4F4F),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }

        IconButton(onClick = onEditClick) {
            Icon(
                painter = painterResource(id = R.drawable.edit_icon),
                contentDescription = null,
                tint = if (selected) Color(0xFF297C3B) else Color(0xFF4F4F4F)
            )
        }
        IconButton(onClick = onDeleteClick) {
            Icon(
                painter = painterResource(id = R.drawable.outline_delete_24),
                contentDescription = null,
                tint = errorColor
            )
        }

    }
}