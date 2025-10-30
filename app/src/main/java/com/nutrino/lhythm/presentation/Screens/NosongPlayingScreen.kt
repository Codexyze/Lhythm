package com.nutrino.lhythm.presentation.Screens

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
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nutrino.lhythm.R
import com.shashank.sony.fancytoastlib.FancyToast


@Composable
fun NoSongPlaying() {
    val context= LocalContext.current
    val currentPosition = rememberSaveable { mutableStateOf(0F) }
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

                Text("Not Playing")

                Spacer(modifier = Modifier.height(16.dp))

                // 🎚️ SeekBar (Slider)
                Slider(
                    value = currentPosition.value, // Just in case pos > dur
                    onValueChange = { currentPosition.value = it },
                    valueRange = 0f..50F,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {

                    IconButton(onClick = {
                        FancyToast.makeText(
                            context, "No Songs Played",
                            FancyToast.LENGTH_SHORT,
                            FancyToast.WARNING, false
                        ).show()

                    }) {
                        Icon(
                            imageVector =  Icons.Default.PlayCircle,
                            contentDescription = "Toggle Play/Pause"
                        )
                    }

                }
            }
        }
    }

}