package com.example.lhythm.presentation.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lhythm.presentation.ViewModels.MediaManagerViewModel
import com.example.lhythm.R
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.delay


@Composable
fun SongControllerScreen(
    viewModel: MediaManagerViewModel = hiltViewModel()
) {
    val context= LocalContext.current
    val currentPosition = viewModel.currentSongPositionState.collectAsState()
    val iconClicked = rememberSaveable { mutableStateOf(false) }
    val duration = viewModel.getDuration()?.toFloat() ?: 1f // Avoid division by zero
    val isPlaying = viewModel.isplayingState.collectAsState()

    LaunchedEffect(Unit) {
        if(viewModel.isPlaying()) {
            while (true) {
                viewModel.getCurrentPosition() // This updates the StateFlow
                delay(1000) // update every second (can fine-tune)
            }
        }
    }
    if(viewModel.isPlaying()){
        Scaffold {
            Box(modifier = Modifier.padding(it)) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.lythmlogoasset),
                        contentDescription = "music",
                        modifier = Modifier.fillMaxSize(0.75f)
                    )

                    Text("Now Playing")

                    Spacer(modifier = Modifier.height(16.dp))

                    // 🎚️ SeekBar (Slider)
                    Slider(
                        value = currentPosition.value.coerceAtMost(duration), // Just in case pos > dur
                        onValueChange = { viewModel.getExoplayer()?.seekTo(it.toLong()) },
                        valueRange = 0f..duration,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {

                        IconButton(onClick = {
                            val player = viewModel.getExoplayer()
                            if (player != null) {
                                if (isPlaying.value) {
                                    viewModel.pauseMusic()
                                } else {
                                    viewModel.playMusic()
                                }
                            } else {
                                FancyToast.makeText(
                                    context, "No Songs Played",
                                    FancyToast.LENGTH_SHORT,
                                    FancyToast.WARNING, false
                                ).show()
                            }
                        }) {
                            Icon(
                                imageVector = if (isPlaying.value) Icons.Default.Pause else Icons.Default.PlayCircle,
                                contentDescription = "Toggle Play/Pause"
                            )
                        }

                    }
                }
            }
        }
    }else{
        NoSongsFoundScreen()
    }



}
