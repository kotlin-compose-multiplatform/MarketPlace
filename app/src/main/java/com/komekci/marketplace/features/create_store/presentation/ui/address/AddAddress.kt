package com.komekci.marketplace.features.create_store.presentation.ui.address

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.core.locale.translateValue
import com.komekci.marketplace.features.auth.presentation.ui.ErrorField
import com.komekci.marketplace.features.create_store.presentation.viewmodel.StoreViewModel
import com.komekci.marketplace.features.home.presentation.viewmodel.LocationViewModel
import com.komekci.marketplace.ui.app.AppCheckBox
import com.komekci.marketplace.ui.app.AppError
import com.komekci.marketplace.ui.app.AppLoading
import com.komekci.marketplace.ui.app.LoadingButton

@OptIn(ExperimentalLayoutApi::class)
@Preview(showSystemUi = true)
@Composable
fun AddAddress(
    regionId: String = "-1",
    cityId: String = "-1",
    addressId: String = "-1",
    storeViewModel: StoreViewModel = hiltViewModel(),
    navHostController: NavHostController = rememberNavController()
) {
    val strings = LocalStrings.current
    val locationViewModel: LocationViewModel = hiltViewModel()
    val locations = locationViewModel.state.collectAsState()
    val addState = remember {
        storeViewModel.addLocationState
    }
    LaunchedEffect(true) {
        locationViewModel.initLocations(region = true)
    }
    val selectedRegion = remember {
        mutableStateOf(regionId)
    }
    val selectedCity = remember {
        mutableStateOf(cityId)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
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
                text = strings.addAddress, style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.W600
                ), textAlign = TextAlign.Center,
                color = Color(0xFF1A1A1A),
                modifier = Modifier.align(Alignment.Center),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

        }


        Spacer(modifier = Modifier.height(16.dp))
//
//        Text(
//            text = strings.addressName,
//            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
//            color = Color(0xFF1C2024)
//        )

//        Column(
//            modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
//            verticalArrangement = Arrangement.spacedBy(6.dp)
//        ) {
//            Text(
//                text = getRequiredText(strings.turkmen),
//                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
//                color = Color(0xFF344054)
//            )
//
//            OutlinedTextField(
//                value = "",
//                colors = OutlinedTextFieldDefaults.colors(
//                    unfocusedBorderColor = Color(0x1C000030),
//                    focusedBorderColor = Color(0x1C000030),
//                    unfocusedTextColor = Color(0x7500041D),
//                    focusedTextColor = Color(0xFF00041D),
//                ),
//                onValueChange = {
//                },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(
//                    imeAction = ImeAction.Next
//                ),
//                placeholder = {
//                    Text(text = strings.address, color = Color(0xFF667085))
//                },
//                modifier = Modifier.fillMaxWidth(),
//                textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
//            )
//
//            Text(
//                text = getRequiredText(strings.russian),
//                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
//                color = Color(0xFF344054)
//            )
//
//            OutlinedTextField(
//                value = "",
//                colors = OutlinedTextFieldDefaults.colors(
//                    unfocusedBorderColor = Color(0x1C000030),
//                    focusedBorderColor = Color(0x1C000030),
//                    unfocusedTextColor = Color(0x7500041D),
//                    focusedTextColor = Color(0xFF00041D),
//                ),
//                onValueChange = {
//                },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(
//                    imeAction = ImeAction.Next
//                ),
//                placeholder = {
//                    Text(text = strings.address, color = Color(0xFF667085))
//                },
//                modifier = Modifier.fillMaxWidth(),
//                textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
//            )
//
//            Text(
//                text = getRequiredText(strings.english),
//                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
//                color = Color(0xFF344054)
//            )
//
//            OutlinedTextField(
//                value = "",
//                colors = OutlinedTextFieldDefaults.colors(
//                    unfocusedBorderColor = Color(0x1C000030),
//                    focusedBorderColor = Color(0x1C000030),
//                    unfocusedTextColor = Color(0x7500041D),
//                    focusedTextColor = Color(0xFF00041D),
//                ),
//                onValueChange = {
//                },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(
//                    imeAction = ImeAction.Next
//                ),
//                placeholder = {
//                    Text(text = strings.address, color = Color(0xFF667085))
//                },
//                modifier = Modifier.fillMaxWidth(),
//                textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
//            )
//        }

        Spacer(modifier = Modifier.height(16.dp))

        if (addState.value.error.isNullOrEmpty().not() || addState.value.message.isNullOrEmpty()
                .not()
        ) {
            ErrorField()
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = strings.addressPlace,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
            color = Color(0xFF1C2024)
        )

        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            if (locations.value.loading) {
                AppLoading(modifier = Modifier.fillMaxWidth())
            } else if (locations.value.error) {
                AppError(modifier = Modifier.fillMaxWidth()) {
                    locationViewModel.getLocations()
                }
            } else {
                FlowRow(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    locations.value.locations?.let { list ->
                        repeat(list.size) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                AppCheckBox(
                                    checked = list[it].id == selectedRegion.value,
                                    onChange = { isCheck ->
                                        if (isCheck) {
                                            selectedRegion.value = list[it].id
                                        } else {
                                            selectedCity.value = "-1"
                                        }
                                    }
                                )
                                Text(
                                    text = translateValue(instance = list[it], property = "name"),
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400),
                                    color = Color(0xFF344054)
                                )
                            }
                        }
                    }

                }

                Text(
                    text = strings.city,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                    color = Color(0xFF1C2024)
                )
                FlowRow(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    locations.value.locations?.let { list ->
                        val cities = list.findLast { v -> v.id == selectedRegion.value }?.districts
                        cities?.let {
                            repeat(cities.size) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    AppCheckBox(checked = cities[it].id == selectedCity.value,
                                        onChange = { isCheck ->
                                            if (isCheck) {
                                                selectedCity.value = cities[it].id
                                            } else {
                                                selectedCity.value = "-1"
                                            }
                                        })
                                    Text(
                                        text = translateValue(
                                            instance = cities[it],
                                            property = "name"
                                        ),
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400),
                                        color = Color(0xFF344054)
                                    )
                                }
                            }
                        }
                    }

                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        LoadingButton(
            loading = addState.value.loading,
            onClick = {
                if(addressId!="-1"){
                    storeViewModel.updateLocation(selectedRegion.value, addressId, selectedCity.value) {
                        navHostController.navigateUp()
                    }
                } else {
                    storeViewModel.addLocation(selectedRegion.value, selectedCity.value) {
                        navHostController.navigateUp()
                    }
                }
            },
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = strings.save,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


    }
}

fun getRequiredText(text: String): AnnotatedString {
    return buildAnnotatedString {
        append(text)
        withStyle(SpanStyle(color = Color(0xFFC62A2F))) {
            append("*")
        }
    }
}