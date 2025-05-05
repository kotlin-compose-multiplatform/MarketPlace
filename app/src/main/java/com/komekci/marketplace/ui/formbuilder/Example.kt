package com.komekci.marketplace.ui.formbuilder

//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
//import androidx.compose.material.icons.filled.Call
//import androidx.compose.material.icons.filled.Lock
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//@Composable
//fun LoginScreen(loginViewModel: LoginViewModel, onSuccess: (String) -> Unit = {}) {
//
//    val state = loginViewModel.authState.collectAsState()
//    val shape = RoundedCornerShape(12.dp)
//    val formState = FormState(
//        fields = listOf(
//            TextFieldState(
//                name = "username",
//                transform = { it.trim().lowercase() },
//                validators = listOf(
//                    Validators.Phone("Telefon belgini dogry giriziň!"),
//                    Validators.Required("Telefon belgini doly giriziň!"),
//                    Validators.Min(8, "8 symboldan az bolmaly däl!"),
//                ),
//            ),
//            TextFieldState(
//                name = "password",
//                transform = { it.trim().lowercase() },
//                validators = listOf(
//                    Validators.Required("Açar sözüni doly giriziň!"),
//                    Validators.Min(4, "4 symboldan az bolmaly däl!")
//                ),
//            )
//        )
//    )
//    val form = remember { formState }
//
//    val username: TextFieldState = form.getState("username")
//    val password: TextFieldState = form.getState("password")
//
//    val loginError = remember {
//        mutableStateOf<String?>(null)
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(20.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .align(Alignment.Center)
//                .verticalScroll(rememberScrollState())
//                .align(Alignment.CenterStart)
//                .clip(shape)
//                .padding(2.dp)
//                .background(MaterialTheme.colorScheme.surface, shape)
//                .padding(12.dp),
//            verticalArrangement = Arrangement.spacedBy(0.dp),
//            horizontalAlignment = Alignment.Start
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.menzil),
//                contentDescription = "Andy Rubin",
//                modifier = Modifier
//                    .width(66.dp)
//                    .height(84.dp)
//            )
//            Spacer(modifier = Modifier.height(18.dp))
//            Text(
//                text = "Menzil sürüji",
//                color = MaterialTheme.colorScheme.onSurfaceVariant,
//                style = MaterialTheme.typography.titleLarge,
//                textAlign = TextAlign.Center,
//                fontSize = 18.sp
//            )
//            Spacer(modifier = Modifier.height(4.dp))
//            Text(
//                text = "Ulgama girmek",
//                color = MaterialTheme.colorScheme.onSurface,
//                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.W600),
//                textAlign = TextAlign.Center,
//                fontSize = 32.sp
//            )
//            OutlinedTextField(
//                shape = RoundedCornerShape(10),
//                value = username.value,
//                onValueChange = { username.change(it)},
//                isError = username.hasError,
//                supportingText = {
//                    if (username.hasError) {
//                        Text(text = username.errorMessage, color = Color.Red)
//                    }
//                },
//                prefix = {
//                    Text(text = "+993")
//                },
//                leadingIcon = {
//                    Icon(Icons.Default.Call, contentDescription = "person")
//                },
//                label = {
//                    Text(text = "Telefon belgi")
//                },
//                keyboardOptions = KeyboardOptions.Default.copy(
//                    keyboardType = KeyboardType.Phone,
//                    imeAction = ImeAction.Next
//                ),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(0.dp, 20.dp, 0.dp, 0.dp)
//            )
//            OutlinedTextField(
//                shape = RoundedCornerShape(10),
//                value = password.value,
//                onValueChange = { password.change(it) },
//                leadingIcon = {
//                    Icon(Icons.Default.Lock, contentDescription = "password")
//                },
//                isError = password.hasError || loginError.value.isNullOrEmpty().not(),
//                supportingText = {
//                    if (password.hasError) {
//                        Text(text = password.errorMessage, color = Color.Red)
//                    }
//                    loginError.value?.let {
//                        Text(text = "Açar sözi ýa-da telefon belgi nädogry", color = Color.Red)
//                    }
//                },
//                label = {
//                    Text(text = "Açar sözi")
//                },
//                keyboardOptions = KeyboardOptions(
//                    imeAction = ImeAction.Done
//                ),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(0.dp, 20.dp, 0.dp, 0.dp),
//                visualTransformation = PasswordVisualTransformation()
//            )
//            Button(
//                colors = ButtonDefaults.buttonColors(disabledContainerColor = Color.Blue),
//                shape = RoundedCornerShape(15),
//                onClick = {
//                    loginError.value = null
//                    if (form.validate()) {
//                        loginViewModel.login(username.value, password.value) {
//                            loginError.value = it
//                        }
//                    }
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(0.dp, 20.dp, 0.dp, 20.dp)
//                    .height(50.dp)
//            ) {
//                if (state.value.loading) {
//                    CircularProgressIndicator(modifier = Modifier.size(35.dp), color = Color.White)
//                } else {
//                    Row(verticalAlignment = Alignment.CenterVertically){
//                        Text(
//                            text = "Içeri gir",
//                            textAlign = TextAlign.Center,
//                            fontSize = 18.sp
//                        )
//                        Spacer(modifier = Modifier.width(14.dp))
//                        Icon(Icons.AutoMirrored.Default.KeyboardArrowRight, contentDescription = null)
//                    }
//                }
//            }
//        }
//    }
//}


//
//Usage¶
//rememberPermissionState and rememberMultiplePermissionsState APIs¶
//
//The rememberPermissionState(permission: String) API allows you to request a certain permission to the user and check for the status of the permission. rememberMultiplePermissionsState(permissions: List<String>) offers the same but for multiple permissions at the same time.

//@OptIn(ExperimentalPermissionsApi::class)
//@Composable
//private fun FeatureThatRequiresCameraPermission() {
//
//    // Camera permission state
//    val cameraPermissionState = rememberPermissionState(
//        android.Manifest.permission.CAMERA
//    )
//
//    if (cameraPermissionState.status.isGranted) {
//        Text("Camera permission Granted")
//    } else {
//        Column {
//            val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
//                // If the user has denied the permission but the rationale can be shown,
//                // then gently explain why the app requires this permission
//                "The camera is important for this app. Please grant the permission."
//            } else {
//                // If it's the first time the user lands on this feature, or the user
//                // doesn't want to be asked again for this permission, explain that the
//                // permission is required
//                "Camera permission required for this feature to be available. " +
//                        "Please grant the permission"
//            }
//            Text(textToShow)
//            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
//                Text("Request permission")
//            }
//        }
//    }
//}