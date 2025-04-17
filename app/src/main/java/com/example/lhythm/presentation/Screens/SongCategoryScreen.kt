package com.example.lhythm.presentation.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lhythm.presentation.UIModels.SongCategory
import com.example.lhythm.presentation.Utils.LoadingScreen
import com.example.lhythm.presentation.ViewModels.GetSongCategoryViewModel
import com.example.lhythm.presentation.ViewModels.MediaManagerViewModel
import com.example.lhythm.ui.theme.BlackColor

@Composable
fun SongCategoriesScreen(viewmodel: GetSongCategoryViewModel= hiltViewModel(),
                         navController: NavController,
                         mediaPlayerViewModel: MediaManagerViewModel= hiltViewModel()) {
    val getAllSongsASCState = viewmodel.songsInASCOrderState.collectAsState()
    val currentCategory = rememberSaveable { mutableStateOf(SongCategory.ASCENDING) }
    if(getAllSongsASCState.value.isLoading){
        LoadingScreen()
    }else if(!getAllSongsASCState.value.error.isNullOrBlank()){
        Text("Error Loading Songs ${getAllSongsASCState.value.error}")
    }else if(!getAllSongsASCState.value.data.isNullOrEmpty()){
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = BlackColor)) {
            Row {
                Button(
                    onClick = {
                        currentCategory.value = SongCategory.ASCENDING
                    }
                ) {
                    Text("Asc")

                }
                Button(
                    onClick = {
                        currentCategory.value = SongCategory.DESCENDING
                    }
                ) {
                    Text("Desc")
                }
                Button(
                    onClick = {
                        currentCategory.value = SongCategory.ARTIST
                    }
                ) {
                    Text("Artist")
                }
                Button(
                    onClick = {
                      currentCategory.value = SongCategory.YEARASC
                    }
                ) {
                    Text("Year")
                }
            }
            when(currentCategory.value){
                SongCategory.ASCENDING ->{
                    GetAllSongASCScreen(navController = navController)
                }
               SongCategory.DESCENDING->{
                    GetAllSongsDESC(navController = navController)
               }

                SongCategory.ARTIST->{

                     GetAllSongsByArtistScreen(navController = navController)
                }
                SongCategory.YEARASC -> {
                    GetSongsByYearASCScreen(navController = navController)
                }
            }
        }

    }
}
