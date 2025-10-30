package com.nutrino.lhythm.presentation.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.nutrino.lhythm.R
import com.nutrino.lhythm.ui.theme.BlackColor

@Preview(showBackground = true)
@Composable
fun NoSongsFoundScreen() {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.nosongsfound)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = true,
        iterations = LottieConstants.IterateForever
    )
    Column(modifier = Modifier.fillMaxSize().background(color = BlackColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Spacer(modifier = Modifier.size(26.dp))
        Text("No songs Found")
        LottieAnimation(
            composition = composition,
            progress = {
                progress
            },
            modifier = Modifier.fillMaxWidth(0.9f)
        )

    }

}