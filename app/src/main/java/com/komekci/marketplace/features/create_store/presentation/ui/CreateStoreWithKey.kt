package com.komekci.marketplace.features.create_store.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.komekci.marketplace.features.create_store.data.entity.store.CreateStoreBody
import com.komekci.marketplace.features.create_store.presentation.viewmodel.StoreViewModel
import com.komekci.marketplace.ui.app.LoadingButton

@Preview()
@Composable
fun CreateStoreWithKey(
    storeViewModel: StoreViewModel = hiltViewModel(),
    onBack: () -> Unit = {},
    goBack: Boolean = false,
    type: String = "NEW"
) {
    val strings = LocalStrings.current


    val code = rememberSaveable {
        mutableStateOf("")
    }

    val state = remember {
        storeViewModel.createStoreState
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .consumeWindowInsets(
                WindowInsets.systemBars.only(WindowInsetsSides.Vertical)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.height(32.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.logo_full),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(40.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = strings.pay,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W700
                    ),
                    color = Color(0xFF2F313F)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = strings.createStore,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400),
                    color = Color(0xFF667085)
                )


                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = code.value,
                    onValueChange = { code.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .imePadding(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0x1C000030)
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text
                    ),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.password),
                            contentDescription = null,
                            tint = Color(0x9E000713)
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Key...",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W600),
                            color = Color(0x7500041D)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(22.dp))
                LoadingButton(
                    loading = state.value.loading,
                    onClick = {
                        storeViewModel.createStore(type = type, CreateStoreBody(key = code.value, paymentMethod = "key")) {
                            if(goBack) {
                                onBack()
                            }
                        }
                    },
                    enabled = code.value.length > 4,
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.fillMaxWidth()
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
                Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.ime))
            }
        }
    }
}