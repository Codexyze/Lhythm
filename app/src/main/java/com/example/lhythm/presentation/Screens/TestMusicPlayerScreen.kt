package com.example.lhythm.presentation.Screens

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import com.example.lhythm.presentation.ViewModels.MediaManagerViewModel
@Composable
fun SongPlayerScreen(
    uri: Uri,
    viewModel: MediaManagerViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    // Remember the current playback state using rememberSaveable to persist across rotations
    val isMusicPlaying = rememberSaveable { mutableStateOf(false) }

    // Only call playMusic() when the uri changes, and prevent re-playing on rotation
    LaunchedEffect(uri) {
        // If music isn't already playing, play it
        if (!isMusicPlaying.value) {
            viewModel.playMusic(uri = uri)
            isMusicPlaying.value = true
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Now Playing")
        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(onClick = {
                // Ensure music isn't replayed on rotation
                if (!isMusicPlaying.value) {
                    viewModel.playMusic()
                    isMusicPlaying.value = true
                }
            }) {
                Text("▶️ Play")
            }

            Button(onClick = {
                viewModel.pauseMusic()
                isMusicPlaying.value = false
            }) {
                Text("⏸ Pause")
            }
        }
    }
}
