package com.komekci.marketplace.features.home.presentation.ui.discount

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.features.home.presentation.viewmodel.DiscountViewModel
import com.komekci.marketplace.ui.app.AppError
import com.komekci.marketplace.ui.app.AppLoading
import com.komekci.marketplace.ui.app.NoData
import com.komekci.marketplace.ui.navigation.Routes

@Composable
fun DiscountScreen(navHostController: NavHostController) {
    val strings = LocalStrings.current
    val viewModel: DiscountViewModel = hiltViewModel()
    LaunchedEffect(true) {
        viewModel.initDiscounts()
    }
    val state = viewModel.state.collectAsState()
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = strings.discounts, style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.W700
            ),
            color = Color(0xFF000000),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        if (state.value.loading) {
            AppLoading(modifier = Modifier.fillMaxSize())
        } else if (state.value.error) {
            AppError {
                viewModel.getDiscounts()
            }
        } else if (state.value.isEmpty) {
            NoData(modifier = Modifier.fillMaxSize()) {
                viewModel.getDiscounts()
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                state.value.discounts?.let { discounts ->
                    items(discounts.size) {
                        DiscountItem(item = discounts[it], modifier = Modifier.fillMaxWidth()) {
                            navHostController.navigate(Routes.onCategory)
                        }
                    }
                }
            }
        }
    }
}