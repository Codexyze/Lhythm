package com.example.lhythm.presentation.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lhythm.data.Local.PlayListTable
import com.example.lhythm.presentation.ViewModels.PlayListViewModel

@Composable
fun ListOfPlayListScreen(playListViewModel: PlayListViewModel = hiltViewModel()) {
    val playlistName = remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize()) {
        Text("PlayList Screen")
        OutlinedTextField(
            value = playlistName.value,
            onValueChange = {
                playlistName.value = it
            }
        )
        Button(onClick = {
           val playList = PlayListTable(playListName = playlistName.value)
            playListViewModel.createOrInsertPlayList(playList)
        }) {
            Text("Add PlayList")
        }

    }

}