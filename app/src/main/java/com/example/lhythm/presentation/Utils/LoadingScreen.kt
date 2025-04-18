package com.example.lhythm.presentation.Utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.lhythm.R
import com.example.lhythm.ui.theme.BlackColor
import com.shashank.sony.fancytoastlib.FancyToast

@Composable
fun LoadingScreen() {
    val context = LocalContext.current
    FancyToast.makeText(
        context, "Loading Songs",
        FancyToast.LENGTH_SHORT,
        FancyToast.CONFUSING, false
    ).show()
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    Column(modifier = Modifier.fillMaxSize().background(color = BlackColor), verticalArrangement = Arrangement.Center
    , horizontalAlignment = Alignment.CenterHorizontally) {
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(R.raw.musicanimination)
        )
        val progress by animateLottieCompositionAsState(composition = composition,
            iterations = LottieConstants.IterateForever, isPlaying = true
        )

        LottieAnimation(
            composition = composition,
            progress = {progress},
            modifier = Modifier.fillMaxWidth()
                .height(screenHeight*0.3f)
        )

    }

}

