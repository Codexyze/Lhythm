package com.example.lhythm.presentation.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lhythm.data.Local.SongEntity
import com.example.lhythm.presentation.Utils.LoadingScreen
import com.example.lhythm.presentation.ViewModels.MediaManagerViewModel
import com.example.lhythm.presentation.ViewModels.PlayListViewModel
import com.shashank.sony.fancytoastlib.FancyToast


@Composable
fun PlayListExample(navController: NavController,playListViewModel: PlayListViewModel= hiltViewModel()) {
    LaunchedEffect(Unit) {
        playListViewModel.getSongsFromPlayList()
    }

    val getAllSongsState = playListViewModel.getSongFromPlayListState.collectAsState()

    if(getAllSongsState.value.isLoading){
        LoadingScreen()
    }else if(!getAllSongsState.value.error.isNullOrEmpty()){
        Text("Error loading songs ${getAllSongsState.value.error}")
    }else{

            val list=getAllSongsState.value.data
            LazyColumn {
                items(count = list.size) {int->
                    val listelementvalue=list[int]
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
                    )

                }
            }


    }

}

@Composable
fun EachPlayListItem(
    id: Int,
    path : String="",
    album : String="Unknown",
    artist : String="Unknown",
    composer : String="Unknown",
    duration : String="0",
    size : String="0",
    title : String="Unknown",
    year : String="0",
    mediaPlayerViewModel: MediaManagerViewModel= hiltViewModel(),
    playListViewModel: PlayListViewModel=hiltViewModel()
) {
    val deletePlayListSongState = playListViewModel.getSongFromPlayListState.collectAsState()
    val context = LocalContext.current
    Card (modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp).clickable{
            if(path.isNullOrEmpty()){
                FancyToast.makeText(
                    context, "Error Loading Song",
                    FancyToast.LENGTH_SHORT,
                    FancyToast.ERROR, false
                ).show()
            }else{
               mediaPlayerViewModel.playMusic(path!!.toUri())
            }
        }, elevation = CardDefaults.elevatedCardElevation(8.dp)
        , shape = RoundedCornerShape(16.dp)
    ){
        Column(modifier = Modifier.padding(8.dp)) {
            Row {
                Text(title, maxLines = 2)
            }
            Row {
                Text(artist, maxLines = 1)
            }
            Row {
                Text(year, maxLines = 1)
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Info",
                    modifier = Modifier.clickable{
                        val songEntity= SongEntity(
                            id=id,
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
                        }else if (deletePlayListSongState.value.isLoading){
                            FancyToast.makeText(
                                context, "Deleting...",
                                FancyToast.LENGTH_SHORT,
                                FancyToast.WARNING, false
                            ).show()
                        }else if(deletePlayListSongState.value.data!=null){
                            playListViewModel.getSongsFromPlayList()
                            FancyToast.makeText(
                                context, "Successfully Deleted",
                                FancyToast.LENGTH_SHORT,
                                FancyToast.SUCCESS, false
                            ).show()
                        }
                    }
                )
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Info",
                    modifier = Modifier.clickable{
                        val songEntity= SongEntity(
                            id=id,
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
                        }else if (deletePlayListSongState.value.isLoading){
                            FancyToast.makeText(
                                context, "Deleting...",
                                FancyToast.LENGTH_SHORT,
                                FancyToast.WARNING, false
                            ).show()
                        }else if(deletePlayListSongState.value.data!=null){
                            playListViewModel.getSongsFromPlayList()
                            FancyToast.makeText(
                                context, "Successfully Deleted",
                                FancyToast.LENGTH_SHORT,
                                FancyToast.SUCCESS, false
                            ).show()
                        }
                    }
                )

            }

        }
    }

}
