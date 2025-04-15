package com.example.lhythm.presentation.Screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lhythm.presentation.Navigation.MUSICPLAYERSCREEN
import com.example.lhythm.presentation.ViewModels.GetAllSongViewModel

@Composable
fun  TestScreen(viewmodel: GetAllSongViewModel = hiltViewModel(),navController: NavController){
    val state = viewmodel.getAllSongsState.collectAsState()

    when {
        state.value.isLoading -> {
            CircularProgressIndicator()
        }

        else -> {
            LazyColumn {
                items(state.value.data) { song ->
                    Box(modifier = Modifier.wrapContentSize().clickable{
                        navController.navigate(MUSICPLAYERSCREEN(path = song.path))
                    }){
                        Column {
                            Text("${song.title}")
                            Spacer(modifier = Modifier.height(5.dp))
                            Text("${song.artist}")
                            Spacer(modifier = Modifier.height(5.dp))
                            Text("${song.duration}")
                            Spacer(modifier = Modifier.height(5.dp))
                            Text("${song.path}")
                            Spacer(modifier = Modifier.height(5.dp))
                            Text("${song.album}")
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(text = "${song.year}")
                            Spacer(modifier = Modifier.height(5.dp))
                            Text("${song.composer}")
                            Spacer(modifier = Modifier.height(5.dp))
                            Text("Sixe ${song.size}")
                        }
                    }

                }

            }
        }
    }
}