package com.example.lhythm.presentation.Screens

import android.media.audiofx.Equalizer
import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import com.example.lhythm.presentation.ViewModels.MediaManagerViewModel
import com.example.lhythm.presentation.ViewModels.PlayListViewModel
import com.example.lhythm.ui.theme.WhiteColor
import kotlinx.coroutines.delay


@OptIn(UnstableApi::class)
@Composable
fun SongControllerScreen(
    viewModel: MediaManagerViewModel = hiltViewModel(),
) {



    Column (modifier = Modifier.fillMaxSize()){
       // Log.d("Equalizer","in compisable ${viewModel.equalizer.collectAsState().value.toString()}")
        Column(modifier = Modifier.fillMaxSize(1f)) {
            AndroidView(
                factory = {
                    PlayerView(it).apply {
                        this.player = viewModel.getExoplayer()
                        this.useController = true
                        this.setShowNextButton(false)
                        this.setShowPreviousButton(false)
                        this.setShowNextButton(true)
                        this.setShowPreviousButton(true)
                        this.subtitleView
                    }
                },
                modifier = Modifier.fillMaxSize(1f)
            )
        }

    }

}

