package com.example.lhythm.presentation.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lhythm.data.Local.FavSongEntity
import com.example.lhythm.presentation.Utils.LoadingScreen
import com.example.lhythm.presentation.Utils.formatDuration
import com.example.lhythm.presentation.ViewModels.FavSongViewModel
import com.example.lhythm.presentation.ViewModels.MediaManagerViewModel
import com.example.lhythm.ui.theme.RedThemeSuit1
import com.example.lhythm.ui.theme.WhiteColor

@Composable
fun FavSongScreen(favSongsViewModel: FavSongViewModel = hiltViewModel(),mediaManagerViewModel: MediaManagerViewModel = hiltViewModel()) {
    val liked = rememberSaveable { mutableStateOf(true) }
    val delFavSongState = favSongsViewModel.deleteFavSongState.collectAsState()
    LaunchedEffect(delFavSongState.value) {
        favSongsViewModel.getAllFavSong()
    }
    val favSongsState = favSongsViewModel.getAllFavSongState.collectAsState()

    if(favSongsState.value.isLoading ){
        LoadingScreen()
    }else if(!favSongsState.value.error.isNullOrEmpty()||!delFavSongState.value.error.isNullOrEmpty()){
        //Error Screen here
    }else if(favSongsState.value.data.isEmpty()){
        NoSongsFoundScreen()
    }else if(!favSongsState.value.data.isNullOrEmpty()|| delFavSongState.value.data!=null){
        favSongsViewModel.getAllFavSong()
        LazyColumn(modifier = Modifier.fillMaxSize()){
            items(favSongsState.value.data) {favSong->
               // EachFavItem(favSong = favSongs)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                           mediaManagerViewModel.playMusic(favSong.path.toUri())

                        },
                    elevation = CardDefaults.elevatedCardElevation(8.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(favSong.title.toString(), maxLines = 2)
                        }
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(text = favSong.artist.toString(), maxLines = 1)
                        }
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(text = favSong.composer.toString(), maxLines = 1)
                        }
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(text = formatDuration(favSong.duration!!.toLong()), maxLines = 1)
                        }
                        Row(
                            modifier = Modifier, horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = "Favorite",
                                modifier = Modifier.weight(1f).clickable {

                                },
                                tint = if (liked.value) {
                                    RedThemeSuit1
                                } else {
                                    WhiteColor
                                }
                            )

                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Delete",
                                modifier = Modifier.weight(1f).clickable {
                                    val favSongEntity = favSong
                                    favSongsViewModel.deleteFavSong(favSongEntity)
                                    favSongsViewModel.getAllFavSong()
                                },
                                tint = if (liked.value) {
                                    RedThemeSuit1
                                } else {
                                    WhiteColor
                                }
                            )
                        }


                    }

                }
            }

        }

    }

}
