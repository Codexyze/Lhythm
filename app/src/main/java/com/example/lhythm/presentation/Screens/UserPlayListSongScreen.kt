package com.example.lhythm.presentation.Screens

import android.content.ContentUris
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.lhythm.R
import com.example.lhythm.constants.Constants
import com.example.lhythm.data.Local.FavSongEntity
import com.example.lhythm.data.Local.PlayListSongMapper
import com.example.lhythm.data.Local.SongEntity
import com.example.lhythm.presentation.Utils.LoadingScreen
import com.example.lhythm.presentation.Utils.formatDuration
import com.example.lhythm.presentation.Utils.showToastMessage
import com.example.lhythm.presentation.ViewModels.MediaManagerViewModel
import com.example.lhythm.presentation.ViewModels.PlayListViewModel
import com.example.lhythm.ui.theme.cardColor
import com.shashank.sony.fancytoastlib.FancyToast

@Composable
fun UserPlayListScreen(navController: NavHostController,
                       playListID: Int,
                       playListViewModel: PlayListViewModel= hiltViewModel()
) {
    LaunchedEffect(Unit) {
        playListViewModel.getSongByPlayListID(playListId = playListID)
    }
    val getSongByPlayListIDState = playListViewModel.getSongByPlayListIDState.collectAsState()

    when{
        getSongByPlayListIDState.value.isLoading->{
            LoadingScreen()
        }
        !getSongByPlayListIDState.value.error.isNullOrEmpty()->{

        }
        getSongByPlayListIDState.value.data.isNullOrEmpty()->{
           NoSongsFoundScreen()
        }
        !getSongByPlayListIDState.value.data.isNullOrEmpty()->{
            val listOfAllSongs = remember(getSongByPlayListIDState.value.data) {
                getSongByPlayListIDState.value.data?.map { it.path.toUri() } ?: emptyList()
            }

            Scaffold(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier
                    .padding(it)
                    .background(color = MaterialTheme.colorScheme.background)) {
                    LazyColumn {
                        items(getSongByPlayListIDState.value.data) {song->
                            EachUserPlayListItem(index = getSongByPlayListIDState.value.data.indexOf(song),
                                songUriList = listOfAllSongs,
                                song = song
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EachUserPlayListItem(mediaManagerViewModel: MediaManagerViewModel=hiltViewModel(),
                         index:Int=0,
                         songUriList: List<Uri>?=null,
                         song: PlayListSongMapper) {
    val duration = remember { mutableStateOf("") }
    val context = LocalContext.current
    val showInfoDialogueBox = remember { mutableStateOf(false) }
    Card (modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable {
            if (!songUriList.isNullOrEmpty()) {
                mediaManagerViewModel.playPlayListWithIndex(
                    listOfSongsUri = songUriList,
                    index = index,
                    context = context
                )

            } else if (songUriList.isNullOrEmpty()) {
                FancyToast.makeText(
                    context, "Error Loading Song",
                    FancyToast.LENGTH_SHORT,
                    FancyToast.ERROR, false
                ).show()
            }else{
                mediaManagerViewModel.playMusic(song.path.toUri())
            }


        }, elevation = CardDefaults.elevatedCardElevation(8.dp)
        , shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor // Light grey like dark theme cards
        )
    ){
        Column(modifier = Modifier.padding(8.dp)) {

            Row (modifier = Modifier.padding(5.dp)){
                val songUri = if(song.albumId==null){
                    null
                }else{
                    ContentUris.withAppendedId(
                        Uri.parse("content://media/external/audio/albumart"),
                        song.albumId.toLong()
                    )
                }
                if(songUri!=null) {
                    AsyncImage(
                        model = songUri,
                        error = painterResource(R.drawable.noalbumimgasset),
                        contentDescription = "AlbumArt",
                        modifier = Modifier
                            .weight(0.25f),
                        placeholder = painterResource(R.drawable.lythmlogoasset)
                    )
                }else{
                    Image(
                        painter = painterResource(R.drawable.noalbumimgasset),
                        contentDescription = "Logo",
                        modifier = Modifier.weight(0.25f)
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))
                Column(modifier = Modifier.weight(0.75f)) {
                    Row {
                       song.title?.let { Text(it, maxLines = 2, color = MaterialTheme.colorScheme.secondary) }

                    }

                    Row {
                       song.artist?.let { Text(it, maxLines = 1, color = MaterialTheme.colorScheme.secondary) }
                    }
                    Row{
                        song.year?.let { Text("year : $it", maxLines = 1, color = MaterialTheme.colorScheme.secondary) }

                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Absolute.SpaceEvenly){
                        song.duration?.let {
                            duration.value = formatDuration(it.toLong())
                            Text(duration.value.toString(), maxLines = 1, color = MaterialTheme.colorScheme.secondary) }


                    }
                    Row(modifier = Modifier, horizontalArrangement = Arrangement.Absolute.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {

                        //add to playlist
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "Add to playlist",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .weight(1f)
                                .clickable {

                                }
                        )
                        Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Favorite",
                            tint = MaterialTheme.colorScheme.primary, modifier = Modifier.weight(1f).clickable {

                            }
                        )
                    }

                }

            }

        }
    }


}