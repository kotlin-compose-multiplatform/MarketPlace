package com.komekci.marketplace.features.create_store.presentation.ui

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.R
import com.komekci.marketplace.core.utils.FileUtils
import com.komekci.marketplace.core.utils.PhoneNumber
import com.komekci.marketplace.features.auth.presentation.ui.ErrorField
import com.komekci.marketplace.features.create_store.presentation.viewmodel.StoreViewModel
import com.komekci.marketplace.ui.app.AskPermissions
import com.komekci.marketplace.ui.app.ImageLoader
import com.komekci.marketplace.ui.app.LoadingButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@Composable
fun AboutStore(
    storeViewModel: StoreViewModel,
    navHostController: NavHostController = rememberNavController()
) {
    val strings = LocalStrings.current
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val state = storeViewModel.myStore.collectAsState()
    val updateState = remember {
        storeViewModel.updateStoreState
    }
    val coroutine = rememberCoroutineScope()
    val askPermission = remember {
        mutableStateOf(false)
    }
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uri ->
            try {
                selectedImageUri = uri[0]
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    )

    var permissions = listOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        permissions = permissions.plus(android.Manifest.permission.READ_MEDIA_IMAGES)
        permissions = permissions.plus(android.Manifest.permission.READ_MEDIA_VIDEO)
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
                text = strings.aboutStore, style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.W600
                ), textAlign = TextAlign.Center,
                color = Color(0xFF1A1A1A),
                modifier = Modifier.align(Alignment.Center),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

        }

        Spacer(modifier = Modifier.height(16.dp))
        updateState.value.error?.let {
            ErrorField(
                text = updateState.value.error ?: "",
                message = updateState.value.message,
                errorCode = updateState.value.code
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        state.value.data?.let { store ->
            val context = LocalContext.current
            val name = remember {
                mutableStateOf(store.name?:"")
            }
            val phone = remember {
                mutableStateOf(store.phoneNumber?:"")
            }
            val tiktok = remember {
                mutableStateOf(store.tiktok?:"")
            }

            val instagram = remember {
                mutableStateOf(store.instagram?:"")
            }
            fun startTask() {
                coroutine.launch {
                    withContext(Dispatchers.IO) {
                        val file = selectedImageUri?.let {
                            File(FileUtils.getPath(context, it))
                        }
                        storeViewModel.updateStore(
                            name = name.value,
                            phone = phone.value,
                            image = file,
                            tiktok = tiktok.value,
                            instagram = instagram.value
                        ) {
                            selectedImageUri = null
                        }
                    }
                }
            }
            Text(
                text = strings.storeLogo,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                color = Color(0xFF1C2024)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                ImageLoader(
                    uri = selectedImageUri,
                    url = store.image?:"",
                    modifier = Modifier
                        .size(62.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    contentScale = ContentScale.Crop
                )
                Button(
                    onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFE5E5)
                    ),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.trash),
                        contentDescription = null,
                        tint = Color(0xFFC62A2F)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = strings.delete,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400),
                        color = Color(0xFFC62A2F)
                    )
                }
                Button(
                    onClick = {
                        checkPermission()
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEBF9EB)
                    ),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.edit),
                        contentDescription = null,
                        tint = Color(0xFF3D9A50)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = strings.edit,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400),
                        color = Color(0xFF3D9A50)
                    )
                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = strings.storeName,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                color = Color(0xFF1C2024)
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = name.value,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0x1C000030),
                    focusedBorderColor = Color(0x1C000030),
                    unfocusedTextColor = Color(0x7500041D),
                    focusedTextColor = Color(0xFF00041D),
                ),
                onValueChange = {
                    name.value = it
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

//            Column(
//                modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
//                verticalArrangement = Arrangement.spacedBy(6.dp)
//            ) {
//                Text(
//                    text = getRequiredText(strings.turkmen),
//                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
//                    color = Color(0xFF344054)
//                )
//
//                OutlinedTextField(
//                    value = "",
//                    colors = OutlinedTextFieldDefaults.colors(
//                        unfocusedBorderColor = Color(0x1C000030),
//                        focusedBorderColor = Color(0x1C000030),
//                        unfocusedTextColor = Color(0x7500041D),
//                        focusedTextColor = Color(0xFF00041D),
//                    ),
//                    onValueChange = {
//                    },
//                    singleLine = true,
//                    keyboardOptions = KeyboardOptions(
//                        imeAction = ImeAction.Next
//                    ),
//                    placeholder = {
//                        Text(text = strings.storeName, color = Color(0xFF667085))
//                    },
//                    modifier = Modifier.fillMaxWidth(),
//                    textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
//                )
//
//                Text(
//                    text = getRequiredText(strings.russian),
//                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
//                    color = Color(0xFF344054)
//                )
//
//                OutlinedTextField(
//                    value = "",
//                    colors = OutlinedTextFieldDefaults.colors(
//                        unfocusedBorderColor = Color(0x1C000030),
//                        focusedBorderColor = Color(0x1C000030),
//                        unfocusedTextColor = Color(0x7500041D),
//                        focusedTextColor = Color(0xFF00041D),
//                    ),
//                    onValueChange = {
//                    },
//                    singleLine = true,
//                    keyboardOptions = KeyboardOptions(
//                        imeAction = ImeAction.Next
//                    ),
//                    placeholder = {
//                        Text(text = strings.storeName, color = Color(0xFF667085))
//                    },
//                    modifier = Modifier.fillMaxWidth(),
//                    textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
//                )
//
//                Text(
//                    text = getRequiredText(strings.english),
//                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
//                    color = Color(0xFF344054)
//                )
//
//                OutlinedTextField(
//                    value = "",
//                    colors = OutlinedTextFieldDefaults.colors(
//                        unfocusedBorderColor = Color(0x1C000030),
//                        focusedBorderColor = Color(0x1C000030),
//                        unfocusedTextColor = Color(0x7500041D),
//                        focusedTextColor = Color(0xFF00041D),
//                    ),
//                    onValueChange = {
//                    },
//                    singleLine = true,
//                    keyboardOptions = KeyboardOptions(
//                        imeAction = ImeAction.Next
//                    ),
//                    placeholder = {
//                        Text(text = strings.storeName, color = Color(0xFF667085))
//                    },
//                    modifier = Modifier.fillMaxWidth(),
//                    textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
//                )
//            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = strings.aboutStore,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                color = Color(0xFF1C2024)
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = "${phone.value}",
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0x1C000030),
                    focusedBorderColor = Color(0x1C000030),
                    unfocusedTextColor = Color(0x7500041D),
                    focusedTextColor = Color(0xFF00041D),
                ),
                onValueChange = {
                    phone.value = it
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Phone
                ),
                placeholder = {
                    Text(text = strings.enterPhone, color = Color(0xFF667085))
                },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Instagram",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                color = Color(0xFF1C2024)
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = instagram.value,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0x1C000030),
                    focusedBorderColor = Color(0x1C000030),
                    unfocusedTextColor = Color(0x7500041D),
                    focusedTextColor = Color(0xFF00041D),
                ),
                onValueChange = {
                    instagram.value = it
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                placeholder = {
                    Text(text = "@komekchi", color = Color(0xFF667085))
                },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Tiktok",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                color = Color(0xFF1C2024)
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = tiktok.value,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0x1C000030),
                    focusedBorderColor = Color(0x1C000030),
                    unfocusedTextColor = Color(0x7500041D),
                    focusedTextColor = Color(0xFF00041D),
                ),
                onValueChange = {
                    tiktok.value = it
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                placeholder = {
                    Text(text = "@komekchi_tiktok", color = Color(0xFF667085))
                },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
            )

//            Column(
//                modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
//                verticalArrangement = Arrangement.spacedBy(6.dp)
//            ) {
//                Text(
//                    text = getRequiredText(strings.turkmen),
//                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
//                    color = Color(0xFF344054)
//                )
//
//                OutlinedTextField(
//                    value = "",
//                    colors = OutlinedTextFieldDefaults.colors(
//                        unfocusedBorderColor = Color(0x1C000030),
//                        focusedBorderColor = Color(0x1C000030),
//                        unfocusedTextColor = Color(0x7500041D),
//                        focusedTextColor = Color(0xFF00041D),
//                    ),
//                    onValueChange = {
//                    },
//                    minLines = 3,
//                    keyboardOptions = KeyboardOptions(
//                        imeAction = ImeAction.Next
//                    ),
//                    placeholder = {
//                        Text(text = strings.enterDescription, color = Color(0xFF667085))
//                    },
//                    modifier = Modifier.fillMaxWidth(),
//                    textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
//                )
//
//                Text(
//                    text = getRequiredText(strings.russian),
//                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
//                    color = Color(0xFF344054)
//                )
//
//                OutlinedTextField(
//                    value = "",
//                    colors = OutlinedTextFieldDefaults.colors(
//                        unfocusedBorderColor = Color(0x1C000030),
//                        focusedBorderColor = Color(0x1C000030),
//                        unfocusedTextColor = Color(0x7500041D),
//                        focusedTextColor = Color(0xFF00041D),
//                    ),
//                    onValueChange = {
//                    },
//                    minLines = 3,
//                    keyboardOptions = KeyboardOptions(
//                        imeAction = ImeAction.Next
//                    ),
//                    placeholder = {
//                        Text(text = strings.enterDescription, color = Color(0xFF667085))
//                    },
//                    modifier = Modifier.fillMaxWidth(),
//                    textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
//                )
//
//                Text(
//                    text = getRequiredText(strings.english),
//                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
//                    color = Color(0xFF344054)
//                )
//
//                OutlinedTextField(
//                    value = "",
//                    colors = OutlinedTextFieldDefaults.colors(
//                        unfocusedBorderColor = Color(0x1C000030),
//                        focusedBorderColor = Color(0x1C000030),
//                        unfocusedTextColor = Color(0x7500041D),
//                        focusedTextColor = Color(0xFF00041D),
//                    ),
//                    onValueChange = {
//                    },
//                    minLines = 3,
//                    keyboardOptions = KeyboardOptions(
//                        imeAction = ImeAction.Next
//                    ),
//                    placeholder = {
//                        Text(text = strings.enterDescription, color = Color(0xFF667085))
//                    },
//                    modifier = Modifier.fillMaxWidth(),
//                    textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
//                )
//            }

            Spacer(modifier = Modifier.height(16.dp))
            LoadingButton(
                loading = updateState.value.loading,
                onClick = { startTask() },
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = strings.save,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                    color = Color.White
                )
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