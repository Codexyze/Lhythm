package com.example.lhythm.presentation.Screens

import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.lhythm.R
import com.example.lhythm.constants.Constants
import com.example.lhythm.data.Local.FavSongEntity
import com.example.lhythm.data.Local.PlayListSongMapper
import com.example.lhythm.data.Local.SongEntity
import com.example.lhythm.presentation.Navigation.AUDIOTRIMMERSCREEN
import com.example.lhythm.presentation.Utils.LoadingScreen
import com.example.lhythm.presentation.Utils.formatDuration
import com.example.lhythm.presentation.Utils.showToastMessage
import com.example.lhythm.presentation.ViewModels.FavSongViewModel
import com.example.lhythm.presentation.ViewModels.GetAllSongViewModel
import com.example.lhythm.presentation.ViewModels.MediaManagerViewModel
import com.example.lhythm.presentation.ViewModels.PlayListViewModel
import com.example.lhythm.ui.theme.cardColor
import com.shashank.sony.fancytoastlib.FancyToast

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun  ListOfAllSongsScreen(viewmodel: GetAllSongViewModel = hiltViewModel(),navController: NavController,
                          mediaPlayerViewModel: MediaManagerViewModel= hiltViewModel()){
    val state = viewmodel.getAllSongsState.collectAsState()
    val listOfPlayListUri = remember { mutableListOf<Uri>() }
    val searchSong = rememberSaveable { mutableStateOf("") }
    val isSearching = rememberSaveable { mutableStateOf(false) }

    //
    val context = LocalContext.current
    val listOfAllSongs = remember { mutableListOf<Uri>() }
    when{
        !state.value.error.isNullOrEmpty() ->{
            Text(text = state.value.error.toString())
        }
        state.value.isLoading->{
            LoadingScreen()
        }
        state.value.data.isNotEmpty()->{
            state.value.data.forEach {
                listOfAllSongs.add(it.path.toUri())
            }

            Column(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier.weight(0.10f), horizontalArrangement = Arrangement.Center) {
                    OutlinedTextField(
                        value = searchSong.value,
                        onValueChange = {
                            searchSong.value = it
                        },
                        label = {
                            Text("Search Song",color = MaterialTheme.colorScheme.primary)
                        },
                        modifier = Modifier.weight(0.85f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                            errorTextColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                        ),textStyle = TextStyle(color = MaterialTheme.colorScheme.primary)
                    )
                    IconButton(
                        onClick = {
                            isSearching.value = true

                        }
                    ) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "Search",tint = MaterialTheme.colorScheme.primary,)
                    }

                }
                if(isSearching.value){
                    if(!searchSong.value.isNullOrEmpty()) {
                        val filterList = state.value.data.filter { song ->
                            song.title?.contains(searchSong.value, ignoreCase = true) == true
                        }

                        LazyColumn (modifier = Modifier.weight(0.90F)){
                           items(filterList){songsFiltered->
                               EachSongItemLook(songid = songsFiltered.id, songTitle = songsFiltered.title,
                                   songArtist = songsFiltered.artist,
                                   songDuration = songsFiltered.duration,
                                   songYear = songsFiltered.year,songPath= songsFiltered.path,
                                   songSize = songsFiltered.size, album = songsFiltered.album,
                                   albumID = songsFiltered.albumId
                                   , composer = songsFiltered.composer,
                                   navController = navController)
                               listOfPlayListUri.add(songsFiltered.path.toUri())
                           }
                        }
                    }


                }else{
                    LazyColumn(modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.background)
                        .weight(0.90f)) {
                        items(state.value.data) { song ->
                            Box(modifier = Modifier
                                .wrapContentSize()
                                .clickable {

                                }){

                                EachSongItemLook(songid = song.id, songTitle = song.title, songArtist = song.artist,
                                    songDuration = song.duration,
                                    songYear = song.year,songPath= song.path,
                                    songSize = song.size, album = song.album,
                                    songUriList = listOfAllSongs
                                    , composer = song.composer, albumID = song.albumId,
                                    index = state.value.data.indexOf(song),
                                    navController = navController)
                                listOfPlayListUri.add(song.path.toUri())

                            }

                        }

                    }
                }

            }

        }else->{
            Text("No Songs Found")
        }

    }
}

@Composable
fun EachSongItemLook(songid: String="",  songTitle: String?="", songArtist: String?="",
                     songDuration: String?="",
                     songYear: String?="",
                     songPath: String?="",
                     songSize: String?="",
                     album: String?="Unknown",
                     composer: String?="Unknown",
                     albumID: String?=null,
                     songUriList: List<Uri>?=null,
                     index: Int = 0,
                     playListViewModel: PlayListViewModel=hiltViewModel(),
                     mediaManagerViewModel: MediaManagerViewModel=hiltViewModel(),
                     favSongViewModel: FavSongViewModel=hiltViewModel(),
                     navController: NavController
                     ) {
    LaunchedEffect(Unit) {
        playListViewModel.getSongsFromPlayList()
        favSongViewModel.getAllFavSong()
    }
    val playListState = playListViewModel.getAllPlayListState.collectAsState()
    val context = LocalContext.current
    val showDialogueBox = rememberSaveable { mutableStateOf(false) }
    val inserToPlayListState = playListViewModel.insertSongToPlaListState.collectAsState()
    val insertOrUpdateFavSongState =favSongViewModel.inserOrUpdateFavState.collectAsState()
    val getAllPlayListSongs =  playListViewModel.getSongFromPlayListState.collectAsState()
    val favSongsState = favSongViewModel.getAllFavSongState.collectAsState()
    val  duration = rememberSaveable{ mutableStateOf("0") }
    val playListSelectionDialog = rememberSaveable { mutableStateOf(false) }


    val createOrUpdatePlayListState = playListViewModel.createOrUpdatePlayListState.collectAsState()
    val upsertPlayListSongsState = playListViewModel.upsertPlayListSongsState.collectAsState()
    val getAllPlayListSongsState = playListViewModel.getAllPlayListSongsState.collectAsState()

    LaunchedEffect(upsertPlayListSongsState.value) {
        playListViewModel.getAllPlayListSongs()
    }
    when{
        !createOrUpdatePlayListState.value.data.isNullOrEmpty()->{
            showToastMessage(context= context, text = "New Playlist Created", type = Constants.TOASTSUCCESS)
        }
        upsertPlayListSongsState.value.isLoading ||createOrUpdatePlayListState.value.isLoading || getAllPlayListSongsState.value.isLoading ||inserToPlayListState.value.isLoading -> {
            //LoadingScreen()
            CircularProgressIndicator()
        }
        !upsertPlayListSongsState.value.error.isNullOrEmpty() || !createOrUpdatePlayListState.value.error.isNullOrEmpty() || !getAllPlayListSongsState.value.error.isNullOrEmpty() || !inserToPlayListState.value.error.isNullOrEmpty()-> {

        }
        !upsertPlayListSongsState.value.data.isNullOrEmpty()->{
            Log.d("UPSERTSUCESS", "SUCESS")

        }
        playListState.value.isLoading -> {
            LoadingScreen()
        }
        !playListState.value.error.isNullOrEmpty() -> {
            Text(text = playListState.value.error.toString())
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

                } else if (songPath.isNullOrEmpty()) {
                    FancyToast.makeText(
                        context, "Error Loading Song",
                        FancyToast.LENGTH_SHORT,
                        FancyToast.ERROR, false
                    ).show()
                } else {
                    mediaManagerViewModel.playMusic(songPath.toUri())
                }


            }, elevation = CardDefaults.elevatedCardElevation(8.dp)
            , shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = cardColor // Light grey like dark theme cards
            )
        ){
            Column(modifier = Modifier.padding(8.dp)) {

                Row (modifier = Modifier.padding(5.dp)){
                    val songUri = if(albumID==null){
                      null
                    }else{
                        ContentUris.withAppendedId(
                            Uri.parse("content://media/external/audio/albumart"),
                            albumID.toLong()
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
                            songTitle?.let { Text(it, maxLines = 2, color = MaterialTheme.colorScheme.secondary) }

                        }

                        Row {
                            songArtist?.let { Text(it, maxLines = 1, color = MaterialTheme.colorScheme.secondary) }
                        }
                        Row{
                            songYear?.let { Text("year : $it", maxLines = 1, color = MaterialTheme.colorScheme.secondary) }

                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Absolute.SpaceEvenly){
                            songDuration?.let {
                                duration.value = formatDuration(it.toLong())
                                Text(duration.value.toString(), maxLines = 1, color = MaterialTheme.colorScheme.secondary) }


                        }
                        Row(modifier = Modifier, horizontalArrangement = Arrangement.Absolute.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Filled.Info, contentDescription = "Info",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        showDialogueBox.value = true
                                    }
                            )
                            //add to playlist
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add to playlist",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        playListSelectionDialog.value = true
                                    }
                            )
                            Icon(imageVector = Icons.Filled.Edit, contentDescription = "Add to playlist",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                      navController.navigate(AUDIOTRIMMERSCREEN(uri = songPath.toString()))
                                    }
                            )
                            Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Favorite",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        //Fav impl here
                                        val favSongEntity = FavSongEntity(
                                            path = songPath.toString(),
                                            album = album,
                                            artist = songArtist,
                                            composer = composer,
                                            duration = songDuration,
                                            size = songSize,
                                            title = songTitle,
                                            year = songYear,
                                            albumId = albumID,
                                            lyrics = "Unknown"

                                        )
                                        favSongViewModel.insertOrUpdateFavSong(favSongEntity = favSongEntity)
                                        if (!insertOrUpdateFavSongState.value.data.isNullOrEmpty()) {
                                            FancyToast.makeText(
                                                context, "Saved",
                                                FancyToast.LENGTH_SHORT,
                                                FancyToast.SUCCESS, false
                                            ).show()
                                        } else if (!insertOrUpdateFavSongState.value.error.isNullOrEmpty()) {
                                            FancyToast.makeText(
                                                context, "Error Saving",
                                                FancyToast.LENGTH_SHORT,
                                                FancyToast.ERROR, false
                                            ).show()
                                        }


                                    }
                            )
                            Icon(imageVector = Icons.Filled.Share, contentDescription = "Share",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        val message = buildString {
                                            append("🎵 I'm vibin' to \"${songTitle ?: "Unknown Title"}\" by ${songArtist ?: "Unknown Artist"}!\n\n")
                                            append("🕒 Duration: ${songDuration ?: "Unknown"}\n")
                                            append("📀 Album: ${album ?: "Unknown"} (${songYear ?: "N/A"})\n")
                                            append("🎼 Composer: ${composer ?: "Unknown"}\n\n")
                                            append("🔥 You can listen to it on the Lythm App!\n")
                                            append("👉 Download now: ${Constants.REPOLINK}")
                                        }

                                        val shareIntent = Intent(Intent.ACTION_SEND)
                                        shareIntent.type = "text/plain"
                                        shareIntent.putExtra(Intent.EXTRA_TEXT, message)
                                        try {
                                            context.startActivity(
                                                Intent.createChooser(
                                                    shareIntent,
                                                    "Share via"
                                                )
                                            )
                                        } catch (e: Exception) {
                                            showToastMessage(
                                                context = context,
                                                text = "Error Sharing",
                                                type = Constants.TOASTERROR
                                            )
                                        }

                                    }
                            )
                    }

                }

                }
                if(showDialogueBox.value){
                    AlertDialog(
                        onDismissRequest = { showDialogueBox.value = false },
                        title = { Text("Add to playlist") },
                        confirmButton = {
                            Button(onClick = {
                                showDialogueBox.value = false
                            }) {
                                Text("Okay")
                            }
                        },
                        dismissButton = {
                            Button(onClick = {
                                val songEntity= SongEntity(
                                    path = songPath.toString(),
                                    album = album,
                                    artist = songArtist,
                                    composer = composer,
                                    duration = songDuration,
                                    size = songSize,
                                    title = songTitle,
                                    year = songYear,
                                )
                                playListViewModel.insertSongToPlayList(songEntity = songEntity)
                            }) {
                                Text("Add to playlist")
                            }
                        },
                        text = {
                            Column {
                                LazyColumn {
                                    item {
                                        Text("All Detail ")
                                        Text("Album : $album")
                                        Text("Artist : $songArtist")
                                        Text("Composer : $composer")
                                        Text("Duration of :" + duration.value.toString())
                                        Text("Size : $songSize")
                                        Text("Title : $songTitle")
                                        Text("Year : $songYear")
                                        Text("This $songTitle was published in year $songYear by $songArtist and composed by $composer." +
                                                " It has a duration of ${duration.value} and is $songSize in size.")
                                    }
                                }


                            }
                        }
                    )
                }
                if(playListSelectionDialog.value){
                    AlertDialog(
                        onDismissRequest = { playListSelectionDialog.value = false },
                        title = { Text("Add to playlist") },
                        confirmButton = {
                            Button(onClick = {
                                val songEntity= SongEntity(
                                    path = songPath.toString(),
                                    album = album,
                                    artist = songArtist,
                                    composer = composer,
                                    duration = songDuration,
                                    size = songSize,
                                    title = songTitle,
                                    year = songYear,
                                    albumId = albumID,
                                )
                                playListViewModel.insertSongToPlayList(songEntity = songEntity)
                                playListSelectionDialog.value = false
                            }) {
                                Text("Add to playlist Default")
                            }
                        },
                        dismissButton = {
                            Button(onClick = {
                                playListSelectionDialog.value = false
                            }) {
                                Text("Close")
                            }
                        },
                        text = {
                            Column {
                                LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                                    items(playListState.value.data) { playListTable ->
                                        Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                                            Button(
                                                onClick = {
                                                    val songAlreadyExists = getAllPlayListSongsState.value.data.any {
                                                        it.title == songTitle && it.playListID== playListTable.id
                                                    }

                                                    if (songAlreadyExists) {
                                                        showToastMessage(context, "Song Already Exists in ${playListTable.playListName}", Constants.TOASTERROR)
                                                    } else {
                                                        val playListSong = PlayListSongMapper(
                                                            playListID = playListTable.id,
                                                            path = songPath.orEmpty(),
                                                            album = album,
                                                            artist = songArtist,
                                                            composer = composer,
                                                            duration = songDuration,
                                                            size = songSize,
                                                            title = songTitle,
                                                            year = songYear,
                                                            albumId = albumID,
                                                            lyrics = "Unknown"
                                                        )
                                                        playListViewModel.upsertPlayListSongs(playListSong)
                                                        showToastMessage(context, "Song Added to ${playListTable.playListName}", Constants.TOASTSUCCESS)
                                                        playListSelectionDialog.value = false

                                                    }


                                                },
                                                modifier = Modifier.fillMaxWidth(0.9f)
                                            ) {
                                                Text(playListTable.playListName)
                                            }
                                        }

                                    }
                                }

                            }
                        }
                    )

                }

            }
        }
 }
