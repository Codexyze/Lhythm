package com.nutrino.lhythm.presentation.Utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.nutrino.lhythm.presentation.UIModels.WindowInfo
import com.nutrino.lhythm.presentation.UIModels.WindowType

@Composable
fun rememberWindowSize(): WindowInfo {
    val configuration = LocalConfiguration.current

    return WindowInfo(
        screenWidthType = when{
            configuration.screenWidthDp < 600 -> WindowType.COMPACT
            configuration.screenWidthDp < 840 -> WindowType.MEDIUM
            else -> WindowType.EXPANDED
        },
        screenHeightType = when{
            configuration.screenHeightDp < 480 -> WindowType.COMPACT
            configuration.screenHeightDp < 900 -> WindowType.MEDIUM
            else -> WindowType.EXPANDED
        },
        screenWidth = configuration.screenWidthDp.dp,
        screenHeight = configuration.screenHeightDp.dp
    )
}