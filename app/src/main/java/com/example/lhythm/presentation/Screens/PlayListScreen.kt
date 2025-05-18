package com.example.lhythm.presentation.Screens

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lyrics
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lhythm.R
import com.example.lhythm.data.Local.FavSongEntity
import com.example.lhythm.data.Local.SongEntity
import com.example.lhythm.presentation.Navigation.LYRICSFULLSCREEN
import com.example.lhythm.presentation.Utils.LoadingScreen
import com.example.lhythm.presentation.ViewModels.FavSongViewModel
import com.example.lhythm.presentation.ViewModels.MediaManagerViewModel
import com.example.lhythm.presentation.ViewModels.PlayListViewModel
import com.example.lhythm.ui.theme.cardColor
import com.shashank.sony.fancytoastlib.FancyToast


@Composable
fun PlayListExample(navController: NavController,playListViewModel: PlayListViewModel= hiltViewModel()
,mediaManagerViewModel: MediaManagerViewModel =hiltViewModel(), ) {
    LaunchedEffect(Unit) {
        playListViewModel.getSongsFromPlayList()
    }
    val context = LocalContext.current
    var listOfSongs = remember { mutableListOf<Uri>() }//playlist
    val searchSong = rememberSaveable { mutableStateOf("") }
    val searchSongState = playListViewModel.searchSongState.collectAsState()
    val isSearching = rememberSaveable { mutableStateOf(false) }

    val getAllSongsState = playListViewModel.getSongFromPlayListState.collectAsState()
    when {
        getAllSongsState.value.isLoading -> {
            LoadingScreen()
        }

        !getAllSongsState.value.error.isNullOrEmpty() -> {
            Text("Error loading songs ${getAllSongsState.value.error}")
        }

        getAllSongsState.value.data == null -> {
            Text("No songs found in playlist")
        }

        else -> {
            if (getAllSongsState.value.data.isNotEmpty()) {
                listOfSongs.addAll(getAllSongsState.value.data.map { it.path.toUri() })
            }
            var list = getAllSongsState.value.data
            Log.d("PLAYLIST", "${listOfSongs.toString()}")
            Column(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background)) {
                Row(modifier = Modifier.weight(0.10f), horizontalArrangement = Arrangement.Center) {
                    OutlinedTextField(
                        value = searchSong.value,
                        onValueChange = {
                            searchSong.value = it
                        },
                        label = {
                            Text("Search Song")
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
                            playListViewModel.searchFromPlayList(searchSong.value)
                            isSearching.value = true
                            Log.d("SEARCHLOG", searchSongState.value.data.toString())

                        },
                        modifier = Modifier.weight(0.15f)
                    ) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                if (isSearching.value) {
                    LazyColumn(modifier = Modifier.weight(0.90f)) {
                        if (!searchSongState.value.data.isNullOrEmpty()) {
                            items(searchSongState.value.data) { song ->
                                EachItemFromSearchScreen(song = song, navController = navController)
                            }
                            item {
                                Button(
                                    onClick = {
                                        isSearching.value = false
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                    Text("Go Back")
                                }
                            }
                        } else {
                            item {
                                Text("No results found")
                            }
                        }

                    }
                } else {
                    LazyColumn(modifier = Modifier.weight(0.90f)) {

                        items(count = list.size) { int ->
                            val listelementvalue = list[int]
                            // listOfSongs.add(listelementvalue.path.toUri())

                            EachPlayListItem(
                                id = listelementvalue.id,
                                path = listelementvalue.path,
                                album = listelementvalue.album.toString(),
                                artist = listelementvalue.artist.toString(),
                                composer = listelementvalue.composer.toString(),
                                duration = listelementvalue.duration.toString(),
                                size = listelementvalue.size.toString(),
                                title = listelementvalue.title.toString(),
                                year = listelementvalue.year.toString(),
                                lyricsString = listelementvalue.lyrics.toString(),
                                navController = navController,
                                playListUris = if (!listOfSongs.isNullOrEmpty()) {
                                    listOfSongs
                                } else {
                                    return@items
                                },
                                indexOfCurrentSong = int
                            )

                        }
                    }

                }
            }
        }
    }
}

@Composable
fun EachPlayListItem(
    id: Int,
    path: String,
    album: String = "Unknown",
    artist: String = "Unknown",
    composer: String = "Unknown",
    duration: String = "0",
    size: String = "0",
    title: String = "Unknown",
    year: String = "0",
    lyricsString: String = "",
    mediaPlayerViewModel: MediaManagerViewModel = hiltViewModel(),
    playListViewModel: PlayListViewModel = hiltViewModel(),
    favSongViewModel: FavSongViewModel = hiltViewModel(),
    playListUris: List<Uri>,
    indexOfCurrentSong: Int = 0,
    navController: NavController

) {
    val lyrics=rememberSaveable { mutableStateOf("") }
    val showLyricsSavingDailog = rememberSaveable { mutableStateOf(false) }
    val showLyricsDialogue = rememberSaveable { mutableStateOf(false) }
    val deletePlayListSongState = playListViewModel.getSongFromPlayListState.collectAsState()
    val context = LocalContext.current
    val insertState=playListViewModel.insertSongToPlaListState.collectAsState()
    val addToFavState= favSongViewModel.inserOrUpdateFavState.collectAsState()
    val lyricsState = playListViewModel.getLyricsFromPlayListState.collectAsState()


   if (insertState.value.isLoading || deletePlayListSongState.value.isLoading||addToFavState.value.isLoading){
       LoadingScreen()
   }else if(insertState.value.error!=null || deletePlayListSongState.value.error!=null||addToFavState.value.error!=null){
       Text("Error ${insertState.value.error}")
   }else{
       Card (modifier = Modifier
           .fillMaxWidth()
           .padding(8.dp)
           .clickable {
               if (path.isNullOrEmpty()) {
                   FancyToast.makeText(
                       context, "Error Loading Song",
                       FancyToast.LENGTH_SHORT,
                       FancyToast.ERROR, false
                   ).show()
               } else {

                   //Play single song here ....
                   //mediaPlayerViewModel.playMusic(path!!.toUri())
                   if (!playListUris.isNullOrEmpty()) {
                       // mediaPlayerViewModel.playPlayList(listOfSongsUri = playListUris)
                       mediaPlayerViewModel.playPlayListWithIndex(
                           listOfSongsUri = playListUris,
                           index = indexOfCurrentSong,
                           context = context
                       )
                   } else {
                       FancyToast.makeText(
                           context, "Error Loading Song",
                           FancyToast.LENGTH_SHORT,
                           FancyToast.ERROR, false
                       ).show()
                   }

               }
           }, elevation = CardDefaults.elevatedCardElevation(8.dp)
           , shape = RoundedCornerShape(16.dp),
           colors = CardDefaults.cardColors(
               containerColor = cardColor
           )
       ){
           Column(modifier = Modifier.padding(8.dp)) {

               Row(modifier = Modifier.padding(5.dp)) {
                   Image(
                       painter = painterResource(R.drawable.lythmlogoasset),
                       contentDescription = "Logo",
                       modifier = Modifier.weight(0.25f)
                   )
                   Spacer(modifier = Modifier.width(20.dp))
                   Column(modifier = Modifier.weight(0.75f)) {
                       Text(title, maxLines = 2, color = MaterialTheme.colorScheme.secondary)
                       Row {
                           Text(artist, maxLines = 1, color = MaterialTheme.colorScheme.secondary)
                       }
                       Row {
                           Text(year, maxLines = 1, color = MaterialTheme.colorScheme.secondary)
                       }
                       Row {
                           if(lyricsString== "Empty Lyrics"){
                               Text(lyricsString)
                           }else{

                           }

                       }
                       Row {
                           Icon(
                               imageVector = Icons.Filled.Favorite, contentDescription = "Favorite",
                               modifier = Modifier
                                   .weight(1f)
                                   .clickable {
                                       val favSongEntity =
                                           FavSongEntity(
                                               id = id,
                                               size = size,
                                               title = title,
                                               artist = artist,
                                               duration = duration,
                                               year = year,
                                               album = album,
                                               path = path,
                                               composer = composer,
                                               lyrics = lyricsString,
                                               albumId = "",
                                           )
                                       favSongViewModel.insertOrUpdateFavSong(favSongEntity = favSongEntity)
                                       if (!addToFavState.value.data.isNullOrEmpty()) {
                                           FancyToast.makeText(
                                               context, "Song Added to Favorites",
                                               FancyToast.LENGTH_SHORT,
                                               FancyToast.SUCCESS, false
                                           ).show()
                                       }

                                   },
                               tint = MaterialTheme.colorScheme.primary
                           )
                           Icon(imageVector = Icons.Filled.Delete, contentDescription = "Info",
                               modifier = Modifier
                                   .weight(1f)
                                   .clickable {
                                       val songEntity = SongEntity(
                                           id = id,
                                           size = size,
                                           title = title,
                                           artist = artist,
                                           duration = duration,
                                           year = year,
                                           album = album,
                                           path = path,
                                           composer = composer
                                       )
                                       playListViewModel.deleteSongFromPlayList(songEntity = songEntity)
                                       if (!deletePlayListSongState.value.error.isNullOrEmpty()) {
                                           FancyToast.makeText(
                                               context, "Error Deleting Song",
                                               FancyToast.LENGTH_SHORT,
                                               FancyToast.ERROR, false
                                           ).show()
                                       } else if (deletePlayListSongState.value.isLoading) {
                                           FancyToast.makeText(
                                               context, "Deleting...",
                                               FancyToast.LENGTH_SHORT,
                                               FancyToast.WARNING, false
                                           ).show()
                                       } else if (deletePlayListSongState.value.data != null) {
                                           playListViewModel.getSongsFromPlayList()
                                           FancyToast.makeText(
                                               context, "Successfully Deleted",
                                               FancyToast.LENGTH_SHORT,
                                               FancyToast.SUCCESS, false
                                           ).show()
                                       }
                                   },tint = MaterialTheme.colorScheme.primary
                           )
                           Icon(
                               imageVector = Icons.Filled.Lyrics, contentDescription = "lyrics",
                               modifier = Modifier
                                   .weight(1f)
                                   .clickable {

                                       showLyricsSavingDailog.value = true

                                   },
                               tint = MaterialTheme.colorScheme.primary
                           )
                           Icon(
                               imageVector = Icons.Filled.TextFields, contentDescription = "lyrics",
                               modifier = Modifier
                                   .weight(1f)
                                   .clickable {
                                       showLyricsDialogue.value = true
                                   },
                               tint = MaterialTheme.colorScheme.primary
                           )
                       }
                   }

               }


               if (showLyricsSavingDailog.value){
                   AlertDialog(
                       onDismissRequest = {
                           showLyricsSavingDailog.value = false

                       },
                       confirmButton = {

                           Button(onClick = {
                               if(!lyrics.value.isNullOrEmpty()){
                                   val songEntity= SongEntity(
                                       id=id,
                                       size = size,
                                       title = title,
                                       artist = artist,
                                       duration = duration,
                                       year = year,
                                       album = album,
                                       path = path,
                                       composer = composer,
                                       lyrics = lyrics.value
                                   )
                                   playListViewModel.insertSongToPlayList(songEntity = songEntity)
                               }else{
                                   FancyToast.makeText(
                                       context, "Fill lyrics...",
                                       FancyToast.LENGTH_SHORT,
                                       FancyToast.ERROR, false
                                   ).show()
                               }


                           }) {
                               Text("Embed Lyrics")
                           }

                       },
                       dismissButton = {

                           Button(onClick = {
                               //Show lyrics here
                               showLyricsSavingDailog.value = false
                           }) {
                               Text("Full Screen")
                           }
                       },
                       text = {
                           Column {
                               LazyColumn {
                                   item {
                                       Text("Add Your Lyrics")
                                       Text(lyrics.value)
                                       OutlinedTextField(
                                           onValueChange = {
                                               lyrics.value = it
                                           },
                                           value = lyrics.value,
                                           label = {
                                               Text("Lyrics")
                                           }
                                       )


                                   }
                               }
                           }
                       }
                   )



               }
               if (showLyricsDialogue.value){
                   playListViewModel.getLyricsFromPlayList(id = id)
                   AlertDialog(
                       text = {
                           when{
                               lyricsState.value.isLoading->{
                                   Text("Loading...")
                               }
                               !lyricsState.value.error.isNullOrEmpty()->{
                                   Text("Error")
                               }
                               !lyricsState.value.data.isNullOrEmpty()->{
                                   LazyColumn(modifier = Modifier.padding(12.dp)) {
                                       item {
                                           if(lyricsState.value.data =="Empty Lyrics"){
                                               Text("No Lyrics Found")
                                           }else{
                                               Text(text = lyricsState.value.data)
                                           }

                                       }
                                   }
                               }
                           }

                       },
                       onDismissRequest = {



                       },
                       confirmButton = {
                           Button(
                               onClick = {

                               }
                           ) {
                               Text("Delete")

                           }
                       },
                       dismissButton = {
                           Button(
                               onClick = {
                                   showLyricsDialogue.value = false
                               }
                           ) {
                               Text("Close")
                           }
                       }
                   )
               }


           }
       }

   }
}

@Composable
fun EachItemFromSearchScreen(song: SongEntity,
                             mediaPlayerViewModel: MediaManagerViewModel= hiltViewModel(),
                             navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
               mediaPlayerViewModel.playMusic(song.path.toUri())

            }) {
        Column(modifier = Modifier.padding(8.dp)) {

            Row(modifier = Modifier.padding(5.dp)) {
                Image(
                    painter = painterResource(R.drawable.lythmlogoasset),
                    contentDescription = "Logo",
                    modifier = Modifier.weight(0.25f)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Column(modifier = Modifier.weight(0.75f)) {
                    Text(song.title.toString(), maxLines = 2)
                    Row {
                        Text(song.artist.toString(), maxLines = 1)
                    }
                    Row {
                        Text(song.year.toString(), maxLines = 1)
                    }
                    Row {
                       //Lyrics



                    }
                    Row {
                        Icon(
                            imageVector = Icons.Filled.Favorite, contentDescription = "Favorite",
                            modifier = Modifier
                                .weight(1f)
                                .clickable {


                                }
                        )
                        Icon(
                            imageVector = Icons.Filled.Delete, contentDescription = "Info",
                            modifier = Modifier
                                .weight(1f)
                                .clickable {


                                }
                        )
                        Icon(
                            imageVector = Icons.Filled.Lyrics, contentDescription = "lyrics",
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    navController.navigate(LYRICSFULLSCREEN(lyrics = song.lyrics.toString()))

                                }
                        )
                        Icon(
                            imageVector = Icons.Filled.TextFields, contentDescription = "lyrics",
                            modifier = Modifier
                                .weight(1f)
                                .clickable {

                                }
                        )
                    }
                }

            }
        }
    }
}