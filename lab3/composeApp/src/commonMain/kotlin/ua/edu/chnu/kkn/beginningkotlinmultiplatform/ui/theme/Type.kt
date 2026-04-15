package ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

fun getTypography(font1: Font, font2: Font? = null): Typography {
    val bodyFontFamily = FontFamily(font1)
    val displayFontFamily = FontFamily(font2 ?: font1)

    return Typography(
        displayLarge = TextStyle(
            fontFamily = displayFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 48.sp,
            lineHeight = 56.sp
        ),
        headlineMedium = TextStyle(
            fontFamily = displayFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp,
            lineHeight = 36.sp
        ),
        titleLarge = TextStyle(
            fontFamily = displayFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 22.sp,
            lineHeight = 28.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = bodyFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        labelMedium = TextStyle(
            fontFamily = bodyFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp
        )
    )
}
