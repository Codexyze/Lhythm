package com.example.lhythm.presentation.Screens

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lhythm.presentation.Utils.LoadingScreen
import com.example.lhythm.presentation.ViewModels.GetSongCategoryViewModel
import com.example.lhythm.presentation.ViewModels.MediaManagerViewModel
import com.example.lhythm.ui.theme.BlackColor


@Composable
fun GetAllSongASCScreen(viewmodel: GetSongCategoryViewModel= hiltViewModel(),
                        navController: NavController,
                        mediaPlayerViewModel: MediaManagerViewModel= hiltViewModel()) {
    val getAllSongsASCState = viewmodel.songsInASCOrderState.collectAsState()
    val listOfAscSongUri = remember { mutableListOf<Uri>() }

    when{
        getAllSongsASCState.value.isLoading->{
            LoadingScreen()
        }
        !getAllSongsASCState.value.error.isNullOrBlank()->{
            Text("Error Loading Songs ${getAllSongsASCState.value.error}")
        }
        !getAllSongsASCState.value.data.isNullOrEmpty()->{
            getAllSongsASCState.value.data.forEach {song->
                listOfAscSongUri.add(song.path.toUri())

            }
            Column(modifier = Modifier
                .fillMaxSize()
                .background(color = BlackColor)) {
                LazyColumn(modifier = Modifier.background(color = BlackColor)) {
                    items(getAllSongsASCState.value.data) { song ->
                        Box(modifier = Modifier
                            .wrapContentSize()
                            .clickable {
                                mediaPlayerViewModel.playMusic(song.path.toUri())
                            }){

                            EachSongItemLook(songTitle = song.title, songPath = song.path, songArtist = song.artist,
                                songDuration = song.duration, songYear = song.year,
                                albumID = song.albumId,
                                songUriList = listOfAscSongUri,
                                index = getAllSongsASCState.value.data.indexOf(song),
                                navController = navController)
                        }

                    }

                }
            }
        }
        else->{
            NoSongsFoundScreen()
        }
    }

}

