package com.komekci.marketplace.features.product.presentation.ui.category

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.core.locale.translateValue
import com.komekci.marketplace.features.home.presentation.viewmodel.CategoryViewModel
import com.komekci.marketplace.state.LocalProductFilter
import com.komekci.marketplace.ui.app.AppError
import com.komekci.marketplace.ui.app.AppLoading
import com.komekci.marketplace.ui.app.NoData

@Preview(showSystemUi = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDialog(
    open: Boolean = true, onDismiss: () -> Unit = {}
) {
    val strings = LocalStrings.current
    val viewModel: CategoryViewModel = hiltViewModel()
    val catState = viewModel.state.collectAsState()
    LaunchedEffect(true) {
        viewModel.initCategories()
    }
    val productsFilter = LocalProductFilter.current
    if (open) {
        val state = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = state,
            shape = RoundedCornerShape(0.dp),
            containerColor = Color.White,
            scrimColor = Color.Transparent,
            dragHandle = {},
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(18.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                    ) {
                        IconButton(onClick = onDismiss) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = null,
                                tint = Color(0xFF1A1A1A),
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        Text(
                            text = strings.categories,
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.W500),
                            color = Color(0xFF1A1A1A),
                            modifier = Modifier.align(Alignment.Center)
                        )

                    }

                    if (catState.value.loading) {
                        AppLoading(modifier = Modifier.fillMaxSize())
                    } else if (catState.value.error) {
                        AppError {
                            viewModel.getCategories()
                        }
                    } else if (catState.value.isEmpty) {
                        NoData(modifier = Modifier.fillMaxSize()) {
                            viewModel.getCategories()
                        }
                    } else {
                        catState.value.categories?.let { list ->
                            LazyColumn {
                                items(list.size) { index ->
                                    val selected = remember {
                                        mutableStateOf(productsFilter.value.categoryId.contains(list[index].id))
                                    }
                                    CategorySelectItem(
                                        text = translateValue(
                                            instance = list[index],
                                            property = "title"
                                        ),
                                        selected = selected.value,
                                    ) {
                                        onDismiss()
                                        if(productsFilter.value.categoryId.contains(list[index].id)) {
                                            productsFilter.value = productsFilter.value.copy(
                                                categoryId = productsFilter.value.categoryId.minus(list[index].id)
                                            )
                                        } else {
                                            productsFilter.value = productsFilter.value.copy(
                                                categoryId = productsFilter.value.categoryId.plus(list[index].id)
                                            )
                                        }
                                        selected.value = !selected.value
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}