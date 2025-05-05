package com.komekci.marketplace.ui.app

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.komekci.marketplace.features.create_store.presentation.ui.CupertinoPhoneInput
import com.komekci.marketplace.ui.theme.newTextColor

@Preview(showSystemUi = true)
@Composable
fun PhonePreview(modifier: Modifier = Modifier) {
    val phone = remember {
        mutableStateOf(
            TextFieldValue(
                text = "",
                selection = TextRange(0)
            )
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        val patterns = listOf(
            PhoneNumberElement.FormatPatterns.TKM,
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            patterns.forEach {


//                CupertinoPhoneInput(
//                    value = phone.value,
//                    onChange = { newValue->
//                        phone.value = newValue
//                    },
//                )

            }
        }
    }
}




@Composable
fun PhoneInputTextField(
    modifier: Modifier = Modifier,
    config: List<PhoneNumberElement>,
    onValueChange: (String) -> Unit,
    value: String,
) {
    val editableDigitCount = remember {
        config.filterIsInstance<PhoneNumberElement.EditableDigit>()
    }
    BasicTextField(
        modifier = modifier,
        value = value,
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            fontSize = 17.sp,
            color = newTextColor
        ),
        onValueChange = { newValue ->
            if (newValue.length <= editableDigitCount.size) {
                onValueChange(newValue)
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
        ),
        decorationBox = {
            val digitIndexMap = remember(value) {
                var start = 0
                config.mapIndexedNotNull { index, it ->
                    if (it is PhoneNumberElement.EditableDigit) {
                        index to start++
                    } else null
                }.toMap()
            }
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                config.forEachIndexed { index, phoneNumberElement ->
                    when (phoneNumberElement) {
                        PhoneNumberElement.EditableDigit -> {
                            digitIndexMap[index]?.let { digitIndex ->
                                EditableDigit(
                                    text = value.getOrNull(digitIndex)?.toString(),
                                )
                            }
                        }

                        is PhoneNumberElement.Mask -> {
                            Mask(
                                text = phoneNumberElement.text,
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun Mask(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.bodyLarge.copy(
            fontSize = 17.sp,
        ),
        color = newTextColor
    )
}

@Composable
fun EditableDigit(
    modifier: Modifier = Modifier,
    text: String?,
) {
    Column(
        modifier = modifier
            .width(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AnimatedContent(
            targetState = text,
            transitionSpec = {
                (slideInVertically() + fadeIn())
                    .togetherWith(slideOutVertically() + fadeOut())

            }, label = "animation"
        ) { text ->
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = text ?: " ",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp,
                ),
                color = newTextColor
            )
        }

        HorizontalDivider(
            thickness = 2.dp,
            color = Color.LightGray
        )
    }
}


sealed interface PhoneNumberElement {
    data class Mask(val text: String) : PhoneNumberElement

    object EditableDigit : PhoneNumberElement

    object FormatPatterns {
        /**
         * The phone number pattern for Russia. The format includes the country code +7, followed by a 10-digit number
         * structured as "+7 (XXX) XXX-XX-XX".
         */
        val RUS by lazy {
            listOf(
                Mask("+7"),
                Mask("("),
                EditableDigit,
                EditableDigit,
                EditableDigit,
                Mask(")"),
                EditableDigit,
                EditableDigit,
                EditableDigit,
                Mask("-"),
                EditableDigit,
                EditableDigit,
                Mask("-"),
                EditableDigit,
                EditableDigit,
            )
        }

        /**
         * The phone number pattern for the United States. The format includes the country code +1, followed by a 10-digit number
         * structured as "+1 (XXX) XXX-XXXX".
         */
        val USA by lazy {
            listOf(
                Mask("+1"),
                Mask("("),
                EditableDigit,
                EditableDigit,
                EditableDigit,
                Mask(")"),
                EditableDigit,
                EditableDigit,
                EditableDigit,
                Mask("-"),
                EditableDigit,
                EditableDigit,
                EditableDigit,
                EditableDigit,
            )
        }

        /**
         * The phone number pattern for France. The format includes the country code +33, followed by a 9-digit number
         * structured as "+33 X XX XX XX XX".
         */
        val FRA by lazy {
            listOf(
                Mask("+33"),
                Mask(" "),
                EditableDigit,
                Mask(" "),
                EditableDigit,
                EditableDigit,
                Mask(" "),
                EditableDigit,
                EditableDigit,
                Mask(" "),
                EditableDigit,
                EditableDigit,
                Mask(" "),
                EditableDigit,
                EditableDigit
            )
        }

        /**
         * +993 XX XX XX XX
         */

        val TKM by lazy {
            listOf(
                Mask("("),
                EditableDigit,
                EditableDigit,
                Mask(")"),
                EditableDigit,
                EditableDigit,
                Mask("-"),
                EditableDigit,
                EditableDigit,
                Mask("-"),
                EditableDigit,
                EditableDigit
            )
        }


        val UZB by lazy {
            listOf(
                Mask("("),
                EditableDigit,
                EditableDigit,
                Mask(")"),
                EditableDigit,
                EditableDigit,
                EditableDigit,
                Mask("-"),
                EditableDigit,
                EditableDigit,
                Mask("-"),
                EditableDigit,
                EditableDigit,
            )
        }

        val KAZ by lazy {
            listOf(
                Mask("("),
                EditableDigit,
                EditableDigit,
                EditableDigit,
                Mask(")"),
                EditableDigit,
                EditableDigit,
                EditableDigit,
                Mask("-"),
                EditableDigit,
                EditableDigit,
                Mask("-"),
                EditableDigit,
                EditableDigit,
            )
        }
    }
}