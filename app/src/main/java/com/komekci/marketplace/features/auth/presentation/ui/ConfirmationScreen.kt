package com.komekci.marketplace.features.auth.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.features.auth.presentation.state.AuthType
import com.komekci.marketplace.features.auth.presentation.viewmodel.CreateAccountViewModel
import com.komekci.marketplace.ui.app.LoadingButton
import com.komekci.marketplace.ui.theme.newTextColor
import com.komekci.marketplace.ui.theme.textColor

@Composable
fun ConfirmationScreen(
    viewModel: CreateAccountViewModel,
    nextIndex: Int = 3,
    previousIndex: Int = 1,
) {
    val strings = LocalStrings.current
    val code = viewModel.code.collectAsState()

    fun back() {
        viewModel.index.intValue = previousIndex
    }

    val checkCodeState = remember {
        viewModel.checkCodeState
    }

    val sendCodeState = remember {
        viewModel.sendCodeState
    }

    BackHandler(true) {
        back()
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Spacer(Modifier.height(33.dp))
        Text(
            strings.enterCode,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 15.sp,
            ),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = newTextColor
        )
        checkCodeState.value.error?.let {
            if (it.isNotEmpty()) {
                ErrorField(text = strings.enterCorrectCode)
            }
        }

//        Text(
//            text = strings.enterCode,
//            style = MaterialTheme.typography.bodyMedium.copy(
//                fontWeight = FontWeight.W500
//            ),
//            color = MaterialTheme.colorScheme.onSecondary
//        )

        OtpTextField(otpText = code.value, modifier = Modifier.fillMaxWidth()) { newValue, _ ->
            viewModel.updateCode(newValue)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {
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

            LoadingButton(
                loading = checkCodeState.value.loading,
                onClick = {
                    val id =
                        if (nextIndex == 2) viewModel.loginState.value.data?.userId else viewModel.registerState.value.response?.userId
                    id?.let {
                        viewModel.checkCode(
                            userId = it,
                            type = if(nextIndex == 2) AuthType.LOGIN else AuthType.REGISTER,
                        ) {
                            viewModel.index.intValue = nextIndex
                        }
                    }

                }, colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                enabled = code.value.length == 4 && sendCodeState.value.loading.not(),
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

        AlternativeText(
            text = strings.codeNotReceive,
            actionText = strings.sendAgain,
            loading = sendCodeState.value.loading
        ) {
            val id =
                if (nextIndex == 2) viewModel.loginState.value.data?.userId else viewModel.registerState.value.response?.userId
            viewModel.sendCode(id) {

            }
        }


    }
}

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 4,
    onOtpTextChange: (String, Boolean) -> Unit
) {

    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpCount) {
                onOtpTextChange.invoke(it.text, it.text.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Done
        ),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(otpCount) { index ->
                    CharView(
                        modifier = Modifier.weight(1f),
                        index = index,
                        text = otpText
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}

@Composable
private fun CharView(
    modifier: Modifier = Modifier,
    borderColor: Color = Color(0xFFD0D5DD),
    index: Int,
    text: String,
) {
    val isFocused = text.length == index
    val char = when {
        index == text.length -> ""
        index > text.length -> ""
        else -> text[index].toString()
    }
    Box(
        modifier
            .height(65.dp)
            .border(
                1.dp, when {
                    isFocused -> MaterialTheme.colorScheme.primary
                    else -> borderColor
                }, RoundedCornerShape(8.dp)
            )
            .padding(2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier,
            text = char,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.W500,
                fontSize = 20.sp
            ),
            color = if (isFocused) {
                MaterialTheme.colorScheme.primary
            } else {
                textColor
            },
            textAlign = TextAlign.Center
        )
    }

}
