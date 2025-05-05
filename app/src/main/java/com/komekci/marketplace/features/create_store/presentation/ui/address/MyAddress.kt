package com.komekci.marketplace.features.create_store.presentation.ui.address

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.core.locale.translateValue
import com.komekci.marketplace.features.create_store.domain.model.UpdateLocationNavParams
import com.komekci.marketplace.features.create_store.presentation.viewmodel.StoreViewModel
import com.komekci.marketplace.ui.navigation.Routes

@Preview(showSystemUi = true)
@Composable
fun MyAddress(
    storeViewModel: StoreViewModel = hiltViewModel(),
    navHostController: NavHostController = rememberNavController()
) {
    val strings = LocalStrings.current
    val state = storeViewModel.myStore.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
                text = strings.myAddress, style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.W600
                ), textAlign = TextAlign.Center,
                color = Color(0xFF1A1A1A),
                modifier = Modifier.align(Alignment.Center),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            state.value.data?.locations?.let { locations ->
                items(locations.size) {
                    locations[it].city?.let { city->
                        AddressItem(
                            region = translateValue(
                                instance = locations[it].region!!.name,
                                property = "",
                                prefix = ""
                            ),
                            city = if(city.name!=null) translateValue(
                                instance = city.name,
                                property = "",
                                prefix = ""
                            ) else "",
                            onDelete = {
                                storeViewModel.deleteLocation(locations[it].id.toString()) {

                                }
                            },
                            onEditClick = {
                                navHostController.navigate(
                                    UpdateLocationNavParams(
                                        addressId = locations[it].id.toString(),
                                        regionId = locations[it].region?.id.toString(),
                                        cityId = locations[it].city?.id.toString(),
                                    )
                                )
                            }
                        )
                    }

                }
            }
            item {
                Button(
                    onClick = { navHostController.navigate(Routes.AddAddress) },
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0x0D05C005)
                    ),
                    border = BorderStroke(1.dp, Color(0x99008D1A))
                ) {
                    Text(
                        text = strings.addAddress,
                        color = Color(0xD6006316),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W500
                        )
                    )
                }
            }

        }
    }
}