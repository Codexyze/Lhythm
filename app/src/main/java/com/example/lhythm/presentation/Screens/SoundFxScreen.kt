package com.example.lhythm.presentation.Screens

import android.R.attr.maxLevel
import android.R.attr.minLevel
import android.media.audiofx.Equalizer
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import com.example.lhythm.presentation.ViewModels.MediaManagerViewModel

@OptIn(UnstableApi::class)
@Composable
fun SoundFXScreen(
    navController: NavController,
    mediaManagerViewModel: MediaManagerViewModel = hiltViewModel()
) {
    val exoplayer = mediaManagerViewModel.getExoplayer()

    // Scaffold sets up the main layout structure
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Box(modifier = Modifier.padding(it)) {

            // Create the equalizer for the ExoPlayer's audio session
            val equalizer = remember(exoplayer?.audioSessionId) {
                exoplayer?.audioSessionId?.let { sessionId ->
                    Equalizer(0, sessionId).apply { enabled = true }
                }
            }

            // Only show UI if equalizer is initialized
            equalizer?.let { eq ->
                val numberOfBands = eq.numberOfBands.toInt()
                val lowerBandLevel = eq.bandLevelRange[0].toInt()
                val upperBandLevel = eq.bandLevelRange[1].toInt()

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "🎧 Equalizer Settings",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.secondary

                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // For each band, create a slider
                    for (i in 0 until numberOfBands) {
                        val band = i.toShort()
                        val centerFreq = eq.getCenterFreq(band) / 1000 // Convert to Hz
                        var level by remember { mutableStateOf(eq.getBandLevel(band).toInt()) }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Text(
                                text = "Band ${i + 1} - ${centerFreq} Hz",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.secondary
                            )

                            Slider(
                                value = level.toFloat(),
                                onValueChange = {
                                    level = it.toInt()
                                    eq.setBandLevel(band, level.toShort())
                                },
                                valueRange = lowerBandLevel.toFloat()..upperBandLevel.toFloat(),
                                steps = 5,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
                            )
                        }
                    }
                }
            } ?: run {
                // Show fallback if Equalizer is null
                Text(
                    text = "⏳Error",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
