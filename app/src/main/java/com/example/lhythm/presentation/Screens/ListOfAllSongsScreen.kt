package com.example.lhythm.presentation.Screens

import android.Manifest
import android.content.Intent
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lhythm.core.MusicForeground.MusicForeground
import com.example.lhythm.presentation.Utils.LoadingScreen
import com.example.lhythm.presentation.Utils.checkPermission
import com.example.lhythm.presentation.Utils.formatDuration
import com.example.lhythm.presentation.ViewModels.GetAllSongViewModel
import com.example.lhythm.presentation.ViewModels.MediaManagerViewModel
import com.example.lhythm.ui.theme.BlackColor
import com.shashank.sony.fancytoastlib.FancyToast

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun  ListOfAllSongsScreen(viewmodel: GetAllSongViewModel = hiltViewModel(),navController: NavController,
                          mediaPlayerViewModel: MediaManagerViewModel= hiltViewModel()){
    val state = viewmodel.getAllSongsState.collectAsState()

    //
    val context = LocalContext.current
    val permissionstate= rememberSaveable { mutableStateOf(false) }
    val permission = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        if(it){
            permissionstate.value = it
        }else{

        }
    }
    LaunchedEffect(Unit) {
        if( checkPermission(context = context, permission = Manifest.permission.POST_NOTIFICATIONS)){
            FancyToast.makeText(
                context, "Loading Songs",
                FancyToast.LENGTH_SHORT,
                FancyToast.CONFUSING, false
            ).show()
        }else{
            permission.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

    }
//
    if(state.value.isLoading){
        LoadingScreen()
    }else if(!state.value.error.isNullOrEmpty()){
        Text("Error Loading Sonhs")
    }else if(!state.value.data.isNullOrEmpty()){
        LazyColumn(modifier = Modifier.background(color = BlackColor)) {
            items(state.value.data) { song ->
                Box(modifier = Modifier.wrapContentSize().clickable{
                    // navController.navigate(MUSICPLAYERSCREEN(path = song.path))
                   // mediaPlayerViewModel.playMusic(song.path.toUri())
                    val intent = Intent(context, MusicForeground::class.java)
                    context.startForegroundService(intent)
                    // THEN, play music
                    mediaPlayerViewModel.playMusic(song.path.toUri()) // <- use actual uri here
                }){

                    EachSongItemLook(songTitle = song.title, songArtist = song.artist, songDuration = song.duration, songYear = song.year)
                }

            }

        }
    }else{
        Text("No Songs Found")
    }

}

@Composable
fun EachSongItemLook(songTitle: String?="", songArtist: String?="", songDuration: String?="", songYear: String?="") {
    val  duration = rememberSaveable{ mutableStateOf("0") }
    Card (modifier = Modifier.fillMaxWidth().padding(8.dp), elevation = CardDefaults.elevatedCardElevation(8.dp)
    , shape = RoundedCornerShape(16.dp)
    ){
        Column(modifier = Modifier.padding(8.dp)) {
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
        }
    }

}