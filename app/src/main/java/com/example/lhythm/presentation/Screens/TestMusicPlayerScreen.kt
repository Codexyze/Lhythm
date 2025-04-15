package com.example.lhythm.presentation.Screens

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lhythm.presentation.ViewModels.MediaManagerViewModel

@Composable
fun SongPlayerScreen(
    uri: Uri,
    viewModel: MediaManagerViewModel = hiltViewModel()
) {
    Scaffold {
        Box(modifier = Modifier.padding(it)) {
            // UI content like Play/Pause buttons
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Now Playing")
                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    // Play/Pause button
                    Button(onClick = {
                        if (!viewModel.isPlaying()) {
                            viewModel.playMusic(uri) // Start playing the music if not already playing
                        } else {
                            viewModel.pauseMusic() // Pause if already playing
                        }
                    }) {
                        Text(if (viewModel.isPlaying()) "⏸ Pause" else "▶️ Play")
                    }

                    // Stop button
                    Button(
                        onClick = {
                            viewModel.stop() // Stop the music
                            viewModel.releasePlayerResources() // Release resources
                        }
                    ) {
                        Text("Stop")
                    }
                }
            }
        }
    }
}
