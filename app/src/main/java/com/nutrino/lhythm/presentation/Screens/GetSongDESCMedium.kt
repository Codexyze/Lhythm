package com.nutrino.lhythm.presentation.Screens

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.nutrino.lhythm.presentation.Utils.LoadingScreen
import com.nutrino.lhythm.presentation.ViewModels.GetSongCategoryViewModel
import com.nutrino.lhythm.presentation.ViewModels.MediaManagerViewModel
import com.nutrino.lhythm.ui.theme.BlackColor


@Composable
fun GetAllSongsDESCMedium(
    viewmodel: GetSongCategoryViewModel = hiltViewModel(),
    navController: NavController,
    mediaPlayerViewModel: MediaManagerViewModel = hiltViewModel()
) {
    val getAllSongsDESCState = viewmodel.songsInDSCOrderState.collectAsState()
    val listOfDescSongsUri = remember { mutableListOf<Uri>() }

    if (getAllSongsDESCState.value.isLoading) {
        LoadingScreen()
    } else if (!getAllSongsDESCState.value.error.isNullOrBlank()) {
        Text("Error Loading Songs ${getAllSongsDESCState.value.error}")
    } else if (!getAllSongsDESCState.value.data.isNullOrEmpty()) {
        getAllSongsDESCState.value.data.forEach { song ->
            listOfDescSongsUri.add(song.path.toUri())
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BlackColor)
                .padding(16.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items (getAllSongsDESCState.value.data) { song ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
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
                            songUriList = listOfDescSongsUri,
                            index = getAllSongsDESCState.value.data.indexOf(song),
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
