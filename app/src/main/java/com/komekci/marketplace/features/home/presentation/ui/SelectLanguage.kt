package com.komekci.marketplace.features.home.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.komekci.marketplace.R
import com.komekci.marketplace.core.locale.Locales
import com.komekci.marketplace.state.LocalSettings
import com.komekci.marketplace.state.SetAppSettings

@Composable
fun SelectLanguage(modifier: Modifier = Modifier, open: Boolean, onDismiss: () -> Unit) {
    if (open) {
        val changeLanguage = SetAppSettings.current
        val settings = LocalSettings.current
        Dialog(onDismissRequest = onDismiss) {
            Column(
                modifier = modifier
                    .background(
                        Color(0xFFFFFFFF),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp)
                    .wrapContentSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.globe),
                    contentDescription = null,
                    tint = Color(0xFF60646C)
                )
                HorizontalDivider(modifier = Modifier.width(130.dp))
                
                LanguageButton(text = "Русский") {
                    changeLanguage(settings.copy(languageTag = Locales.RU))
                    onDismiss()
                }
                LanguageButton(text = "English") {
                    changeLanguage(settings.copy(languageTag = Locales.EN))
                    onDismiss()
                }

                LanguageButton(text = "Türkmençe") {
                    changeLanguage(settings.copy(languageTag = Locales.TK))
                    onDismiss()
                }

                LanguageButton(text = "Uzbek") {
                    changeLanguage(settings.copy(languageTag = Locales.UZ))
                    onDismiss()
                }

            }
        }
    }
}

@Composable
fun LanguageButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    TextButton(onClick = onClick, modifier = modifier) {
        Text(
            text = text,
            color = Color(0xFF1C2024),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400)
        )
    }
}
