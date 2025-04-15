package com.example.lhythm.presentation.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lhythm.presentation.Navigation.MUSICPLAYERSCREEN
import com.example.lhythm.presentation.ViewModels.GetAllSongViewModel
import com.example.lhythm.presentation.ViewModels.MediaManagerViewModel

@Composable
fun  ListOfAllSongsScreen(viewmodel: GetAllSongViewModel = hiltViewModel(),navController: NavController,
                          mediaPlayerViewModel: MediaManagerViewModel= hiltViewModel()){
    val state = viewmodel.getAllSongsState.collectAsState()

    when {
        state.value.isLoading -> {
            CircularProgressIndicator()
        }

        else -> {
            LazyColumn {
                items(state.value.data) { song ->
                    Box(modifier = Modifier.wrapContentSize().clickable{
                       // navController.navigate(MUSICPLAYERSCREEN(path = song.path))
                        mediaPlayerViewModel.playMusic(song.path.toUri())
                    }){
                       // Column {
//                            Text("${song.title}")
//                            Spacer(modifier = Modifier.height(5.dp))
//                            Text("${song.artist}")
//                            Spacer(modifier = Modifier.height(5.dp))
//                            Text("${song.duration}")
//                            Spacer(modifier = Modifier.height(5.dp))
//                            Text("${song.path}")
//                            Spacer(modifier = Modifier.height(5.dp))
//                            Text("${song.album}")
//                            Spacer(modifier = Modifier.height(5.dp))
//                            Text(text = "${song.year}")
//                            Spacer(modifier = Modifier.height(5.dp))
//                            Text("${song.composer}")
//                            Spacer(modifier = Modifier.height(5.dp))
//                            Text("Sixe ${song.size}")

                       // }
                        EachSongItemLook(songTitle = song.title, songArtist = song.artist, songDuration = song.duration, songYear = song.year)
                    }

                }

            }
        }
    }
}

@Composable
fun EachSongItemLook(songTitle: String?="", songArtist: String?="", songDuration: String?="", songYear: String?="") {

    Card (modifier = Modifier.fillMaxWidth().padding(8.dp), elevation = CardDefaults.elevatedCardElevation(8.dp)){
        Column(modifier = Modifier.padding(8.dp)) {
            Row {
                songTitle?.let { Text(it, maxLines = 2) }
            }
            Row {
                songArtist?.let { Text(it, maxLines = 1) }
            }
            Row {
                songYear?.let { Text(it, maxLines = 1) }
                songDuration?.let { Text(it, maxLines = 1) }
            }
        }
    }


}