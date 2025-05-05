package com.komekci.marketplace.ui.app

import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AppCheckBox(
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    onChange: (Boolean) -> Unit = {},
) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        Checkbox(
            checked = checked,
            onCheckedChange = onChange,
            modifier = modifier,
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0x30019706),
                checkmarkColor = Color(0xFF297C3B),
                uncheckedColor = Color(0xFFD4D4D8)
            )
        )
    }
}