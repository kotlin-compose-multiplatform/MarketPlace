package com.komekci.marketplace.features.create_store.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.ui.app.PhoneInputTextField
import com.komekci.marketplace.ui.app.PhoneNumberElement
import com.komekci.marketplace.ui.formbuilder.TextFieldState
import com.komekci.marketplace.ui.theme.newTextColor
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, device = "id:pixel_9")
@Composable
fun Payme(
    navHostController: NavHostController = rememberNavController()
) {
    val strings = LocalStrings.current
    val phone = rememberSaveable {
        mutableStateOf("+998")
    }
    Column(
        Modifier
            .fillMaxSize()
            .background(
                color = Color.White
            ), verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CenterAlignedTopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = {
                        navHostController.navigateUp()
                    }
                ) {
                    Icon(
                        Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "back",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            title = {
                Text(
                    text = "Payme",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W600
                    ),
                    color = Color(0xFF0F1E3C)
                )
            }
        )

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = 16.dp
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Payme platformadan töleg eden\n" +
                        "telafon belgisini giriziň",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 15.sp,
                ),
                modifier = Modifier.fillMaxWidth(0.7f),
                textAlign = TextAlign.Center,
                color = newTextColor
            )

            CupertinoInput(
                value = phone.value,
                onChange = {
                    phone.value = it
                },
                title = "Phone",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                )
            )



        }
    }
}

@Composable
fun CupertinoInput(
    modifier: Modifier = Modifier,
    value: String,
    onChange: (String) -> Unit,
    title: String,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions()
) {
    Row(
        modifier.background(
            color = Color(0xFF767680).copy(alpha = 0.12f),
            shape = RoundedCornerShape(16.dp)
        ).padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            title,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 17.sp,
            ),
            modifier = Modifier.weight(20f),
            color = newTextColor
        )

        BasicTextField(
            value = value,
            onValueChange = {
                onChange(it)
            },
            modifier = Modifier.weight(80f),
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 17.sp,
                color = newTextColor
            ),
            singleLine = singleLine,
            keyboardOptions = keyboardOptions
        )
    }
}

val phonePatterns = listOf(
    "+993",
    "+998",
    "+7",
)

val rightPatterns = listOf(
    PhoneNumberElement.FormatPatterns.TKM,
    PhoneNumberElement.FormatPatterns.UZB,
    PhoneNumberElement.FormatPatterns.KAZ,
)

@Composable
fun CupertinoPhoneInput(
    modifier: Modifier = Modifier,
    value: String,
    onChange: (String) -> Unit,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Phone
    ),
    interactionSource: MutableInteractionSource = MutableInteractionSource()
) {
    val expanded = remember {
        mutableStateOf(false)
    }
    val selectedPattern = rememberSaveable {
        mutableStateOf(phonePatterns[0])
    }
    val selectedRightPattern = rememberSaveable {
        mutableIntStateOf(0)
    }

//    val textFieldState = rememberTextFieldState(value.replaceFirst(selectedPattern.value, ""))

    Row(
        modifier.background(
            color = Color(0xFF767680).copy(alpha = 0.12f),
            shape = RoundedCornerShape(16.dp)
        ).padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            selectedPattern.value,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 17.sp,
            ),
            modifier = Modifier.weight(15f).clickable {
                expanded.value = true
            },
            color = newTextColor
        )
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = {
                expanded.value = false
            }
        ) {
            phonePatterns.forEachIndexed { index, s ->
                DropdownMenuItem(
                    text = {
                        Text(
                            s,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 17.sp,
                            ),
                            modifier = Modifier.weight(25f),
                            color = newTextColor
                        )
                    },
                    onClick = {
                        onChange("")
                        selectedRightPattern.intValue = index
                        selectedPattern.value = s
                        expanded.value = false
                    }
                )
            }

        }

        key(selectedRightPattern.intValue) {
            PhoneInputTextField(
                modifier = Modifier.weight(85f),
                config = rightPatterns[selectedRightPattern.intValue],
                onValueChange = { newValue ->
                    val nw = "${selectedPattern.value}$newValue"
                    onChange(nw)
                },
                value = value.replaceFirst(selectedPattern.value, ""),
            )
        }


//        Text(
//            "+993",
//            style = MaterialTheme.typography.bodyLarge.copy(
//                fontSize = 17.sp,
//            ),
//            modifier = Modifier.weight(15f),
//            color = newTextColor,
//            textAlign = TextAlign.End
//        )

//        BasicTextField(
//            value = value,
//            onValueChange = { v->
//                onChange(v)
//            },
//            modifier = Modifier.weight(75f),
//            textStyle = MaterialTheme.typography.bodyLarge.copy(
//                fontSize = 17.sp,
//                color = newTextColor
//            ),
//            singleLine = singleLine,
//            enabled = enabled,
//            keyboardOptions = keyboardOptions,
//            interactionSource = interactionSource
//        )
    }
}

