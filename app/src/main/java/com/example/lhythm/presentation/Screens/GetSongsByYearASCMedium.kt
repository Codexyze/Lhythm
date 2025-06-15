package com.example.lhythm.presentation.Screens

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lhythm.presentation.Utils.LoadingScreen
import com.example.lhythm.presentation.ViewModels.GetSongCategoryViewModel
import com.example.lhythm.presentation.ViewModels.MediaManagerViewModel
import com.example.lhythm.ui.theme.BlackColor

@Composable
fun GetSongsByYearASCScreenMedium(
    viewmodel: GetSongCategoryViewModel = hiltViewModel(),
    navController: NavController,
    mediaPlayerViewModel: MediaManagerViewModel = hiltViewModel()
) {
    val getSongsByYearASCState = viewmodel.getSongsByYearASCState.collectAsState()
    val listOfYearSongUri = remember { mutableListOf<Uri>() }

    if (getSongsByYearASCState.value.isLoading) {
        LoadingScreen()
    } else if (!getSongsByYearASCState.value.error.isNullOrBlank()) {
        Text("Error Loading Songs ${getSongsByYearASCState.value.error}")
    } else if (!getSongsByYearASCState.value.data.isNullOrEmpty()) {
        getSongsByYearASCState.value.data.forEach { song ->
            listOfYearSongUri.add(song.path.toUri())
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BlackColor)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = BlackColor),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items (getSongsByYearASCState.value.data) { song ->
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .clickable {
                                mediaPlayerViewModel.playMusic(song.path.toUri())
                            }
                    ) {
                        EachSongItemLook(
                            songTitle = song.title,
                            songPath = song.path,
                            songArtist = song.artist,
                            songDuration = song.duration,
                            songYear = song.year,
                            albumID = song.albumId,
                            songUriList = listOfYearSongUri,
                            index = getSongsByYearASCState.value.data.indexOf(song),
                            navController = navController
                        )
                    }
                }
            }
        }
    } else {
        NoSongsFoundScreen()
    }
}
