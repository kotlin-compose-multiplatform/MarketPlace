package com.komekci.marketplace.features.auth.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.R
import com.komekci.marketplace.features.auth.data.entity.Message
import com.komekci.marketplace.features.auth.data.entity.country.AllCountryEntityItem
import com.komekci.marketplace.features.auth.presentation.viewmodel.CreateAccountViewModel
import com.komekci.marketplace.features.create_store.presentation.ui.CupertinoInput
import com.komekci.marketplace.features.create_store.presentation.ui.CupertinoPhoneInput
import com.komekci.marketplace.state.LocalSettings
import com.komekci.marketplace.ui.app.LoadingButton
import com.komekci.marketplace.ui.formbuilder.FormState
import com.komekci.marketplace.ui.formbuilder.TextFieldState
import com.komekci.marketplace.ui.formbuilder.Validators
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Local

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneNumberScreen(
    viewModel: CreateAccountViewModel,
    title: String,
    alternativeText: String,
    actionText: String,
    handleBack: Boolean = false,
    nextIndex: Int = 2,
    previousIndex: Int = 0,
    countries: List<AllCountryEntityItem> = emptyList(),
    onBackClick: () -> Unit = {},
    onActionClick: () -> Unit = {}
) {
    val strings = LocalStrings.current
    val phone = viewModel.phoneNumber.collectAsState()
    val error = remember {
        mutableStateOf(false)
    }
    val registerState = remember {
        viewModel.registerState
    }

    val loginState = remember {
        viewModel.loginState
    }

    fun register() {
        viewModel.register {
            viewModel.index.intValue = nextIndex
        }
    }

    fun login() {
        viewModel.login {
            viewModel.index.intValue = nextIndex
        }
    }

    fun back() {
        if(handleBack){
            viewModel.index.intValue = previousIndex
        } else {
            onBackClick()
        }
    }

    val formState = FormState(
        fields = listOf(
            TextFieldState(
                name = "phone",
                transform = { it.trim().lowercase() },
                validators = listOf(
                    Validators.Required(strings.enterPhone),
                ),
            )

            )
    )
    val form = remember { formState }

    val formPhone: TextFieldState = form.getState("phone")

    BackHandler(handleBack) {
        back()
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

//        Text(
//            text = title,
//            style = MaterialTheme.typography.titleLarge.copy(
//                fontSize = 20.sp,
//                fontWeight = FontWeight.W700
//            ),
//            color = MaterialTheme.colorScheme.onSecondary
//        )

        if (formPhone.hasError) {
            ErrorField(text = strings.enterPhone)
        }

        registerState.value.error?.let { 
            if(it.isNotEmpty() && nextIndex==2) {
                ErrorField(text = it, errorCode = 500)
            }
        }

        registerState.value.message?.let {messages->
            if(messages.isNotEmpty() && nextIndex==2) {
                ErrorField(message = messages)
            }
        }

        loginState.value.error?.let {
            if(it.isNotEmpty() && nextIndex==1) {
                ErrorField(text = it, errorCode = 500)
            }
        }

        loginState.value.message?.let {messages->
            if(messages.isNotEmpty() && nextIndex==1) {
                ErrorField(message = messages)
            }
        }
        val username = viewModel.username.collectAsState()



        if(nextIndex==2) {
            CupertinoInput(
                value = username.value,
                onChange = {
                    viewModel.updateUsername(it)
                },
                title = strings.name,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth(),
            )
//            OutlinedTextField(
//                value = username.value,
//                colors = OutlinedTextFieldDefaults.colors(
//                    focusedBorderColor = Color(0x99008D1A)
//                ),
//                onValueChange = {
//                    viewModel.updateUsername(it)
//                },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(
//                    imeAction = ImeAction.Next
//                ),
//                placeholder = {
//                    Text(text = "...")
//                },
//                modifier = Modifier.fillMaxWidth(),
//                textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
//            )
        }

        if(nextIndex!=2) {
            Text(
                text = strings.enterPhone,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.W500
                ),
                color = MaterialTheme.colorScheme.onSecondary
            )

        }

        PhoneNumberInput(
            defaultValue = phone.value,
            enabled = if(nextIndex==2) registerState.value.loading.not() else loginState.value.loading.not(),
            onFocusChange = {
                error.value = !it && phone.value.length != 8
            }
        ) {
            formPhone.change(it)
            viewModel.updatePhoneNumber(it)
        }

        if(countries.isNotEmpty()) {
            TreeSelect(
                countries = countries,
                onCountrySelect = { c->
                    viewModel.updateSelectedCountryId(c.id)
                },
                onRegionSelect = { r->
                    viewModel.updateSelectedRegion(r.id)
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {
                    onBackClick()
                    back()
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF2F2F5)
                ),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = strings.back,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                    color = Color(0xFF60646C)
                )
            }

            if(nextIndex==2) {
                LoadingButton(
                    loading = registerState.value.loading,
                    onClick = {
                        formState.validate()
                        register()
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    enabled = phone.value.isNotEmpty(),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = strings.continueButton,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            } else {
                LoadingButton(
                    loading = loginState.value.loading,
                    onClick = {
                        formState.validate()
                        login()
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    enabled = phone.value.isNotEmpty(),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = strings.continueButton,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }

        AlternativeText(text = alternativeText, actionText = actionText) {
            onActionClick()
        }


    }
}

@Composable
fun PhoneNumberInput(
    defaultValue: String = "",
    enabled: Boolean = true,
    onFocusChange: (Boolean) -> Unit = {},
    onValueChange: (String) -> Unit = {},
) {
    val phone = remember {
        mutableStateOf(defaultValue)
    }

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    LaunchedEffect(isFocused) {
        onFocusChange(isFocused)
    }

    CupertinoPhoneInput(
        value = phone.value,
        enabled = true,
        onChange = {
            onValueChange(it)
//            val correctedValue = applyAutoCorrection(it.text)
            phone.value = it
        },
        interactionSource = interactionSource
    )


//    OutlinedTextField(
//        value = phone.value,
//        colors = OutlinedTextFieldDefaults.colors(
//            focusedBorderColor = Color(0x99008D1A)
//        ),
//        interactionSource = interactionSource,
//        onValueChange = {
//            onValueChange(it.text.replace(Regex("[^0-9]"), ""))
//            val correctedValue = applyAutoCorrection(it.text)
//            phone.value = TextFieldValue(
//                text = correctedValue,
//                selection = TextRange(correctedValue.length)
//            )
//        },
//        enabled = enabled,
//        singleLine = true,
//        keyboardOptions = KeyboardOptions(
//            imeAction = ImeAction.Done,
//            keyboardType = KeyboardType.Phone
//        ),
//        prefix = {
//            Text(
//                text = "+993",
//                color = Color(0xFF1C2024),
//                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
//            )
//        },
//        placeholder = {
//            Text(
//                text = "(_ _) _ _ - _ _ - _ _",
//                color = Color(0xFF7E808A),
//                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
//            )
//        },
//        modifier = Modifier.fillMaxWidth(),
//        textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
//    )
}

fun applyAutoCorrection(input: String): String {
    // Implement your auto-correction logic here
    // For example, you can remove non-numeric characters
    val newValue = input.replace(Regex("[^0-9]"), "")

    // Format the phone number according to the desired structure
    val formattedValue = buildString {
        if (newValue.length > 0) append("(${newValue.take(2)})") // Area code
        if (newValue.length > 2) append(" ${newValue.drop(2).take(2)}") // First part
        if (newValue.length > 4) append(" - ${newValue.drop(4).take(2)}") // Second part
        if (newValue.length > 6) append(" - ${newValue.drop(6).take(2)}") // Third part
    }

    return formattedValue
}


@Composable
fun ErrorField(
    modifier: Modifier = Modifier,
    text: String = LocalStrings.current.somethingWentWrong,
    message: List<Message>? = null,
    errorCode: Int? = null,
) {
    val strings = LocalStrings.current
    val codeError = if(errorCode!=null) {
        if(errorCode>=500)
            strings.serverError
        else if(errorCode>=400)
            strings.clientError
        else if(errorCode>=300)
            strings.warning
        else text
    } else {
        if(message.isNullOrEmpty().not()) {
            message?.joinToString { it.tm }
        } else text
    }
    Row(
        modifier
            .fillMaxWidth()
            .background(Color(0x0FFF0101), shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_info_outline_24),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error
        )

        Text(
            text = codeError?:strings.somethingWentWrong,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400),
            color = MaterialTheme.colorScheme.error
        )
    }
}


@Composable
fun AlternativeText(
    text: String,
    actionText: String,
    loading: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400),
            color = Color(0xFF667085)
        )
        Spacer(modifier = Modifier.width(12.dp))
        if(loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(35.dp),
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            Text(
                text = actionText,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400),
                color = Color(
                    0xC2007A19
                ),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    onClick()
                }
            )
        }
    }
}
