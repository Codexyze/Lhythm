package com.example.lhythm.presentation.Screens

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lhythm.presentation.ViewModels.PlayListViewModel


@Composable
fun PlayListExample(navController: NavController,playListViewModel: PlayListViewModel= hiltViewModel()) {
    LaunchedEffect(Unit) {
        playListViewModel.getSongsFromPlayList()
    }

    val getAllSongsState = playListViewModel.getSongFromPlayListState.collectAsState()


    val list=getAllSongsState.value.data
    LazyColumn {
        items(count = list.size) {int->
            val listvalue=list[int]
            Text(listvalue.title.toString())
            Log.d("DATABASE", "${listvalue.title.toString()}")

        }
    }




}
