package com.komekci.marketplace.features.home.presentation.ui.location

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.komekci.marketplace.core.locale.translateValue
import com.komekci.marketplace.features.home.presentation.ui.SearchInput
import com.komekci.marketplace.features.home.presentation.viewmodel.LocationViewModel
import com.komekci.marketplace.state.LocalProductFilter
import com.komekci.marketplace.ui.app.AppError
import com.komekci.marketplace.ui.app.AppLoading
import com.komekci.marketplace.ui.app.NoData
import com.komekci.marketplace.ui.navigation.Routes

@Composable
fun SelectLocation(homeNavController: NavHostController) {
    val viewModel: LocationViewModel = hiltViewModel()
    LaunchedEffect(true) {
        viewModel.initLocations()
    }
    val productFilter = LocalProductFilter.current

    val locations = viewModel.state.collectAsState()




    if (locations.value.loading) {
        AppLoading(modifier = Modifier.fillMaxSize())
    } else if (locations.value.error) {
        AppError {
            viewModel.getLocations()
        }
    } else if (locations.value.isEmpty) {
        NoData(modifier = Modifier.fillMaxSize()) {
            viewModel.getLocations()
        }
    } else {
        locations.value.locations?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                repeat(it.size) { index ->
                    val title = translateValue(it[index],"name")
                    LocationItem(
                        modifier = Modifier.fillMaxWidth(),
                        title = title,
                        subtitle = "Welaýat ${it[index].districts.count()}"
                    ) {
                        homeNavController.navigate(Routes.On_Country.replace("{name}", title).replace("{c_id}", it[index].id))
//                        productFilter.value = productFilter.value.copy(
//                            region = if (it[index].id == productFilter.value.region) "0" else it[index].id
//                        )
//                        val f = it.find { fl -> fl.id == it[index].id }
//                        if (f == null || f.districts.isEmpty()) {
//                            homeNavController.navigateUp()
//                        }
                    }
                }
            }

//                LazyColumn(modifier = Modifier.fillMaxSize()) {
//                    it.filter { fl ->
//                        fl.id == productFilter.value.region
//                    }.let { districts ->
//                        if(districts.isNotEmpty()) {
//                            items(districts[0].districts) { district ->
//                                DistrictItem(title = district.name_tm, selected = productFilter.value.district == district.id) {
//                                    productFilter.value = productFilter.value.copy(
//                                        district = if(productFilter.value.district == district.id) "0" else district.id
//                                    )
//                                    homeNavController.navigateUp()
//                                }
//                            }
//                        }
//                    }
//                }
        }


    }
}