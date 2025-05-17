package com.example.lhythm.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lhythm.constants.Constants
import com.example.lhythm.presentation.ViewModels.OnBoardingViewModel

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,


    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)
val darkColorPallete =darkColorScheme(
    background = Color(0xFF000000),
    primary = Color(0xFFFF0B55),
    secondary = Color(0xFFFFFFFF),
    surfaceVariant = Color(0x13000000),
)

val lightColorPallete = lightColorScheme(
    background = Color(0xFF000000),
    primary = Color(0xFFFF0B55),
    secondary = Color(0xFFFFFFFF),
    surfaceVariant = Color(0x13000000)
)
val greenColorPallete = lightColorScheme(
    background = Color(0xFF000000),
    primary = Color(0xFF8BC34A),
    secondary = Color(0xFFFFFFFF),
    surfaceVariant = Color(0x13000000)
)
val blueColorPallete = lightColorScheme(
    background = Color(0xFF000000),
    primary = Color(0xFF03A9F4),
    secondary = Color(0xFFFFFFFF),
    surfaceVariant = Color(0x13000000)
)
val yellowColorPallete = lightColorScheme(
    background = Color(0xFF000000),
    primary = Color(0xFFFFEB3B),
    secondary = Color(0xFFFFFFFF),
    surfaceVariant = Color(0x13000000)
)
val purpleColorPallete = lightColorScheme(
    background = Color(0xFF000000),
    primary = Color(0xFFDF77EE),
    secondary = Color(0xFFFFFFFF),
    surfaceVariant = Color(0x13000000)
)
val pinkColorPallete = lightColorScheme(
    background = Color(0xFF000000),
    primary = Color(0xFFF35389),
    secondary = Color(0xFFFFFFFF)
)
val orangeColorPallete = lightColorScheme(
    background = Color(0xFF000000),
    primary = Color(0xFFF54E1B),
    secondary = Color(0xFFFFFFFF),
    surfaceVariant = Color(0x13000000)
)


@Composable
fun LhythmTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun LhythmCustomTheme(
    darktheme: Boolean = isSystemInDarkTheme(),
    themeViewModel:OnBoardingViewModel = hiltViewModel(),
    content: @Composable () -> Unit
){
    LaunchedEffect(Unit) {
        themeViewModel.getThemeSelection()
    }
    val themeState = themeViewModel.themeSelection.collectAsState()
    //val colourScheme = if(darktheme) darkColorPallete else lightColorPallete
    val colourScheme = when(themeState.value){
        Constants.REDTHEME -> darkColorPallete
        Constants.GREENTHEME -> greenColorPallete
        Constants.BLUETHEME -> blueColorPallete
        Constants.YELLOWTHEME -> yellowColorPallete
        Constants.PURPLETHEME -> purpleColorPallete
        Constants.PINKTHEME -> pinkColorPallete
        Constants.ORANGETHEME -> orangeColorPallete
        else -> darkColorPallete
    }
    MaterialTheme(
        colorScheme = colourScheme,
        typography = Typography,
        content = content
    )

}