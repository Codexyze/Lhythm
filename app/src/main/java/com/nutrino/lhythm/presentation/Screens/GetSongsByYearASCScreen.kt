package com.nutrino.lhythm.presentation.Screens

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
import com.nutrino.lhythm.presentation.Utils.LoadingScreen
import com.nutrino.lhythm.presentation.ViewModels.GetSongCategoryViewModel
import com.nutrino.lhythm.presentation.ViewModels.MediaManagerViewModel
import com.nutrino.lhythm.ui.theme.BlackColor


@Composable
fun GetSongsByYearASCScreen(viewmodel: GetSongCategoryViewModel= hiltViewModel(),
                              navController: NavController,
                              mediaPlayerViewModel: MediaManagerViewModel= hiltViewModel()) {
    val getSongsByYearASCState = viewmodel.getSongsByYearASCState.collectAsState()
    val listOfYearSongUri = remember { mutableListOf<Uri>() }

    if( getSongsByYearASCState.value.isLoading){
        LoadingScreen()
    }else if(! getSongsByYearASCState.value.error.isNullOrBlank()){
        Text("Error Loading Songs ${ getSongsByYearASCState.value.error}")
    }else if(! getSongsByYearASCState.value.data.isNullOrEmpty()){
        getSongsByYearASCState.value.data.forEach { song->
            listOfYearSongUri.add(song.path.toUri())
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = BlackColor)) {
            LazyColumn(modifier = Modifier.background(color = BlackColor)) {
                items( getSongsByYearASCState.value.data) { song ->
                    Box(modifier = Modifier
                        .wrapContentSize()
                        .clickable {
                            mediaPlayerViewModel.playMusic(song.path.toUri())
                        }){

                        EachSongItemLook(songTitle = song.title, songPath =song.path ,
                            songArtist = song.artist, songDuration = song.duration,
                            songYear = song.year, albumID = song.albumId,
                            songUriList = listOfYearSongUri,
                            index = getSongsByYearASCState.value.data.indexOf(song),
                            navController = navController)
                    }

                }

            }
        }
    }else{
        NoSongsFoundScreen()
    }

}

