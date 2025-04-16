package com.example.lhythm.presentation.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lhythm.presentation.Utils.LoadingScreen
import com.example.lhythm.presentation.ViewModels.GetAllSongsASCViewModel

@Composable
fun SongCategoriesScreen(viewmodel: GetAllSongsASCViewModel= hiltViewModel()) {
    val getAllSongsASCState = viewmodel.songsInASCOrderState.collectAsState()
    if(getAllSongsASCState.value.isLoading){
        LoadingScreen()
    }else if(!getAllSongsASCState.value.error.isNullOrBlank()){
        Text("Error Loading Songs ${getAllSongsASCState.value.error}")
    }else{
        Column(modifier = Modifier.fillMaxSize()){
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(onClick = {

                }) {
                    Text("ASC")
                }
                Button(onClick = {

                }) {
                    Text("DESC")
                }

            }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(getAllSongsASCState.value.data){song->
                    EachSongItemLook(songTitle = song.title, songArtist = song.artist, songDuration = song.duration, songYear = song.year)
                }

            }
        }
    }


}