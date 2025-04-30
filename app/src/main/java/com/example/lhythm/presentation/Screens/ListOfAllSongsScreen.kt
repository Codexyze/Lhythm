package com.example.lhythm.presentation.Screens

import android.app.PendingIntent
import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Spinner
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.core.content.ContextCompat.startForegroundService
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.lhythm.R
import com.example.lhythm.core.BroadCastReciver.StopServiceReciver
import com.example.lhythm.core.MusicForeground.MusicForeground
import com.example.lhythm.data.Local.FavSongEntity
import com.example.lhythm.data.Local.SongEntity
import com.example.lhythm.data.Song.Song
import com.example.lhythm.presentation.Utils.LoadingScreen
import com.example.lhythm.presentation.Utils.formatDuration
import com.example.lhythm.presentation.ViewModels.FavSongViewModel
import com.example.lhythm.presentation.ViewModels.GetAllSongViewModel
import com.example.lhythm.presentation.ViewModels.MediaManagerViewModel
import com.example.lhythm.presentation.ViewModels.PlayListViewModel
import com.example.lhythm.ui.theme.BlackColor
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
                            Text("Search Song")
                        },
                        modifier = Modifier.weight(0.85f)
                    )
                    IconButton(
                        onClick = {
                            isSearching.value = true

                        }
                    ) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
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
                                   , composer = songsFiltered.composer)
                               listOfPlayListUri.add(songsFiltered.path.toUri())
                           }
                        }
                    }


                }else{
                    LazyColumn(modifier = Modifier.background(color = BlackColor).weight(0.90f)) {
                        items(state.value.data) { song ->
                            Box(modifier = Modifier.wrapContentSize().clickable{
                                // navController.navigate(MUSICPLAYERSCREEN(path = song.path))
                                // mediaPlayerViewModel.playMusic(song.path.toUri())
//                    val intent = Intent(context, MusicForeground::class.java)
//                    context.startForegroundService(intent)
                                // THEN, play music
                                //   mediaPlayerViewModel.playMusic(song.path.toUri()) // <- use actual uri here
                            }){

                                EachSongItemLook(songid = song.id, songTitle = song.title, songArtist = song.artist,
                                    songDuration = song.duration,
                                    songYear = song.year,songPath= song.path,
                                    songSize = song.size, album = song.album,
                                    songUriList = listOfAllSongs
                                    , composer = song.composer, albumID = song.albumId,
                                    index = state.value.data.indexOf(song))
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
                     favSongViewModel: FavSongViewModel=hiltViewModel()) {
         val context = LocalContext.current
         val showDialogueBox = rememberSaveable { mutableStateOf(false) }
        val inserToPlayListState = playListViewModel.insertSongToPlaListState.collectAsState()
         val insertOrUpdateFavSongState =favSongViewModel.inserOrUpdateFavState.collectAsState()
         val getAllPlayListSongs =  playListViewModel.getSongFromPlayListState.collectAsState()
         val favSongsState = favSongViewModel.getAllFavSongState.collectAsState()
        val  duration = rememberSaveable{ mutableStateOf("0") }
        LaunchedEffect(getAllPlayListSongs.value.data) {
            playListViewModel.getSongsFromPlayList()
            favSongViewModel.getAllFavSong()
        }


        Card (modifier = Modifier.fillMaxWidth().padding(8.dp).clickable{
            if (!songUriList.isNullOrEmpty()){
                mediaManagerViewModel.playPlayListWithIndex(listOfSongsUri = songUriList, index = index, context = context)
                    // val intent = Intent(context, MusicForeground::class.java)
                 //startForegroundService(context, intent)

            }
            else if (songPath.isNullOrEmpty()){
                FancyToast.makeText(
                    context, "Error Loading Song",
                    FancyToast.LENGTH_SHORT,
                    FancyToast.ERROR, false
                ).show()
            }else{
                mediaManagerViewModel.playMusic(songPath.toUri())
            }


        }, elevation = CardDefaults.elevatedCardElevation(8.dp)
            , shape = RoundedCornerShape(16.dp)
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


//                    Image(
//                        painter = painterResource(R.drawable.lythmlogoasset),
//                        contentDescription = "Logo",
//                        modifier = Modifier.weight(0.25f)
//                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Column(modifier = Modifier.weight(0.75f)) {
                        Row {
                            songTitle?.let { Text(it, maxLines = 2) }

                        }

                        Row {
                            songArtist?.let { Text(it, maxLines = 1) }
                        }
                        Row{
                            songYear?.let { Text("year : $it", maxLines = 1) }

                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Absolute.SpaceEvenly){
                            songDuration?.let {
                                duration.value = formatDuration(it.toLong())
                                Text(duration.value.toString(), maxLines = 1) }


                        }
                        Row(modifier = Modifier, horizontalArrangement = Arrangement.Absolute.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Filled.Info, contentDescription = "Info",
                                modifier = Modifier.weight(1f).clickable{
                                    showDialogueBox.value = true
                                }
                            )
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add to playlist",
                                modifier = Modifier.weight(1f).clickable{
                                    val data = getAllPlayListSongs.value.data
                                    //check if exists
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
                                    val doesExist: Boolean =data.any {
                                        it.title== songEntity.title
                                    }
                                    Log.d("DOESEXIST", "$doesExist")
                                    if(doesExist){
                                        FancyToast.makeText(
                                            context, "Song Already Exists",
                                            FancyToast.LENGTH_SHORT,
                                            FancyToast.WARNING, false
                                        ).show()
                                    }else{
                                        playListViewModel.insertSongToPlayList(songEntity = songEntity)
                                    }
                                }
                            )
                            Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Favorite",
                                modifier = Modifier.weight(1f).clickable{
                                    //Fav impl here
                                    val favSongEntity= FavSongEntity(
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
                                    if(!insertOrUpdateFavSongState.value.data.isNullOrEmpty()){
                                        FancyToast.makeText(
                                            context, "Saved",
                                            FancyToast.LENGTH_SHORT,
                                            FancyToast.SUCCESS, false
                                        ).show()
                                    }else if(!insertOrUpdateFavSongState.value.error.isNullOrEmpty()){
                                        FancyToast.makeText(
                                            context, "Error Saving",
                                            FancyToast.LENGTH_SHORT,
                                            FancyToast.ERROR, false
                                        ).show()
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
                                        Text("Duration of :" +
                                                duration.value.toString())
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

            }
        }
 }

