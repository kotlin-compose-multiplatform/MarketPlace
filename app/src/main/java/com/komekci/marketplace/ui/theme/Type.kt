package com.komekci.marketplace.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.komekci.marketplace.R

// Set of Material typography styles to start with


val interFontFamily = FontFamily(
    Font(R.font.roboto_thin, FontWeight.W100),
    Font(R.font.roboto_light, FontWeight.W200),
    Font(R.font.roboto_light, FontWeight.W300),
    Font(R.font.roboto_regular, FontWeight.W400),
    Font(R.font.roboto_medium, FontWeight.W500),
    Font(R.font.roboto_medium, FontWeight.W600),
    Font(R.font.roboto_bold, FontWeight.W700),
    Font(R.font.roboto_bold, FontWeight.W800),
    Font(R.font.roboto_black, FontWeight.W900)
)

val tekoFontFamily = FontFamily(
    Font(R.font.teko_light, FontWeight.W100),
    Font(R.font.teko_light, FontWeight.W200),
    Font(R.font.teko_light, FontWeight.W300),
    Font(R.font.teko_regular, FontWeight.W400),
    Font(R.font.teko_medium, FontWeight.W500),
    Font(R.font.teko_semibold, FontWeight.W600),
    Font(R.font.teko_bold, FontWeight.W700),
    Font(R.font.teko_bold, FontWeight.W800),
    Font(R.font.teko_bold, FontWeight.W900)
)


private val defaultTypography = Typography()
val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = interFontFamily),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = interFontFamily),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = interFontFamily),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = interFontFamily),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = interFontFamily),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = interFontFamily),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = interFontFamily),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = interFontFamily),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = interFontFamily),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = interFontFamily),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = interFontFamily),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = interFontFamily),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = interFontFamily),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = interFontFamily),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = interFontFamily)

)


