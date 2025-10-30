package com.nutrino.lhythm.presentation.Screens

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
import com.nutrino.lhythm.presentation.Utils.LoadingScreen
import com.nutrino.lhythm.presentation.ViewModels.GetSongCategoryViewModel
import com.nutrino.lhythm.presentation.ViewModels.MediaManagerViewModel
import com.nutrino.lhythm.ui.theme.BlackColor


@Composable
fun GetAllSongsByArtistScreenMedium(
    viewmodel: GetSongCategoryViewModel = hiltViewModel(),
    navController: NavController,
    mediaPlayerViewModel: MediaManagerViewModel = hiltViewModel()
) {
    val getAllSongsByArtistState = viewmodel.getAllSongsByArtist.collectAsState()
    val listOfSongByArtistUri = remember { mutableListOf<Uri>() }

    if (getAllSongsByArtistState.value.isLoading) {
        LoadingScreen()
    } else if (!getAllSongsByArtistState.value.error.isNullOrBlank()) {
        Text("Error Loading Songs ${getAllSongsByArtistState.value.error}")
    } else if (!getAllSongsByArtistState.value.data.isNullOrEmpty()) {
        getAllSongsByArtistState.value.data.forEach { song ->
            listOfSongByArtistUri.add(song.path.toUri())
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
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items (getAllSongsByArtistState.value.data) { song ->
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .clickable {
                                mediaPlayerViewModel.playMusic(song.path.toUri())
                            }
                    ) {
                        EachSongItemLook(
                            songPath = song.path,
                            songTitle = song.title,
                            songArtist = song.artist,
                            songDuration = song.duration,
                            songYear = song.year,
                            albumID = song.albumId,
                            songUriList = listOfSongByArtistUri,
                            index = getAllSongsByArtistState.value.data.indexOf(song),
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
