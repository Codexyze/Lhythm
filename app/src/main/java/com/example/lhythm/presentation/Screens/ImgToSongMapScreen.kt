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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.lhythm.R
import com.example.lhythm.presentation.Utils.LoadingScreen
import com.example.lhythm.presentation.Utils.formatDuration
import com.example.lhythm.presentation.ViewModels.GetSongCategoryViewModel
import com.example.lhythm.presentation.ViewModels.MediaManagerViewModel
import com.example.lhythm.ui.theme.BlackColor
import com.example.lhythm.ui.theme.cardColor

@Composable
fun ImageToSongMapScreen(viewmodel: GetSongCategoryViewModel= hiltViewModel(),
                         navController: NavController,
                         mediaPlayerViewModel: MediaManagerViewModel= hiltViewModel()) {
    val getAllSongsASCState = viewmodel.songsInASCOrderState.collectAsState()
    val listOfAscSongUri = remember { mutableListOf<Uri>() }

    when{
        getAllSongsASCState.value.isLoading->{
            LoadingScreen()
        }
        !getAllSongsASCState.value.error.isNullOrBlank()->{
            Text("Error Loading Songs ${getAllSongsASCState.value.error}")
        }
        !getAllSongsASCState.value.data.isNullOrEmpty()->{
            getAllSongsASCState.value.data.forEach {song->
                listOfAscSongUri.add(song.path.toUri())

            }
            Column(modifier = Modifier
                .fillMaxSize()
                .background(color = BlackColor)) {
                LazyColumn(modifier = Modifier.background(color = BlackColor)) {
                    items(getAllSongsASCState.value.data) { song ->
                        Box(modifier = Modifier
                            .wrapContentSize()
                            .clickable {
                              //Here on click mapp


                            }){

                            EachSongToImgItemLook(songTitle = song.title, songPath = song.path, songArtist = song.artist,
                                songDuration = song.duration, songYear = song.year,
                                albumID = song.albumId,
                                songUriList = listOfAscSongUri,
                                index = getAllSongsASCState.value.data.indexOf(song),
                                navController = navController)
                        }

                    }

                }
            }
        }
        else->{
            NoSongsFoundScreen()
        }
    }

}


@Composable
fun EachSongToImgItemLook(songid: String="",  songTitle: String?="", songArtist: String?="",
                     songDuration: String?="",
                     songYear: String?="",
                     songPath: String?="",
                     songSize: String?="",
                     album: String?="Unknown",
                     composer: String?="Unknown",
                     albumID: String?=null,
                     songUriList: List<Uri>?=null,
                     index: Int = 0,
                     mediaManagerViewModel: MediaManagerViewModel=hiltViewModel(),
                     navController: NavController
) {


    val context = LocalContext.current
    val showDialogueBox = rememberSaveable { mutableStateOf(false) }
    val  duration = rememberSaveable{ mutableStateOf("0") }


    Card (modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable {
            //Add to room db


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

                }

            }
        }
    }
}






