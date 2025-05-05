package com.komekci.marketplace.features.auth.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.features.auth.presentation.viewmodel.CreateAccountViewModel
import com.komekci.marketplace.ui.theme.gray500

@Composable
fun UsernameScreen(
    viewModel: CreateAccountViewModel
) {
    val strings = LocalStrings.current
    val value = viewModel.username.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = strings.createAccount,
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.W700
            ),
            color = MaterialTheme.colorScheme.onSecondary
        )

        Text(
            text = strings.enterName,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.W400
            ),
            color = gray500
        )

        OutlinedTextField(
            value = value.value,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0x99008D1A)
            ),
            onValueChange = {
                viewModel.updateUsername(it)
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            placeholder = {
                Text(text = "...")
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400)
        )

        Button(
            onClick = {
                viewModel.index.intValue = 1
            }, colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
            ),
            enabled = value.value.length > 3,
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = strings.accept,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}