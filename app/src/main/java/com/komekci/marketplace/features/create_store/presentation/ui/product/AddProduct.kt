package com.komekci.marketplace.features.create_store.presentation.ui.product

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastAny
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.R
import com.komekci.marketplace.core.locale.translateValue
import com.komekci.marketplace.core.utils.FileUtils
import com.komekci.marketplace.features.create_store.data.entity.add_product.ProductPrice
import com.komekci.marketplace.features.create_store.presentation.ui.getRequiredText
import com.komekci.marketplace.features.create_store.presentation.viewmodel.StoreViewModel
import com.komekci.marketplace.ui.app.AppError
import com.komekci.marketplace.ui.app.AppLoading
import com.komekci.marketplace.ui.app.AskPermissions
import com.komekci.marketplace.ui.app.ImageLoader
import com.komekci.marketplace.ui.app.LoadingButton
import com.komekci.marketplace.ui.app.Select
import com.komekci.marketplace.ui.formbuilder.FormState
import com.komekci.marketplace.ui.formbuilder.TextFieldState
import com.komekci.marketplace.ui.formbuilder.Validators
import com.komekci.marketplace.ui.theme.errorColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


val currency = listOf(
    "TMT",
    "UZS",
    "KZT",
)

@Composable
fun AddProduct(
    storeViewModel: StoreViewModel,
    navHostController: NavHostController = rememberNavController()
) {
    val params = storeViewModel.params.collectAsState()
    LaunchedEffect(true) {
        storeViewModel.initParams()
    }
    var selectedImageUri by remember {
        mutableStateOf<List<Uri>?>(null)
    }
    val coroutine = rememberCoroutineScope()
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uri ->
            try {
                selectedImageUri = uri
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    )

    val askPermission = remember {
        mutableStateOf(false)
    }

    var permissions = listOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        permissions = listOf(
            android.Manifest.permission.READ_MEDIA_IMAGES,
            android.Manifest.permission.READ_MEDIA_VIDEO
        )
    }

    AskPermissions(permissions = permissions, show = askPermission.value) {
        askPermission.value = false
        singlePhotoPickerLauncher.launch(
            "image/*"
        )
    }
    fun checkPermission() {
        askPermission.value = true
    }

    val strings = LocalStrings.current

    var prices by remember {
        mutableStateOf<List<ProductPrice>>(currency.map { ProductPrice(
            currency = it,
            discount = 0.0,
            price = 0.0
        ) })
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

        if (params.value.loading) {
            AppLoading(modifier = Modifier.fillMaxSize())
        } else if (params.value.error.isNullOrEmpty().not()) {
            AppError(modifier = Modifier.fillMaxSize()) {
                storeViewModel.getParams()
            }
        } else {
            params.value.params?.let { item ->
                val context = LocalContext.current
                val uploadImageState = remember {
                    storeViewModel.uploadProductImageState
                }

                val addState = remember {
                    storeViewModel.addProductState
                }
                val catId = rememberSaveable {
                    mutableIntStateOf(-1)
                }
                val catalogId = rememberSaveable {
                    mutableIntStateOf(-1)
                }
                val subCatalogId = rememberSaveable {
                    mutableIntStateOf(-1)
                }
                val brandId = rememberSaveable {
                    mutableIntStateOf(-1)
                }

                val code = rememberSaveable {
                    mutableStateOf("")
                }


                val formState = FormState(
                    fields = listOf(
                        TextFieldState(
                            name = "name_tm",
                            transform = { it.trim().lowercase() },
                            validators = listOf(
                                Validators.Required("Adyny doly giriziň!"),
                                Validators.Min(2, "2 symboldan az bolmaly däl!"),
                            ),
                        ),
                        TextFieldState(
                            name = "name_ru",
                            transform = { it.trim().lowercase() },
                            validators = listOf(
                                Validators.Required("Adyny doly giriziň!"),
                                Validators.Min(2, "2 symboldan az bolmaly däl!"),
                            ),
                        ),
                        TextFieldState(
                            name = "name_en",
                            transform = { it.trim().lowercase() },
                            validators = listOf(
                                Validators.Required("Adyny doly giriziň!"),
                                Validators.Min(2, "2 symboldan az bolmaly däl!"),
                            ),
                        ),
                        TextFieldState(
                            name = "desc_en",
                            transform = { it.trim().lowercase() },
                            validators = listOf(
                                Validators.Required("Doly giriziň!"),
                                Validators.Min(2, "2 symboldan az bolmaly däl!"),
                            ),
                        ),

                        TextFieldState(
                            name = "desc_ru",
                            transform = { it.trim().lowercase() },
                            validators = listOf(
                                Validators.Required("Doly giriziň!"),
                                Validators.Min(2, "2 symboldan az bolmaly däl!"),
                            ),
                        ),
                        TextFieldState(
                            name = "desc_tm",
                            transform = { it.trim().lowercase() },
                            validators = listOf(
                                Validators.Required("Doly giriziň!"),
                                Validators.Min(2, "2 symboldan az bolmaly däl!"),
                            ),
                        ),
                        TextFieldState(
                            name = "price",
                            initial = "0",
                            transform = { it },
                            validators = listOf(
                                Validators.Required("Bahasyny giriziň!"),
                            ),
                        ),
                        TextFieldState(
                            name = "discount",
                            initial = "0",
                            transform = { it },
                            validators = listOf(
                                Validators.Required("Doly giriziň!"),
                            ),
                        ),

                        )
                )
                val form = remember { formState }

                val nameTm: TextFieldState = form.getState("name_tm")
                val nameRu: TextFieldState = form.getState("name_ru")
                val nameEn: TextFieldState = form.getState("name_en")
                val descTm: TextFieldState = form.getState("desc_tm")
                val descRu: TextFieldState = form.getState("desc_ru")
                val descEn: TextFieldState = form.getState("desc_en")
                val discount: TextFieldState = form.getState("discount")

                fun startTask() {
                    coroutine.launch {
                        withContext(Dispatchers.IO) {
                            val images = selectedImageUri?.let { imgs ->
                                imgs.map {
                                    File(FileUtils.getPath(context, it))
                                }
                            }
                            images?.let {
                                storeViewModel.uploadImages(images) { imageIds ->
                                    Log.e("IMAGES", imageIds.joinToString { "," })
                                    storeViewModel.addProduct(
                                        name_tm = nameTm.value,
                                        name_en = nameEn.value,
                                        name_ru = nameRu.value,
                                        desc_tm = descTm.value,
                                        desc_ru = descRu.value,
                                        desc_en = descEn.value,
                                        catId = catId.intValue,
                                        catalogId = catalogId.intValue,
                                        subCatId = subCatalogId.intValue,
                                        brandId = brandId.intValue,
                                        imagesId = imageIds,
                                        price = prices,
                                        discount = discount.value,
                                        code = code.value,
                                    ) {
                                        navHostController.navigateUp()
                                    }
                                }
                            }
                        }
                    }
                }
                Text(
                    text = strings.nameOfProduct,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                    color = Color(0xFF1C2024)
                )

                Column(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = getRequiredText(strings.turkmen),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF344054)
                    )

                    OutlinedTextField(
                        value = nameTm.value,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0x1C000030),
                            focusedBorderColor = Color(0x1C000030),
                            unfocusedTextColor = Color(0x7500041D),
                            focusedTextColor = Color(0xFF00041D),
                        ),
                        onValueChange = {
                            nameTm.change(it)
                        },
                        isError = nameTm.hasError,
                        supportingText = {
                            if (nameTm.hasError) {
                                Text(text = nameTm.errorMessage, color = errorColor)
                            }
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        placeholder = {
                            Text(text = strings.storeName, color = Color(0xFF667085))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
                    )

                    Text(
                        text = getRequiredText(strings.russian),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF344054)
                    )

                    OutlinedTextField(
                        value = nameRu.value,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0x1C000030),
                            focusedBorderColor = Color(0x1C000030),
                            unfocusedTextColor = Color(0x7500041D),
                            focusedTextColor = Color(0xFF00041D),
                        ),
                        onValueChange = {
                            nameRu.change(it)
                        },
                        isError = nameRu.hasError,
                        supportingText = {
                            if (nameRu.hasError) {
                                Text(text = nameRu.errorMessage, color = errorColor)
                            }
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        placeholder = {
                            Text(text = strings.storeName, color = Color(0xFF667085))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
                    )

                    Text(
                        text = getRequiredText(strings.english),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF344054)
                    )

                    OutlinedTextField(
                        value = nameEn.value,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0x1C000030),
                            focusedBorderColor = Color(0x1C000030),
                            unfocusedTextColor = Color(0x7500041D),
                            focusedTextColor = Color(0xFF00041D),
                        ),
                        onValueChange = {
                            nameEn.change(it)
                        },
                        isError = nameEn.hasError,
                        supportingText = {
                            if (nameEn.hasError) {
                                Text(text = nameEn.errorMessage, color = errorColor)
                            }
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        placeholder = {
                            Text(text = strings.storeName, color = Color(0xFF667085))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
                    )

                    Text(
                        text = strings.uploadPhoto,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF344054),
                        modifier = Modifier.clickable {
                            checkPermission()
                        }
                    )

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            checkPermission()
                        }) {
                        OutlinedTextField(
                            value = "",
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0x1C000030),
                                focusedBorderColor = Color(0x1C000030),
                                unfocusedTextColor = Color(0x7500041D),
                                focusedTextColor = Color(0xFF00041D),
                            ),
                            onValueChange = {
                            },
                            singleLine = true,
                            readOnly = true,
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.outline_file_upload_24),
                                    contentDescription = null,
                                    tint = Color(0xFF343330)
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next
                            ),
                            placeholder = {
                                Text(text = strings.addPhoto, color = Color(0xFF667085))
                            },
                            interactionSource = remember { MutableInteractionSource() }
                                .also { interactionSource ->
                                    LaunchedEffect(interactionSource) {
                                        interactionSource.interactions.collect {
                                            if (it is PressInteraction.Release) {
                                                checkPermission()
                                            }
                                        }
                                    }
                                },
                            modifier = Modifier
                                .fillMaxWidth(),
                            textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
                        )
                    }
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        selectedImageUri?.let { images ->
                            items(images.size) { index ->
                                Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                                    ImageLoader(
                                        uri = images[index],
                                        modifier = Modifier
                                            .size(60.dp)
                                            .clip(RoundedCornerShape(4.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                    if (uploadImageState.value.loading) {
                                        CircularProgressIndicator(modifier = Modifier.size(35.dp))
                                    } else if (uploadImageState.value.result.isNullOrEmpty()
                                            .not()
                                    ) {
                                        uploadImageState.value.result?.let { res ->
                                            if (res.fastAny { v -> v.index == index && v.id != -1 }) {
                                                Icon(
                                                    Icons.Default.CheckCircle,
                                                    contentDescription = null,
                                                    tint = MaterialTheme.colorScheme.primary
                                                )
                                            } else {
                                                Icon(
                                                    Icons.Default.Clear,
                                                    contentDescription = null,
                                                    tint = errorColor
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Text(
                        text = strings.codeOfProduct,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF344054)
                    )



                    OutlinedTextField(
                        value = code.value,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0x1C000030),
                            focusedBorderColor = Color(0x1C000030),
                            unfocusedTextColor = Color(0x7500041D),
                            focusedTextColor = Color(0xFF00041D),
                        ),
                        onValueChange = {
                            code.value = it
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        placeholder = {
                            Text(text = "#####", color = Color(0xFF667085))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = strings.category,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                    color = Color(0xFF1C2024)
                )

                Column(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val expanded = remember {
                        mutableStateOf("0")
                    }
                    Text(
                        text = strings.selectCategory,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF344054)
                    )

                    Select(
                        placeholder = strings.category,
                        items = item.categories?.map { v ->
                            translateValue(
                                instance = v.name,
                                property = "",
                                prefix = ""
                            )
                        } ?: emptyList(),
                        expanded = expanded.value == "1",
                        onSelected = {
                            item.categories?.let { l ->
                                catId.intValue = l[it].id
                            }
                        }
                    ) {
                        if (it) {
                            expanded.value = "1"
                        } else {
                            expanded.value = "0"
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = strings.subCategory,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF344054)
                    )
                    val realCatalogs = remember(catId.intValue) {
                        mutableStateOf(item.catalogs?.filter { it.categoryId == catId.intValue })
                    }
                    Select(
                        placeholder = strings.catalog,
                        items = realCatalogs.value?.map { v ->
                            translateValue(
                                instance = v.name,
                                property = "",
                                prefix = ""
                            )
                        } ?: emptyList(),
                        expanded = expanded.value == "2",
                        onSelected = {
                            realCatalogs.value?.let { l ->
                                catalogId.intValue = l[it].id
                            }
                        }) {
                        if (it) {
                            expanded.value = "2"
                        } else {
                            expanded.value = "0"
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = strings.subCategory,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF344054)
                    )

                    val realCatalogs2 = remember(catId.intValue) {
                        mutableStateOf(item.subCatalogs?.filter { it.categoryId == catId.intValue })
                    }

                    Select(
                        placeholder = strings.subCategory,
                        items = realCatalogs2.value?.map { v ->
                            translateValue(
                                instance = v.name,
                                property = "",
                                prefix = ""
                            )
                        } ?: emptyList(),
                        expanded = expanded.value == "4",
                        onSelected = {
                            realCatalogs2.value?.let { l ->
                                subCatalogId.intValue = l[it].id
                            }
                        }) {
                        if (it) {
                            expanded.value = "4"
                        } else {
                            expanded.value = "0"
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = strings.brand,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF344054)
                    )

                    val realBrands = remember(catId.intValue) {
                        mutableStateOf( item.brands?.filter { it.categoryId == catId.intValue })
                    }

                    Select(
                        placeholder = strings.brand,
                        items = realBrands.value?.map { v ->
                            v.name
                        } ?: emptyList(),
                        expanded = expanded.value == "3",
                        onSelected = {
                            realBrands.value?.let { l ->
                                brandId.intValue = l[it].id
                            }
                        }) {
                        if (it) {
                            expanded.value = "3"
                        } else {
                            expanded.value = "0"
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = strings.aboutProduct,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                    color = Color(0xFF1C2024)
                )

                Column(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = getRequiredText(strings.turkmen),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF344054)
                    )

                    OutlinedTextField(
                        value = descTm.value,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0x1C000030),
                            focusedBorderColor = Color(0x1C000030),
                            unfocusedTextColor = Color(0x7500041D),
                            focusedTextColor = Color(0xFF00041D),
                        ),
                        onValueChange = {
                            descTm.change(it)
                        },
                        isError = descTm.hasError,
                        supportingText = {
                            if (descTm.hasError) {
                                Text(text = descTm.errorMessage, color = errorColor)
                            }
                        },
                        minLines = 3,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        placeholder = {
                            Text(text = strings.enterDescription, color = Color(0xFF667085))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
                    )

                    Text(
                        text = getRequiredText(strings.russian),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF344054)
                    )

                    OutlinedTextField(
                        value = descRu.value,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0x1C000030),
                            focusedBorderColor = Color(0x1C000030),
                            unfocusedTextColor = Color(0x7500041D),
                            focusedTextColor = Color(0xFF00041D),
                        ),
                        onValueChange = {
                            descRu.change(it)
                        },
                        isError = descRu.hasError,
                        supportingText = {
                            if (descRu.hasError) {
                                Text(text = descRu.errorMessage, color = errorColor)
                            }
                        },
                        minLines = 3,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        placeholder = {
                            Text(text = strings.enterDescription, color = Color(0xFF667085))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
                    )

                    Text(
                        text = getRequiredText(strings.english),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF344054)
                    )

                    OutlinedTextField(
                        value = descEn.value,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0x1C000030),
                            focusedBorderColor = Color(0x1C000030),
                            unfocusedTextColor = Color(0x7500041D),
                            focusedTextColor = Color(0xFF00041D),
                        ),
                        onValueChange = {
                            descEn.change(it)
                        },
                        isError = descEn.hasError,
                        supportingText = {
                            if (descEn.hasError) {
                                Text(text = descEn.errorMessage, color = errorColor)
                            }
                        },
                        minLines = 3,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        placeholder = {
                            Text(text = strings.enterDescription, color = Color(0xFF667085))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = strings.productPrice,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                    color = Color(0xFF1C2024)
                )

                Column(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    prices.forEachIndexed { index, v->
                        Column(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = strings.productPrice+" (${v.currency})",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                                color = Color(0xFF344054)
                            )
                            OutlinedTextField(
                                value = v.price?.toString()?:"0",
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = Color(0x1C000030),
                                    focusedBorderColor = Color(0x1C000030),
                                    unfocusedTextColor = Color(0x7500041D),
                                    focusedTextColor = Color(0xFF00041D),
                                ),
                                onValueChange = { newPrice->
                                    prices = prices.map {
                                        if(v.currency == it.currency) {
                                            it.copy(price = try {
                                                newPrice.toDouble()
                                            } catch (ex: Exception) {0.0})
                                        } else {
                                            it
                                        }
                                    }
                                },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Number
                                ),
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
                                value = v.discount.toString(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = Color(0x1C000030),
                                    focusedBorderColor = Color(0x1C000030),
                                    unfocusedTextColor = Color(0x7500041D),
                                    focusedTextColor = Color(0xFF00041D),
                                ),
                                onValueChange = { newPrice->
                                    prices = prices.map {
                                        if(v.currency == it.currency) {
                                            it.copy(discount = try {
                                                newPrice.toDouble()
                                            } catch (ex: Exception) {0.0})
                                        } else {
                                            it
                                        }
                                    }
                                },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Number
                                ),
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



                Spacer(modifier = Modifier.height(16.dp))
                LoadingButton(
                    loading = uploadImageState.value.loading || addState.value.loading,
                    onClick = {
                        if (form.validate()) {
                            startTask()
                        }
                    },
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = strings.send,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                        color = Color.White
                    )
                }
            }
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