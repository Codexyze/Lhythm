package com.nutrino.lhythm.presentation.Screens

import android.content.ContentUris
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.nutrino.lhythm.R
import com.nutrino.lhythm.data.Local.FavSongEntity
import com.nutrino.lhythm.presentation.Utils.LoadingScreen
import com.nutrino.lhythm.presentation.Utils.formatDuration
import com.nutrino.lhythm.presentation.ViewModels.FavSongViewModel
import com.nutrino.lhythm.presentation.ViewModels.MediaManagerViewModel

@Composable
fun FavSongScreen(favSongsViewModel: FavSongViewModel = hiltViewModel()) {
    val liked = rememberSaveable { mutableStateOf(true) }
    val delFavSongState = favSongsViewModel.deleteFavSongState.collectAsState()

    LaunchedEffect(Unit) {
        favSongsViewModel.getAllFavSong()
    }
   val listOfUri = remember { mutableListOf<Uri>() }
    val favSongsState = favSongsViewModel.getAllFavSongState.collectAsState()
    when{
        favSongsState.value.isLoading->{
            LoadingScreen()
        }
        !favSongsState.value.error.isNullOrEmpty()||!delFavSongState.value.error.isNullOrEmpty()->{
            NoSongsFoundScreen()
        }
        favSongsState.value.data.isNullOrEmpty()->{
            NoSongsFoundScreen()
        }
        !favSongsState.value.data.isNullOrEmpty()->{
            favSongsState.value.data.forEach {song->
               listOfUri.add(song.path.toUri())
            }

            LazyColumn(modifier = Modifier.fillMaxSize()){
                items(favSongsState.value.data) {favSong->
                    Log.d("IMAGEURI",favSong.imagePersonal.toString())
                    // EachFavItem(favSong = favSongs)
                 EachFavItemScreen(favSong = favSong, listOfUris = listOfUri, index = favSongsState.value.data.indexOf(favSong))
                }

            }
        }else->{
            NoSongsFoundScreen()
        }
    }

}

@Composable
fun EachFavItemScreen(favSong: FavSongEntity,favSongsViewModel: FavSongViewModel = hiltViewModel(),
                      listOfUris: List<Uri> = emptyList<Uri>() , index: Int,
                      mediaManagerViewModel: MediaManagerViewModel = hiltViewModel()) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                mediaManagerViewModel.playPlayListWithIndex(listOfSongsUri = listOfUris, index = index, context = context)

            },
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {

        Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxSize(0.25f)) {

                    Log.d("ImagePersonal", favSong.albumId.toString())
                    if (favSong.albumId.isNullOrEmpty()) {
                        Image(
                            painter = painterResource(R.drawable.lythmlogoasset),
                            contentDescription = "Image",
                            modifier = Modifier.weight(0.25f)
                        )
                    } else {
                        val image = ContentUris.withAppendedId(
                            Uri.parse("content://media/external/audio/albumart"),
                            favSong.albumId.toLong()
                        )
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Box(
                                modifier = Modifier
                                    .weight(0.25f)
                                    .aspectRatio(1f) // Ensures square
                            ) {
                                AsyncImage(
                                    model = image,
                                    placeholder = painterResource(R.drawable.lythmlogoasset),
                                    contentDescription = "Personal Image",
                                    error = painterResource(R.drawable.lythmlogoasset),
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                        }

                    }

                }

                Column(modifier = Modifier.fillMaxSize(0.75f)) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(favSong.title.toString(), maxLines = 2)
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(text = favSong.artist.toString(), maxLines = 1)
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(text = formatDuration(favSong.duration!!.toLong()), maxLines = 1)
                    }
                    Row(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier.weight(1f).clickable {
                                val favSongEntity = favSong
                                favSongsViewModel.deleteFavSong(favSongEntity)
                                // favSongsViewModel.getAllFavSong()
                            },
                            tint = MaterialTheme.colorScheme.primary

                        )

                    }
                }
            }

        }

    }
}
