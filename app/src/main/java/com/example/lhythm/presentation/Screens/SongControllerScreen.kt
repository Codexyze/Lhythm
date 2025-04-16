package com.example.lhythm.presentation.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lhythm.presentation.ViewModels.MediaManagerViewModel
import com.example.lhythm.R

@Composable
fun SongControllerScreen(
    viewModel: MediaManagerViewModel = hiltViewModel()
) {
    Scaffold {
        Box(modifier = Modifier.padding(it)) {
            // UI content like Play/Pause buttons
            Column(modifier = Modifier.padding(16.dp).fillMaxSize(), verticalArrangement = Arrangement.Center
                , horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painter = painterResource(R.drawable.lythmlogoasset), contentDescription = "music",
                    modifier = Modifier.fillMaxSize(0.75f))

                Text("Now Playing")
                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    // Play/Pause button
                    Button(onClick = {
                       viewModel.pauseMusic()
                    }) {
                        Text("Pause")
                    }
                    Button(onClick = {
                        viewModel.playMusic()
                    }) {
                        Text("Play")
                    }

                    // Stop button

                }
            }
        }
    }
}
