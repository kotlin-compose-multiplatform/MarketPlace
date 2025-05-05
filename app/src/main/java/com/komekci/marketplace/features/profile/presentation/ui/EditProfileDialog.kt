package com.komekci.marketplace.features.profile.presentation.ui

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.R
import com.komekci.marketplace.core.utils.FileUtils
import com.komekci.marketplace.core.utils.PhoneNumber
import com.komekci.marketplace.features.auth.presentation.ui.ErrorField
import com.komekci.marketplace.features.auth.presentation.ui.TreeSelect
import com.komekci.marketplace.features.auth.presentation.viewmodel.CreateAccountViewModel
import com.komekci.marketplace.features.create_store.presentation.ui.CupertinoSelection
import com.komekci.marketplace.features.home.presentation.viewmodel.LocationViewModel
import com.komekci.marketplace.features.profile.presentation.viewmodel.ProfileViewModel
import com.komekci.marketplace.ui.app.AskPermissions
import com.komekci.marketplace.ui.app.ImageLoader
import com.komekci.marketplace.ui.app.LoadingButton
import com.komekci.marketplace.ui.theme.newTextColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun EditProfileDialog(
    open: Boolean = true,
    viewModel: ProfileViewModel = hiltViewModel(),
    onClose: () -> Unit = {}
) {
    val strings = LocalStrings.current
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val cviewmodel: CreateAccountViewModel = hiltViewModel()
    val countries = cviewmodel.countryState

    LaunchedEffect(true) {
        cviewmodel.getAllCountries()
    }


    val deleteState = remember {
        viewModel.deleteUserImageState
    }

    val coroutine = rememberCoroutineScope()

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
    val context = LocalContext.current

    if (open) {
        val askPermission = remember {
            mutableStateOf(false)
        }
        val state = remember {
            viewModel.updateUserState
        }
        val user = viewModel.profile.collectAsState()
        val name = remember {
            mutableStateOf("${user.value?.username}")
        }
        val phone = remember {
            mutableStateOf(PhoneNumber.prettierPhone("${user.value?.phone}"))
        }


        LaunchedEffect(user.value) {
            cviewmodel.updateSelectedRegion(user.value?.regionId)
            cviewmodel.updateSelectedCountryId(user.value?.countryId)
        }


        fun startTask() {
            coroutine.launch {
                withContext(Dispatchers.IO) {

                    val file = selectedImageUri?.let {
                        File(FileUtils.getPath(context, it))
                    }
                    viewModel.updateUser(
                        name = name.value,
                        phone = PhoneNumber.removeExtra(phone.value),
                        countryId = cviewmodel.selectedCountryId.value,
                        regionId = cviewmodel.selectedRegion.value,
                        image = file
                    ) {
                        selectedImageUri = null
                        onClose()
                        viewModel.getProfile()
                    }
                }
            }
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
        ModalBottomSheet(
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            onDismissRequest = onClose,
            containerColor = Color.White,
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
            dragHandle = {}
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = strings.editProfile,
                        color = Color(0xFF2F313F),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 20.sp, fontWeight = FontWeight.W700
                        ),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier.weight(1f)
                    )
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .border(1.5.dp, Color(0xFFF2F2F5), CircleShape)
                            .clickable { onClose() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Clear, contentDescription = null, tint = Color(0xFF8B8D98)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
                state.value.error?.let {
                    ErrorField(
                        text = state.value.error ?: "",
                        message = state.value.message,
                        errorCode = 400
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = strings.myName,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                    color = Color(0xFF1C2024)
                )
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
                        Text(text = "...")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = strings.myNumber,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                    color = Color(0xFF1C2024)
                )
                OutlinedTextField(
                    value = phone.value,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0x1C000030),
                        focusedBorderColor = Color(0x1C000030),
                        unfocusedTextColor = Color(0x7500041D),
                        focusedTextColor = Color(0xFF00041D),
                    ),
                    onValueChange = {
                        phone.value = it
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Phone
                    ),
                    placeholder = {
                        Text(text = "...")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
                )
                Spacer(modifier = Modifier.height(6.dp))
                if(countries.value.countries.isNotEmpty()) {
                    TreeSelect(
                        countries = countries.value.countries,
                        defaultCountry = countries.value.countries.find { it.id === user.value?.countryId },
                        defaultRegion = countries.value.countries.flatMap { it.regions?: emptyList() }.find { it.id === user.value?.regionId },
                        onCountrySelect = { c->
                            cviewmodel.updateSelectedCountryId(c.id)
                        },
                        onRegionSelect = { r->
                            cviewmodel.updateSelectedRegion(r.id)
                        }
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = strings.myImage,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                    color = Color(0xFF1C2024)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    ImageLoader(
                        uri = selectedImageUri,
                        url = "${user.value?.image}",
                        modifier = Modifier
                            .size(62.dp)
                            .clip(RoundedCornerShape(6.dp)),
                        contentScale = ContentScale.Crop
                    )
                    LoadingButton(
                        loading = deleteState.value.loading,
                        onClick = {
                            viewModel.deleteImage {
                                selectedImageUri = null
                            }
                        }, colors = ButtonDefaults.buttonColors(
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



                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {


                    Button(
                        onClick = {
                            selectedImageUri = null
                            onClose()
                        },
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.weight(1f),
                        enabled = state.value.loading.not(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0x0D05C005)
                        ),
                        border = BorderStroke(1.dp, Color(0x99008D1A))
                    ) {
                        Text(
                            text = strings.cancel,
                            color = Color(0xD6006316),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W500
                            )
                        )
                    }





                    LoadingButton(
                        loading = state.value.loading,
                        onClick = {
                            startTask()
                        },
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = strings.accept,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W500
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}