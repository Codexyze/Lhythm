package com.example.lhythm.presentation.Screens

import android.content.ContentUris
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.lhythm.R
import com.example.lhythm.constants.Constants
import com.example.lhythm.data.Local.FavSongEntity
import com.example.lhythm.presentation.Utils.LoadingScreen
import com.example.lhythm.presentation.Utils.formatDuration
import com.example.lhythm.presentation.Utils.showToastMessage
import com.example.lhythm.presentation.ViewModels.FavSongViewModel
import com.example.lhythm.presentation.ViewModels.MediaManagerViewModel
import com.example.lhythm.ui.theme.WhiteColor

@Composable
fun FavSongScreen(favSongsViewModel: FavSongViewModel = hiltViewModel(),mediaManagerViewModel: MediaManagerViewModel = hiltViewModel()) {
    val liked = rememberSaveable { mutableStateOf(true) }
    val delFavSongState = favSongsViewModel.deleteFavSongState.collectAsState()
    LaunchedEffect(Unit) {
        favSongsViewModel.getAllFavSong()
    }
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
            LazyColumn(modifier = Modifier.fillMaxSize()){
                items(favSongsState.value.data) {favSong->
                    Log.d("IMAGEURI",favSong.imagePersonal.toString())
                    // EachFavItem(favSong = favSongs)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                mediaManagerViewModel.playMusic(favSong.path.toUri())

                            },
                        elevation = CardDefaults.elevatedCardElevation(8.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        EachFavItemScreen(favSong = favSong)


                    }
                }

            }
        }else->{
            NoSongsFoundScreen()
        }
    }

}

@Composable
fun EachFavItemScreen(favSong: FavSongEntity,favSongsViewModel: FavSongViewModel = hiltViewModel()) {
    val imageUri = rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val alertDialogue = rememberSaveable {mutableStateOf(false) }
    val favSongState = favSongsViewModel.getAllFavSongState.collectAsState()
    val mediaPicker = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) {
        if(it==null){
            showToastMessage(context = context, type = Constants.TOASTWARNING, text = "Please select an image")

        }else{
            imageUri.value = it.toString()
            showToastMessage(context = context,type = Constants.TOASTSUCCESS,text = "Image selected successfully")
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxSize(0.25f)) {
                val songUri = if(favSong.albumId.isNullOrEmpty()){
                    null
                }else{
                    ContentUris.withAppendedId(
                        Uri.parse("content://media/external/audio/albumart"),
                        favSong.albumId.toLong()
                    )
                }

                Log.d("ImagePersonal",favSong.albumId.toString())
                if(songUri==null){
                    Image(
                        painter = painterResource(R.drawable.lythmlogoasset),
                        contentDescription = "Image",
                        modifier = Modifier.weight(0.25f)
                    )
                }else{
                    AsyncImage(
                        model = songUri,
                        placeholder = painterResource(R.drawable.lythmlogoasset),
                        contentDescription = "Personal Image",
                        error = painterResource(R.drawable.noalbumimgasset),
                        modifier = Modifier.weight(0.25f)
                    )
                }

            }

            Column(modifier = Modifier.fillMaxSize(0.75f)) {
                Row(modifier = Modifier.fillMaxWidth())  {
                    Text(favSong.title.toString(), maxLines = 2)
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = favSong.artist.toString(), maxLines = 1)
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = favSong.composer.toString(), maxLines = 1)
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = formatDuration(favSong.duration!!.toLong()), maxLines = 1)
                }
                Row(
                    modifier = Modifier, horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
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
                        tint = WhiteColor

                    )
                    Icon(
                        imageVector = Icons.Filled.Image,
                        contentDescription = "Picture",
                        modifier = Modifier.weight(1f).clickable {
                            //add picture here
                           alertDialogue.value = true

                        },
                        tint = WhiteColor

                    )
                }
            }
        }
        if(alertDialogue.value){
            AlertDialog(
                text = {
                    if(imageUri.value!="" || imageUri.value!=null){
                        Column {
                            AsyncImage(
                                model = imageUri.value,
                                placeholder = painterResource(R.drawable.lythmlogoasset),
                                contentDescription = "Personal Image",
                                error = painterResource(R.drawable.noalbumimgasset)
                            )
                        }
                    }else{
                        Text("Select Image")

                    }

                },
                onDismissRequest = {
                    alertDialogue.value = false
                },
                confirmButton = {
                    Button(
                        onClick = {
                            mediaPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        }
                    ) {
                        Text("Open Image")
                    }
                }
                ,
                dismissButton = {
                    Button(
                        onClick = {
                                if(imageUri.value !=""){
                                val favSong = FavSongEntity(
                                    id = favSong.id,
                                    title = favSong.title,
                                    artist = favSong.artist,
                                    composer = favSong.composer,
                                    duration = favSong.duration,
                                    path = favSong.path,
                                    imagePersonal = imageUri.value,
                                    size = favSong.size,
                                    album = favSong.album,
                                    year = favSong.year,
                                    albumId = favSong.albumId,
                                    lyrics = favSong.lyrics
                                )
                                favSongsViewModel.insertOrUpdateFavSong(favSongEntity = favSong)
                                showToastMessage(context = context, text ="Added Image", type = Constants.TOASTSUCCESS )
                            }else{
                                showToastMessage(context = context, text ="Null image", type = Constants.TOASTERROR )
                            }
                        }
                    ) {
                        Text("Apply")
                    }
                }
            )
        }


    }

}
