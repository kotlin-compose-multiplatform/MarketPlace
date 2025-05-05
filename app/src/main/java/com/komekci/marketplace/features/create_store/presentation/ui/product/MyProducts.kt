package com.komekci.marketplace.features.create_store.presentation.ui.product

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.R
import com.komekci.marketplace.features.create_store.presentation.viewmodel.StoreViewModel
import com.komekci.marketplace.ui.app.AppError
import com.komekci.marketplace.ui.app.AppLoading
import com.komekci.marketplace.ui.app.NoData
import com.komekci.marketplace.ui.navigation.Routes

@Composable
fun MyProducts(
    storeViewModel: StoreViewModel,
    navHostController: NavHostController = rememberNavController()
) {
    val strings = LocalStrings.current
    val state = storeViewModel.myProducts.collectAsState()
    LaunchedEffect(true) {
        storeViewModel.initMyProducts()
    }
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
                text = strings.myProducts, style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.W600
                ), textAlign = TextAlign.Center,
                color = Color(0xFF1A1A1A),
                modifier = Modifier.align(Alignment.Center),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        if (state.value.loading) {
            AppLoading(modifier = Modifier.fillMaxSize())
        } else if (state.value.error.isNullOrEmpty().not() || state.value.message.isNullOrEmpty()
                .not()
        ) {
            AppError(modifier = Modifier.fillMaxSize()) {
                storeViewModel.getMyProducts()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                item {
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { navHostController.navigate(Routes.HelperChats) },
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.weight(1f),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.chat_second),
                                contentDescription = "chat",
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(Modifier.width(3.dp))
                            Text(
                                text = strings.writeMessageKomekci,
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.W400
                                )
                            )
                        }
                        Button(
                            onClick = { navHostController.navigate(Routes.AddProduct) },
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0x0D05C005)
                            ),
                            border = BorderStroke(1.dp, Color(0x99008D1A))
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_add_box_24),
                                contentDescription = "add",
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(Modifier.width(3.dp))
                            Text(
                                text = strings.addProduct,
                                color = Color(0xD6006316),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.W400
                                )
                            )
                        }
                    }
                }
                if (state.value.data.isNullOrEmpty()) {
                    item {
                        NoData(modifier = Modifier.fillMaxSize()) {
                            storeViewModel.getMyProducts()
                        }
                    }
                }
                state.value.data?.let { list ->
                    items(list.size) { index ->
                        ProductItem(
                            product = list[index],
                            onClickEdit = {
                                storeViewModel.selectProduct(list[index])
                                navHostController.navigate(Routes.EditProduct)
                            }
                        ) {
                            storeViewModel.selectProduct(list[index])
                            navHostController.navigate(Routes.MyProduct)
                        }
                    }
                }
            }
        }
    }
}