package com.komekci.marketplace.features.create_store.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.features.basket.presentation.ui.PaymentConfirmation
import com.komekci.marketplace.features.create_store.data.entity.store.CreateStoreBody
import com.komekci.marketplace.features.create_store.presentation.viewmodel.StoreViewModel
import com.komekci.marketplace.features.home.presentation.viewmodel.LocationViewModel
import com.komekci.marketplace.ui.app.LoadingButton
import com.komekci.marketplace.ui.theme.newTextColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, device = "id:pixel_9")
@Composable
fun OnlinePayment(
    storeViewModel: StoreViewModel = hiltViewModel(),
    navHostController: NavHostController = rememberNavController(),
    profileNavHostController: NavHostController = rememberNavController(),
    type: String = "NEW"
) {
    val strings = LocalStrings.current
    val locationViewModel: LocationViewModel = hiltViewModel()
    val country = locationViewModel.state.collectAsState()

    LaunchedEffect(true) {
        locationViewModel.initLocations()
    }
    val selectedCountry = rememberSaveable {
        mutableStateOf("0")
    }
    val selectedMonth = rememberSaveable {
        mutableIntStateOf(1)
    }
    val selectedBank = rememberSaveable {
        mutableIntStateOf(0)
    }

    val openPayment = remember {
        mutableStateOf(false)
    }

    val state = remember {
        storeViewModel.createStoreStateOnline
    }

    val coroutineScope = rememberCoroutineScope()

    state.value.data?.let { send->
        send.url?.let {
            PaymentConfirmation(
                open = openPayment.value,
                onClose = {
                    openPayment.value = false
                },
                url = send.url,
                finalUrl = send.finalUrl!!,
                onSuccess = {
                    openPayment.value = false
                    if(type == "NEW") {
                        storeViewModel.getStoreToken(send.transactionId!!, onSuccess = {
                            profileNavHostController.navigateUp()
                        })
                    } else {
                        profileNavHostController.navigateUp()
                    }

                }
            )
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(
                color = Color.White
            ), verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CenterAlignedTopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = {
                        navHostController.navigateUp()
                    }
                ) {
                    Icon(
                        Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "back",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            title = {
                Text(
                    text = "Online",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W600
                    ),
                    color = Color(0xFF0F1E3C)
                )
            }
        )

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = 16.dp
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            country.value.locations?.let { locations ->
                Text(
                    strings.whichCountryCard,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 15.sp,
                    ),
                    modifier = Modifier.fillMaxWidth(0.7f),
                    textAlign = TextAlign.Center,
                    color = newTextColor
                )

                CupertinoSelection(
                    values = locations.map {
                        it.name_tm
                    }
                ) { index ->
                    selectedCountry.value = locations[index].id
                }
            }

            Text(
                strings.selectBank,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 15.sp,
                ),
                modifier = Modifier.fillMaxWidth(0.7f),
                textAlign = TextAlign.Center,
                color = newTextColor
            )

            CupertinoSelection(
                values = listOf(
                    "Halkbank", "Senagat bank", "Rysgal bank"
                )
            ) { bank ->
                selectedBank.intValue = bank
            }

            Text(
                text = strings.selectBox,
                color = Color(0xFF3C3C43).copy(alpha = 0.60f),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            )

            CupertinoSelection(
                values = listOf(
                    "1 aý / 30 tmt",
                    "2 aý / 90 tmt",
                    "3 aý / 180 tmt"
                )
            ) { month ->
                selectedMonth.intValue = month.plus(1)
            }

            LoadingButton(
                loading = state.value.loading,
                onClick = {
                    storeViewModel.createStoreOnline(
                        type = type,
                        CreateStoreBody(
                            paymentMethod = "card",
                            bank = selectedBank.intValue,
                            countryId = selectedCountry.value.toInt(),
                            month = selectedMonth.intValue
                        ),
                        onSuccess = {
                            openPayment.value = true
                        }
                    )
                },
                enabled = selectedCountry.value != "0" && selectedBank.intValue >= 0,
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = strings.accept,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500
                    )
                )
            }
            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.ime))
        }
    }
}

@Composable
fun CupertinoSelection(
    modifier: Modifier = Modifier,
    values: List<String>,
    onSelect: (Int) -> Unit
) {
    val selected = rememberSaveable {
        mutableIntStateOf(0)
    }
    Column(
        modifier
            .background(
                color = Color(0xFF767680).copy(alpha = 0.12f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        repeat(values.count()) { index ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable {
                        onSelect(index)
                        selected.intValue = index
                    }
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = "check",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.alpha(if (selected.intValue == index) 1f else 0f)
                )

                Text(
                    text = values[index],
                    color = newTextColor,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}