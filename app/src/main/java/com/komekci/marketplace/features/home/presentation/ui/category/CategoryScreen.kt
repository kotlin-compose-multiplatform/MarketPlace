package com.komekci.marketplace.features.home.presentation.ui.category

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.features.home.presentation.ui.SearchInput
import com.komekci.marketplace.features.home.presentation.viewmodel.CategoryViewModel
import com.komekci.marketplace.state.LocalProductFilter
import com.komekci.marketplace.ui.app.AppError
import com.komekci.marketplace.ui.app.AppLoading
import com.komekci.marketplace.ui.app.NoData
import com.komekci.marketplace.ui.navigation.Routes
import com.primex.core.VerticalGrid

@Composable
fun CategoryScreen(homeNavController: NavHostController) {
    val viewModel: CategoryViewModel = hiltViewModel()

    LaunchedEffect(true) {
        viewModel.initCategories()
    }

    val strings = LocalStrings.current

    val state = viewModel.state.collectAsState()
    val productFilter = LocalProductFilter.current


    if (state.value.loading) {
        AppLoading(modifier = Modifier.fillMaxSize())
    } else if (state.value.error) {
        AppError {
            viewModel.getCategories()
        }
    } else if (state.value.isEmpty) {
        NoData(modifier = Modifier.fillMaxSize()) {
            viewModel.getCategories()
        }
    } else {
        state.value.categories?.let {
            VerticalGrid(
                columns = 3,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 12.dp, horizontal = 16.dp)
            ) {
                repeat(it.count()) { index ->
                    CategoryItem(
                        item = it[index],
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 12.dp,
                                end = if ((index + 1) % 3 == 0) 0.dp else 12.dp
                            )
                            .clickable {
                                homeNavController.navigate(Routes.onCategory)
                                productFilter.value = productFilter.value.copy(
                                    storeId = emptyList(),
                                    categoryId = listOf(it[index].id),
                                    catalogId = emptyList(),
                                    brandId = emptyList()
                                )
                            })
                }
            }

        }

    }
}