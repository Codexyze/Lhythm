package com.nutrino.lhythm.presentation.Screens

import android.content.ContentUris
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.draw.clip
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
fun FavSongScreenMedium(favSongsViewModel: FavSongViewModel = hiltViewModel()) {
    val liked = rememberSaveable { mutableStateOf(true) }
    val delFavSongState = favSongsViewModel.deleteFavSongState.collectAsState()

    LaunchedEffect(Unit) {
        favSongsViewModel.getAllFavSong()
    }

    val listOfUri = remember { mutableListOf<Uri>() }
    val favSongsState = favSongsViewModel.getAllFavSongState.collectAsState()

    when {
        favSongsState.value.isLoading -> {
            LoadingScreen()
        }
        !favSongsState.value.error.isNullOrEmpty() || !delFavSongState.value.error.isNullOrEmpty() -> {
            NoSongsFoundScreen()
        }
        favSongsState.value.data.isNullOrEmpty() -> {
            NoSongsFoundScreen()
        }
        !favSongsState.value.data.isNullOrEmpty() -> {
            favSongsState.value.data.forEach { song ->
                listOfUri.add(song.path.toUri())
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(favSongsState.value.data) { favSong ->
                    Log.d("IMAGEURI", favSong.imagePersonal.toString())
                    EachFavItemScreenMedium(
                        favSong = favSong,
                        listOfUris = listOfUri,
                        index = favSongsState.value.data.indexOf(favSong)
                    )
                }
            }
        }
        else -> {
            NoSongsFoundScreen()
        }
    }
}

@Composable
fun EachFavItemScreenMedium(
    favSong: FavSongEntity,
    favSongsViewModel: FavSongViewModel = hiltViewModel(),
    listOfUris: List<Uri> = emptyList(),
    index: Int,
    mediaManagerViewModel: MediaManagerViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                mediaManagerViewModel.playPlayListWithIndex(
                    listOfSongsUri = listOfUris,
                    index = index,
                    context = context
                )
            },
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (favSong.albumId.isNullOrEmpty()) {
                    Image(
                        painter = painterResource(R.drawable.lythmlogoasset),
                        contentDescription = "Default Image",
                        modifier = Modifier
                            .size(90.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                } else {
                    val image = ContentUris.withAppendedId(
                        Uri.parse("content://media/external/audio/albumart"),
                        favSong.albumId.toLong()
                    )
                    AsyncImage(
                        model = image,
                        placeholder = painterResource(R.drawable.lythmlogoasset),
                        error = painterResource(R.drawable.lythmlogoasset),
                        contentDescription = "Album Art",
                        modifier = Modifier
                            .size(90.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = favSong.title.orEmpty(),
                        maxLines = 2,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = favSong.artist.orEmpty(),
                        maxLines = 1,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = formatDuration(favSong.duration!!.toLong()),
                        maxLines = 1,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }

            Divider(modifier = Modifier.padding(vertical = 4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            favSongsViewModel.deleteFavSong(favSong)
                        }
                )
            }
        }
    }
}

