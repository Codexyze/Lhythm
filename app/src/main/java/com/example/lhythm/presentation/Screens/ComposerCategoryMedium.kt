package com.example.lhythm.presentation.Screens

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
fun ComposerCategoryMedium(
    viewmodel: GetSongCategoryViewModel = hiltViewModel(),
    navController: NavController,
    mediaPlayerViewModel: MediaManagerViewModel = hiltViewModel()
) {
    val composerSortStateAsc = viewmodel.getSongByComposerASCState.collectAsState()
    val listOfComposerPlay = remember { mutableListOf<Uri>() }

    when {
        composerSortStateAsc.value.isLoading -> {
            LoadingScreen()
        }

        !composerSortStateAsc.value.error.isNullOrEmpty() -> {
            Text("Error Loading Songs ${composerSortStateAsc.value.error}")
        }

        !composerSortStateAsc.value.data.isNullOrEmpty() -> {
            composerSortStateAsc.value.data.forEach {
                listOfComposerPlay.add(it.path.toUri())
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
                        .background(color = BlackColor)
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items (composerSortStateAsc.value.data) { song ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
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
                                songUriList = listOfComposerPlay,
                                index = composerSortStateAsc.value.data.indexOf(song),
                                navController = navController
                            )
                        }
                    }
                }
            }
        }

        else -> {
            NoSongsFoundScreen()
        }
    }
}
