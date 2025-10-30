package com.nutrino.lhythm.presentation.UIModels

import androidx.compose.ui.unit.Dp

data class WindowInfo(
    val screenWidthType: WindowType,
    val screenHeightType: WindowType,
    val screenWidth:Dp,
    val screenHeight:Dp
)
