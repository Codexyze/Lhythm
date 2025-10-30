package com.nutrino.lhythm.presentation.Screens

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import com.nutrino.lhythm.presentation.ViewModels.MediaManagerViewModel


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

