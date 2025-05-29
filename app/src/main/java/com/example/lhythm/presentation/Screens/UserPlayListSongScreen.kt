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
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.lhythm.R
import com.example.lhythm.constants.Constants
import com.example.lhythm.data.Local.PlayListSongMapper
import com.example.lhythm.presentation.Navigation.LYRICSFULLSCREEN
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
                                song = song,
                                navController = navController
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
                         song: PlayListSongMapper,
                         navController: NavController,
                         playListViewModel: PlayListViewModel= hiltViewModel()) {
    val duration = remember { mutableStateOf("") }
    val context = LocalContext.current
    val showInfoDialogueBox = remember { mutableStateOf(false) }
    val lyrics=rememberSaveable { mutableStateOf("") }
    val updateLyricsFromPlayListState = playListViewModel.updateLyricsFromPlayListState.collectAsState()
    val showInfoDialogue= rememberSaveable { mutableStateOf(false) }
    val deletePlayListSongsState = playListViewModel.deletePlayListSongsState.collectAsState()
    when{
        updateLyricsFromPlayListState.value.isLoading || deletePlayListSongsState.value.isLoading->{
            LoadingScreen()
        }
        !updateLyricsFromPlayListState.value.error.isNullOrEmpty()->{
            showToastMessage(context = context,text = "Error Updating Lyrics",type = Constants.TOASTERROR)
        }
        !updateLyricsFromPlayListState.value.data.isNullOrEmpty()->{
            showToastMessage(context = context,text = "Lyrics Updated",type = Constants.TOASTSUCCESS)
        }
        !deletePlayListSongsState.value.error.isNullOrEmpty()->{
            showToastMessage(context = context,text = "Error Deleting Song",type = Constants.TOASTERROR)
        }
        !deletePlayListSongsState.value.data.isNullOrEmpty()->{
            showToastMessage(context = context,text = "Song Deleted",type = Constants.TOASTSUCCESS)
        }

    }

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
                                    playListViewModel.deletePlayListSongs(playListSongMapper =song )

                                }
                        )
                        Icon(imageVector = Icons.Filled.ChatBubble, contentDescription = "Lyrics",
                            tint = MaterialTheme.colorScheme.primary, modifier = Modifier.weight(1f).clickable {
                                navController.navigate(LYRICSFULLSCREEN(lyrics = song.lyrics.toString()))

                            }
                        )
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "Lyrics",
                            tint = MaterialTheme.colorScheme.primary, modifier = Modifier.weight(1f).clickable {
//                               playListViewModel.updateLyricsFromPlayList( )
                                showInfoDialogueBox.value = true
                            }
                        )
                        Icon(imageVector = Icons.Filled.Info, contentDescription = "Info",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.weight(1f).clickable {
                                showInfoDialogueBox.value = true

                            }
                        )
                    }

                }

            }

        }
    }
    if(showInfoDialogueBox.value){
        AlertDialog(
            onDismissRequest = {
                showInfoDialogueBox.value = false
            },
            title = {
                if(lyrics.value.isEmpty()){
                    Text("Add Lyrics",color = MaterialTheme.colorScheme.primary)
                }else{
                    LazyColumn {
                        item {
                            Text(
                                text = lyrics.value,
                                color = MaterialTheme.colorScheme.primary,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis
                            )

                        }
                    }

                }
            },
            text = {
                OutlinedTextField(
                    value = lyrics.value,
                    onValueChange = {
                        lyrics.value = it
                    },
                    singleLine = true,
                    label = {
                        Text("Add Lyrics",color = MaterialTheme.colorScheme.primary)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                        errorTextColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                    ),textStyle = TextStyle(color = MaterialTheme.colorScheme.primary)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        playListViewModel.updateLyricsFromPlayList(id = song.id,lyrics = lyrics.value)

                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showInfoDialogueBox.value = false

                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Cancel")
                }

            }
        )
    } else if(showInfoDialogueBox.value){
        AlertDialog(
            onDismissRequest = { showInfoDialogueBox.value = false },
            title = { Text("Add to playlist") },
            confirmButton = {

            },
            dismissButton = {

            },
            text = {
                Column {
                    LazyColumn {
                        item {
                            Text("All Detail ")
                            Text("Title : ${song.title}")
                            Text("Album : ${song.album}")
                            Text("Artist : ${song.artist}")
                            Text("Composer : ${song.composer}")
                            Text("Duration of :" + duration.value.toString())
                            Text("Size : ${song.size}")
                            Text("Year : ${song.year}")
                            Text("This ${song.title} was published in year ${song.year} by ${song.artist} and composed by ${song.composer}." +
                                    " It has a duration of ${duration.value} and is ${song.size} in size.")
                        }
                    }


                }
            }
        )
    }


}