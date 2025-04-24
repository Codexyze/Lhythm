package com.example.lhythm.presentation.Screens

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
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lhythm.presentation.Utils.LoadingScreen
import com.example.lhythm.presentation.ViewModels.GetSongCategoryViewModel
import com.example.lhythm.presentation.ViewModels.MediaManagerViewModel
import com.example.lhythm.ui.theme.BlackColor


@Composable
fun GetAllSongsByArtistScreen(viewmodel: GetSongCategoryViewModel= hiltViewModel(),
                    navController: NavController,
                    mediaPlayerViewModel: MediaManagerViewModel= hiltViewModel()) {
    val getAllSongsByArtistState = viewmodel.getAllSongsByArtist.collectAsState()

    if(getAllSongsByArtistState.value.isLoading){
        LoadingScreen()
    }else if(!getAllSongsByArtistState.value.error.isNullOrBlank()){
        Text("Error Loading Songs ${getAllSongsByArtistState.value.error}")
    }else if(!getAllSongsByArtistState.value.data.isNullOrEmpty()){
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = BlackColor)) {
            LazyColumn(modifier = Modifier.background(color = BlackColor)) {
                items(getAllSongsByArtistState.value.data) { song ->
                    Box(modifier = Modifier
                        .wrapContentSize()
                        .clickable {
                            mediaPlayerViewModel.playMusic(song.path.toUri())
                        }){

                        EachSongItemLook(songPath = song.path, songTitle = song.title,
                            songArtist = song.artist, songDuration = song.duration,
                            songYear = song.year, albumID = song.albumId)
                    }

                }

            }
        }
    }else{
        NoSongsFoundScreen()
    }

}