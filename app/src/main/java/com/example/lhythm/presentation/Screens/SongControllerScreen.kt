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
    playlistViewModel : PlayListViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        if (viewModel.isPlaying()) {
            while (true) {
                viewModel.getCurrentPosition()
                delay(1000)
            }
        }
    }


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
//@OptIn(UnstableApi::class)
//@Composable
//fun SongControllerScreen(
//    viewModel: MediaManagerViewModel = hiltViewModel(),
//    playlistViewModel: PlayListViewModel = hiltViewModel(),
//) {
//    // Remembered mutable state for equalizer
//    var equalizer by remember { mutableStateOf<Equalizer?>(null) }
//
//    // Track position if playing
//    LaunchedEffect(Unit) {
//        if (viewModel.isPlaying()) {
//            while (true) {
//                viewModel.getCurrentPosition()
//                delay(1000)
//            }
//        }
//    }
//
//    // Initialize equalizer when audio session ID changes or is available
//    LaunchedEffect(viewModel.getExoplayer()?.audioSessionId) {
//        val audioSessionId = viewModel.getExoplayer()?.audioSessionId
//        if (audioSessionId != null) {
//            Log.d("Equalizer", "Audio Session ID: $audioSessionId")
//            equalizer?.release()  // Release previous equalizer if any
//            equalizer = Equalizer(0, audioSessionId).apply {
//                enabled = true
//            }
//        }
//    }
//
//    Column(modifier = Modifier.fillMaxSize()) {
//        // PlayerView
//        Column(modifier = Modifier.fillMaxSize(1f)) {
//            AndroidView(
//                factory = {
//                    PlayerView(it).apply {
//                        this.player = viewModel.getExoplayer()
//                        this.useController = true
//                        this.setShowNextButton(false)
//                        this.setShowPreviousButton(false)
//                        this.setShowNextButton(true)
//                        this.setShowPreviousButton(true)
//                        this.subtitleView
//                    }
//                },
//               // modifier = Modifier.fillMaxSize(1f)
//            )
//        }
//
//        // Equalizer Controls
//        Column(modifier = Modifier.fillMaxSize(1f)) {
//            val audiosessionID = viewModel.exoplayeInstance()?.audioSessionId
//            Log.d("Equalizer", "Audio session ID: $audiosessionID")
//
//                equalizer?.let { eq ->
//                    Log.d("Equalizer", "Equalizer initialized w: $eq")
//                    val bandCount = eq.numberOfBands.toInt()
//                    val minLevel = eq.bandLevelRange[0].toInt()
//                    val maxLevel = eq.bandLevelRange[1].toInt()
//
//                    Column(modifier = Modifier.padding(16.dp)) {
//                        Text("🎧 Equalizer Settings", style = MaterialTheme.typography.headlineSmall)
//                        Spacer(modifier = Modifier.height(16.dp))
//
//                        // Display band settings
//                        for (i in 0 until bandCount) {
//                            val band = i.toShort()
//                            val centerFreq = eq.getCenterFreq(band) / 1000 // Hz
//                            var level by remember { mutableStateOf(eq.getBandLevel(band).toInt()) }
//
//                            Text("Band ${i + 1} (${centerFreq}Hz)")
//                            Slider(
//                                value = level.toFloat(),
//                                onValueChange = {
//                                    level = it.toInt()
//                                    eq.setBandLevel(band, level.toShort())
//                                },
//                                valueRange = minLevel.toFloat()..maxLevel.toFloat(),
//                                steps = 5,
//                                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).background(color = WhiteColor)
//                            )
//                        }
//                    }
//                } ?: run {
//                    Text("⏳ Initializing Equalizer...", modifier = Modifier.padding(16.dp))
//                }
//            }
//        }
//    }

//@OptIn(UnstableApi::class)
//@Composable
//fun SongControllerScreen(
//    viewModel: MediaManagerViewModel = hiltViewModel(),
//    playlistViewModel: PlayListViewModel = hiltViewModel(),
//) {
//    // Remembered mutable state for equalizer
//    var equalizer by remember { mutableStateOf<Equalizer?>(null) }
//
//    // Track position if playing
//    LaunchedEffect(Unit) {
//        if (viewModel.isPlaying()) {
//            while (true) {
//                viewModel.getCurrentPosition()
//                delay(1000)
//            }
//        }
//    }
//
//    // Initialize equalizer when audio session ID changes or is available
//    LaunchedEffect(viewModel.getExoplayer()?.audioSessionId) {
//        val audioSessionId = viewModel.getExoplayer()?.audioSessionId
//        if (audioSessionId != null) {
//            Log.d("Equalizer", "Audio Session ID: $audioSessionId")
//            equalizer?.release()  // Release previous equalizer if any
//            equalizer = Equalizer(0, audioSessionId).apply {
//                enabled = true
//            }
//        }
//    }
//
//    Column(modifier = Modifier.fillMaxSize()) {
//        // PlayerView
////        Box(modifier = Modifier.fillMaxSize(1f)) {
////            AndroidView(
////                factory = {
////                    PlayerView(it).apply {
////                        this.player = viewModel.getExoplayer()
////                        this.useController = true
////                        this.setShowNextButton(false)
////                        this.setShowPreviousButton(false)
////                        this.setShowNextButton(true)
////                        this.setShowPreviousButton(true)
////                        this.subtitleView
////                    }
////                },
////                modifier = Modifier.fillMaxSize(1f)
////            )
////        }
//
//        // Equalizer Controls
//        Box(modifier = Modifier.fillMaxSize()) {
//            val audiosessionID = viewModel.exoplayeInstance()?.audioSessionId
//            Log.d("Equalizer", "Audio session ID: $audiosessionID")
//
//            equalizer?.let { eq ->
//                Log.d("Equalizer", "Equalizer initialized w: $eq")
//                val bandCount = eq.numberOfBands.toInt()
//                val minLevel = eq.bandLevelRange[0].toInt()
//                val maxLevel = eq.bandLevelRange[1].toInt()
//
//                Column(modifier = Modifier
//                    .align(Alignment.BottomStart) // Ensures equalizer controls are at the bottom
//                    .padding(16.dp)) {
//                    Text("🎧 Equalizer Settings", style = MaterialTheme.typography.headlineSmall)
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    // Display band settings
//                    for (i in 0 until bandCount) {
//                        val band = i.toShort()
//                        val centerFreq = eq.getCenterFreq(band) / 1000 // Hz
//                        var level by remember { mutableStateOf(eq.getBandLevel(band).toInt()) }
//
//                        Text("Band ${i + 1} (${centerFreq}Hz)")
//                        Slider(
//                            value = level.toFloat(),
//                            onValueChange = {
//                                level = it.toInt()
//                                eq.setBandLevel(band, level.toShort())
//                            },
//                            valueRange = minLevel.toFloat()..maxLevel.toFloat(),
//                            steps = 5,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(vertical = 8.dp)
//                                .height(40.dp) // Set height to ensure it's visible
//                                .background(color = Color.Gray) // Added background for visibility
//                        )
//                    }
//                }
//            } ?: run {
//                Text("⏳ Initializing Equalizer...", modifier = Modifier.padding(16.dp))
//            }
//        }
//    }
//}
