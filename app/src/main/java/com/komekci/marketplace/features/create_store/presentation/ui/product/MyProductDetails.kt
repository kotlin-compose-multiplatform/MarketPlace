package com.komekci.marketplace.features.create_store.presentation.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.R
import com.komekci.marketplace.core.locale.translateValue
import com.komekci.marketplace.features.create_store.presentation.viewmodel.StoreViewModel
import com.komekci.marketplace.ui.app.ImageLoader
import com.komekci.marketplace.ui.app.LoadingButton
import com.komekci.marketplace.ui.navigation.Routes
import com.komekci.marketplace.ui.theme.blackColor
import com.komekci.marketplace.ui.theme.errorColor

@Preview(showSystemUi = true)
@Composable
fun MyProductDetails(
    storeViewModel: StoreViewModel = hiltViewModel(),
    navHostController: NavHostController = rememberNavController()
) {
    val strings = LocalStrings.current
    val state = remember {
        storeViewModel.singleProduct
    }
    val deleteState = remember {
        storeViewModel.deleteState
    }
    val showDelete = remember {
        mutableStateOf(false)
    }
    if (showDelete.value) {
        AlertDialog(
            shape = RoundedCornerShape(12.dp),
            containerColor = Color.White,
            title = {
                Text(text = "Do you want delete?", color = blackColor)
            },
            dismissButton = {
                Button(
                    shape = RoundedCornerShape(8.dp),
                    onClick = { showDelete.value = false }, colors = ButtonDefaults.buttonColors(
                        containerColor = errorColor
                    )
                ) {
                    Text(text = "NO", color = MaterialTheme.colorScheme.onPrimary)
                }
            },
            onDismissRequest = {
                showDelete.value = false
            }, confirmButton = {
                LoadingButton(
                    shape = RoundedCornerShape(8.dp),
                    loading = deleteState.value.loading,
                    onClick = {
                        storeViewModel.deleteProduct(state.value?.id?.toString() ?: "") {
                            navHostController.navigateUp()
                        }
                    }) {
                    Text(text = "YES", color = MaterialTheme.colorScheme.onPrimary)
                }
            })
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
                text = strings.newProduct, style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.W600
                ), textAlign = TextAlign.Center,
                color = Color(0xFF1A1A1A),
                modifier = Modifier.align(Alignment.Center),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        state.value?.let { product ->
            key(product) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = translateValue(instance = product.name?:"", property = "", prefix = ""),
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                            color = Color(0xFF1C2024)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(Color(0xFFF9F9FB), RoundedCornerShape(4.dp))
                                    .clip(RoundedCornerShape(4.dp))
                                    .clickable {
                                        storeViewModel. getSingleProduct(product.id?:0)
                                        navHostController.navigate(Routes.EditProduct)
                                    }
                                    .padding(6.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.edit_icon),
                                    contentDescription = null,
                                    tint = Color(0xFF3F3F46),
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color(0xFFFFF7F7), RoundedCornerShape(4.dp))
                                    .clickable {
                                        showDelete.value = true
                                    }
                                    .padding(6.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.trash),
                                    contentDescription = null,
                                    tint = Color(0xFFC62A2F),
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(26.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFFE4E4E9), RoundedCornerShape(4.dp))
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = strings.nameOfProduct,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF344054)
                    )
                    Text(
                        text = "Türkmen: ${product.name?.tm}",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF1C2024)
                    )
                    Text(
                        text = "Русский: ${product.name?.ru}",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF1C2024)
                    )
                    Text(
                        text = "English: ${product.name?.en}",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF1C2024)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFFE4E4E9), RoundedCornerShape(4.dp))
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = strings.images,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF344054)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                        product.images?.let { imgs ->
                            items(imgs.size) { index ->
                                ImageLoader(
                                    url = imgs[index]?.image?:"",
                                    modifier = Modifier
                                        .size(70.dp)
                                        .clip(RoundedCornerShape(5.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = strings.codeOfProduct,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF344054)
                    )

                    Text(
                        text = "#${product.code}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.W500,
                            fontSize = 18.sp
                        ),
                        color = Color(0xFF344054)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFFE4E4E9), RoundedCornerShape(4.dp))
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = strings.category,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.W500,
                            fontSize = 18.sp
                        ),
                        color = Color(0xFF1C2024)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = strings.selectCategory,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF344054)
                    )
                    Text(
                        text = if(product.categoryName!=null) translateValue(
                            instance = product.categoryName,
                            property = "",
                            prefix = ""
                        ) else "",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF667085)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = strings.subCategory,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF344054)
                    )
                    Text(
                        text = if(product.catalogName!=null) translateValue(
                            instance = product.catalogName,
                            property = "",
                            prefix = ""
                        ) else "",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF667085)
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = strings.brand,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF344054)
                    )
                    Text(
                        text = product.brandName?:"",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF667085)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFFE4E4E9), RoundedCornerShape(4.dp))
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = strings.aboutProduct,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.W500,
                            fontSize = 18.sp
                        ),
                        color = Color(0xFF1C2024)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Türkmençe",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF344054)
                    )
                    Text(
                        text = product.description?.tm?:"",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF667085)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Русский",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF344054)
                    )
                    Text(
                        text = product.description?.ru?:"",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF667085)
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "English",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF344054)
                    )
                    Text(
                        text = product.description?.en?:"",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF667085)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFFE4E4E9), RoundedCornerShape(4.dp))
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    product.prices?.forEachIndexed { index, v->
                        Column(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = strings.productPrice+" (${v?.currency})",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                                color = Color(0xFF344054)
                            )
                            OutlinedTextField(
                                value = v?.price?.toString()?:"0",
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = Color(0x1C000030),
                                    focusedBorderColor = Color(0x1C000030),
                                    unfocusedTextColor = Color(0x7500041D),
                                    focusedTextColor = Color(0xFF00041D),
                                ),
                                onValueChange = { newPrice->

                                },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Number
                                ),
                                readOnly = true,
                                placeholder = {
                                    Text(
                                        text = strings.enterProductPrice, color = Color(0xFF667085),
                                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
                                    )
                                },
                                modifier = Modifier.fillMaxWidth(),
                                textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = strings.discountOfProduct,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                                color = Color(0xFF344054)
                            )
                            OutlinedTextField(
                                value = v?.discount.toString(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = Color(0x1C000030),
                                    focusedBorderColor = Color(0x1C000030),
                                    unfocusedTextColor = Color(0x7500041D),
                                    focusedTextColor = Color(0xFF00041D),
                                ),
                                onValueChange = { newPrice->

                                },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Number
                                ),
                                readOnly = true,
                                placeholder = {
                                    Text(
                                        text = strings.enterProductDiscount, color = Color(0xFF667085),
                                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
                                    )
                                },
                                trailingIcon = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Text(
                                            text = "%   ",
                                            color = Color(0xFF667085),
                                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
                                        )
                                        Icon(
                                            Icons.Default.KeyboardArrowDown,
                                            contentDescription = null,
                                            tint = Color(0xFF343330)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
                            )
                        }
                    }
                }
            }

        }
    }
}