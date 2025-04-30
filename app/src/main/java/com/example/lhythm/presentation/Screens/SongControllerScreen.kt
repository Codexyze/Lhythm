package com.example.lhythm.presentation.Screens

import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import com.example.lhythm.presentation.ViewModels.MediaManagerViewModel
import com.example.lhythm.R
import com.example.lhythm.constants.Constants
import com.example.lhythm.core.Media.MediaPlayerManager
import com.example.lhythm.presentation.Utils.showToastMessage
import com.example.lhythm.presentation.ViewModels.PlayListViewModel
import kotlinx.coroutines.delay
//
//@Composable
//fun SongControllerScreen(
//    viewModel: MediaManagerViewModel = hiltViewModel(),
//    playlistViewModel : PlayListViewModel = hiltViewModel()
//) {
//    LaunchedEffect(Unit) {
//        if (viewModel.isPlaying()) {
//            while (true) {
//                viewModel.getCurrentPosition()
//                delay(1000)
//            }
//        }
//    }
//
//    var duration = viewModel.getDuration()?.toFloat() ?: 1f
//    val currentPosition = viewModel.currentSongPositionState.collectAsState()
//    val index = viewModel.index.collectAsState()
//    val list = viewModel.songQueue.collectAsState()
//    val isPlaying = viewModel.isplayingState.collectAsState()
//    val lyricsState = playlistViewModel.getLyricsFromPlayListState.collectAsState()
//
//    val context = LocalContext.current
//    val player = viewModel.getExoplayer()
//
//
//    Log.d("SongControllerScreen", "index: ${index.value}")
//    Log.d("SongControllerScreen", "list: ${list.value}")
//
//
//
//    Scaffold {
//        Box(modifier = Modifier.padding(it)) {
//            Column(
//                modifier = Modifier
//                    .padding(16.dp)
//                    .fillMaxSize(),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                // 🎵 Image
//                Image(
//                    painter = painterResource(R.drawable.lythmlogoasset),
//                    contentDescription = "Now Playing Image",
//                    modifier = Modifier
//                        .fillMaxWidth(0.75f)
//                        .aspectRatio(1f)
//                )
//
//                // 🎶 Text
//                Text("Now Playing")
//
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                if (isPlaying.value) {
//                    duration = viewModel.getDuration()?.toFloat() ?: 1f
//                    Slider(
//                        value = currentPosition.value.coerceAtMost(duration),
//                        onValueChange = { viewModel.getExoplayer()?.seekTo(it.toLong()) },
//                        valueRange = 0f..duration,
//                        modifier = Modifier.fillMaxWidth()
//                    )
//
//                } else {
//                    // Maybe show loading or disabled slider
//                    duration=viewModel.getDuration()?.toFloat() ?: 1f
//                }
//
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // 🎮 Control Buttons
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceEvenly
//                ) {
//                    //  Previous Button
//                    IconButton(onClick = {
//                        if (player != null) {
//                            if (index.value > 0) {
//                                viewModel.playPlayListWithIndex(
//                                    listOfSongsUri = list.value,
//                                    index = index.value - 1,
//                                    context = context
//                                )
//                            } else {
//                                showToastMessage(
//                                    context = context,
//                                    text = "Already at the first song!",
//                                    type = Constants.TOASTCONFUSING
//                                )
//                            }
//                        } else {
//                            showToastMessage(
//                                context = context,
//                                text = "No Songs Played",
//                                type = Constants.TOASTWARNING
//                            )
//                        }
//                    }) {
//                        Icon(
//                            imageVector = Icons.Filled.NavigateBefore,
//                            contentDescription = "Previous"
//                        )
//                    }
//
//                    // ️ Play/Pause Button
//                    IconButton(onClick = {
//                        if (player != null) {
//                            if (isPlaying.value) {
//                                viewModel.pauseMusic()
//                            } else {
//                                viewModel.playMusic()
//                            }
//                        } else {
//                            showToastMessage(
//                                context = context,
//                                text = "No Songs Played",
//                                type = Constants.TOASTWARNING
//                            )
//                        }
//                    }) {
//                        Icon(
//                            imageVector = if (isPlaying.value) Icons.Default.Pause else Icons.Default.PlayCircle,
//                            contentDescription = "Play/Pause",
//                            modifier = Modifier.size(64.dp) // Big ol' button 😎
//                        )
//                    }
//
//                    //  Next Button
//                    IconButton(onClick = {
//                        if (player != null) {
//                            if (index.value + 1 < list.value.size) {
//                                // 👉 Move to next song
//                                viewModel.playPlayListWithIndex(
//                                    listOfSongsUri = list.value,
//                                    index = index.value + 1,
//                                    context = context
//                                )
//                            } else {
//                                // 🚫 Playlist ended
//                                showToastMessage(
//                                    context = context,
//                                    text = "Playlist Ended",
//                                    type = Constants.TOASTCONFUSING
//                                )
//                            }
//                        } else {
//                            showToastMessage(
//                                context = context,
//                                text = "No Songs Played",
//                                type = Constants.TOASTWARNING
//                            )
//                        }
//                    }) {
//                        Icon(
//                            imageVector = Icons.Filled.NavigateNext,
//                            contentDescription = "Next"
//                        )
//                    }
//                }
//            }
//        }
//    }
//
//}
//

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

    var duration = viewModel.getDuration()?.toFloat() ?: 1f
    val currentPosition = viewModel.currentSongPositionState.collectAsState()
    val index = viewModel.index.collectAsState()
    val list = viewModel.songQueue.collectAsState()
    val isPlaying = viewModel.isplayingState.collectAsState()
    val lyricsState = playlistViewModel.getLyricsFromPlayListState.collectAsState()

    val context = LocalContext.current
    val player = viewModel.getExoplayer()


    Log.d("SongControllerScreen", "index: ${index.value}")
    Log.d("SongControllerScreen", "list: ${list.value}")

//
//
//    Scaffold {
//        Box(modifier = Modifier.padding(it)) {
//            Column(
//                modifier = Modifier
//                    .padding(16.dp)
//                    .fillMaxSize(),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                // 🎵 Image
//                Image(
//                    painter = painterResource(R.drawable.lythmlogoasset),
//                    contentDescription = "Now Playing Image",
//                    modifier = Modifier
//                        .fillMaxWidth(0.75f)
//                        .aspectRatio(1f)
//                )
//
//                // 🎶 Text
//                Text("Now Playing")
//
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                if (isPlaying.value) {
//                    duration = viewModel.getDuration()?.toFloat() ?: 1f
//                    Slider(
//                        value = currentPosition.value.coerceAtMost(duration),
//                        onValueChange = { viewModel.getExoplayer()?.seekTo(it.toLong()) },
//                        valueRange = 0f..duration,
//                        modifier = Modifier.fillMaxWidth()
//                    )
//
//                } else {
//                    // Maybe show loading or disabled slider
//                    duration=viewModel.getDuration()?.toFloat() ?: 1f
//                }
//
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // 🎮 Control Buttons
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceEvenly
//                ) {
//                    //  Previous Button
//                    IconButton(onClick = {
//                        if (player != null) {
//                            if (index.value > 0) {
//                                viewModel.playPlayListWithIndex(
//                                    listOfSongsUri = list.value,
//                                    index = index.value - 1,
//                                    context = context
//                                )
//                            } else {
//                                showToastMessage(
//                                    context = context,
//                                    text = "Already at the first song!",
//                                    type = Constants.TOASTCONFUSING
//                                )
//                            }
//                        } else {
//                            showToastMessage(
//                                context = context,
//                                text = "No Songs Played",
//                                type = Constants.TOASTWARNING
//                            )
//                        }
//                    }) {
//                        Icon(
//                            imageVector = Icons.Filled.NavigateBefore,
//                            contentDescription = "Previous"
//                        )
//                    }
//
//                    // ️ Play/Pause Button
//                    IconButton(onClick = {
//                        if (player != null) {
//                            if (isPlaying.value) {
//                                viewModel.pauseMusic()
//                            } else {
//                                viewModel.playMusic()
//                            }
//                        } else {
//                            showToastMessage(
//                                context = context,
//                                text = "No Songs Played",
//                                type = Constants.TOASTWARNING
//                            )
//                        }
//                    }) {
//                        Icon(
//                            imageVector = if (isPlaying.value) Icons.Default.Pause else Icons.Default.PlayCircle,
//                            contentDescription = "Play/Pause",
//                            modifier = Modifier.size(64.dp) // Big ol' button 😎
//                        )
//                    }
//
//                    //  Next Button
//                    IconButton(onClick = {
//                        if (player != null) {
//                            if (index.value + 1 < list.value.size) {
//                                // 👉 Move to next song
//                                viewModel.playPlayListWithIndex(
//                                    listOfSongsUri = list.value,
//                                    index = index.value + 1,
//                                    context = context
//                                )
//                            } else {
//                                // 🚫 Playlist ended
//                                showToastMessage(
//                                    context = context,
//                                    text = "Playlist Ended",
//                                    type = Constants.TOASTCONFUSING
//                                )
//                            }
//                        } else {
//                            showToastMessage(
//                                context = context,
//                                text = "No Songs Played",
//                                type = Constants.TOASTWARNING
//                            )
//                        }
//                    }) {
//                        Icon(
//                            imageVector = Icons.Filled.NavigateNext,
//                            contentDescription = "Next"
//                        )
//                    }
//                }
//            }
//        }
//    }

    Column (modifier = Modifier.fillMaxSize()){
        AndroidView(
            factory = {
                PlayerView(it).apply {
                    this.player = viewModel.getExoplayer()
                    this.useController = true
                    this.setShowNextButton(false)
                    this.setShowPreviousButton(false)
                    this.setShowNextButton(true)
                    this.setShowPreviousButton(true)
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}


