package com.example.vivemurcia.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.vivemurcia.R



// Set of Material typography styles to start with
val Typography = Typography(

    displayLarge = TextStyle (
        fontFamily = FontFamily(Font(R.font.plusjakartasansmedium)),
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 0.sp,
        letterSpacing = 0.5.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp

    ),

    bodySmall = TextStyle (
        fontFamily = FontFamily(Font(R.font.plusjakartasansmedium)),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.5.sp),


)