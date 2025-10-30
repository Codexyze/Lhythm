package com.nutrino.lhythm.presentation.Screens

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.nutrino.lhythm.R
import com.nutrino.lhythm.data.Local.SongToImage
import com.nutrino.lhythm.presentation.Utils.LoadingScreen
import com.nutrino.lhythm.presentation.Utils.formatDuration
import com.nutrino.lhythm.presentation.ViewModels.GetSongCategoryViewModel
import com.nutrino.lhythm.presentation.ViewModels.ImageViewModel
import com.nutrino.lhythm.presentation.ViewModels.MediaManagerViewModel
import com.nutrino.lhythm.ui.theme.cardColor

@Composable
fun ImageToSongMapScreen(viewmodel: GetSongCategoryViewModel= hiltViewModel(),
                         navController: NavController,
                         mediaPlayerViewModel: MediaManagerViewModel= hiltViewModel(),
                         imagePath: String) {
    val getAllSongsASCState = viewmodel.songsInASCOrderState.collectAsState()
    val listOfAscSongUri = remember { mutableListOf<Uri>() }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        when{
            getAllSongsASCState.value.isLoading->{
                LoadingScreen()
            }
            !getAllSongsASCState.value.error.isNullOrBlank()->{
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Error Loading Songs\n${getAllSongsASCState.value.error}",
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(24.dp)
                    )
                }
            }
            !getAllSongsASCState.value.data.isNullOrEmpty()->{
                getAllSongsASCState.value.data.forEach {song->
                    listOfAscSongUri.add(song.path.toUri())
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(color = MaterialTheme.colorScheme.background)
                ) {
                    // Header Section
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                            .padding(horizontal = 20.dp, vertical = 16.dp)
                    ) {
                        Text(
                            text = "Map Songs to Memory",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Select a song to create a memory",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.alpha(0.6f)
                        )
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = MaterialTheme.colorScheme.background)
                    ) {
                        items(getAllSongsASCState.value.data) { song ->
                            Box(modifier = Modifier
                                .wrapContentSize()
                                .clickable {
                                  //Here on click mapp
                                }){
                                EachSongToImgItemLook(
                                    songTitle = song.title,
                                    songPath = song.path,
                                    songArtist = song.artist,
                                    songDuration = song.duration,
                                    songYear = song.year,
                                    albumID = song.albumId,
                                    songUriList = listOfAscSongUri,
                                    index = getAllSongsASCState.value.data.indexOf(song),
                                    navController = navController,
                                    imagePath = imagePath
                                )
                            }
                        }
                    }
                }
            }
            else->{
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    NoSongsFoundScreen()
                }
            }
        }
    }
}

@Composable
fun EachSongToImgItemLook(
    songid: String="",
    songTitle: String?="",
    songArtist: String?="",
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
    navController: NavController,
    imageViewModel: ImageViewModel = hiltViewModel(),
    imagePath: String ?=""
) {
    val context = LocalContext.current
    val showDialogueBox = rememberSaveable { mutableStateOf(false) }
    val  duration = rememberSaveable{ mutableStateOf("0") }
    val noteText = rememberSaveable { mutableStateOf("") }
    val imageToSongMapperState = imageViewModel.mapImgToSong.collectAsState()

    when{
        imageToSongMapperState.value.isLoading->{
            LoadingScreen()
        }
        !imageToSongMapperState.value.error.isNullOrBlank()->{
            Text("Error Loading Songs ${imageToSongMapperState.value.error}")
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable {
                //Add to room db
                showDialogueBox.value = true
            },
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        )
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Album Art
            val songUri = if(albumID==null){
                null
            }else{
                ContentUris.withAppendedId(
                    Uri.parse("content://media/external/audio/albumart"),
                    albumID.toLong()
                )
            }

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                if(songUri!=null) {
                    AsyncImage(
                        model = songUri,
                        error = painterResource(R.drawable.noalbumimgasset),
                        contentDescription = "AlbumArt",
                        modifier = Modifier.fillMaxSize(),
                        placeholder = painterResource(R.drawable.lythmlogoasset),
                        contentScale = ContentScale.Crop
                    )
                }else{
                    Image(
                        painter = painterResource(R.drawable.noalbumimgasset),
                        contentDescription = "Logo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Song Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Song Title
                songTitle?.let {
                    Text(
                        text = it,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Artist
                songArtist?.let {
                    Text(
                        text = it,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 14.sp,
                        modifier = Modifier.alpha(0.7f)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Duration and Year
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    songDuration?.let {
                        duration.value = formatDuration(it.toLong())
                        Text(
                            text = duration.value.toString(),
                            maxLines = 1,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    songYear?.let {
                        Text(
                            text = it,
                            maxLines = 1,
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 12.sp,
                            modifier = Modifier.alpha(0.6f)
                        )
                    }
                }
            }

            // Delete Icon Button
            IconButton(
                onClick = {
                    //del here
                },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }

    // Memory Note Dialog
    if (showDialogueBox.value){
        AlertDialog(
            onDismissRequest = {
                showDialogueBox.value = false
            },
            title = {
                Text(
                    "Add a Memory Note",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Write your thoughts and memories about this song",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .alpha(0.6f)
                            .padding(bottom = 16.dp),
                        lineHeight = 18.sp
                    )

                    OutlinedTextField(
                        value = noteText.value,
                        onValueChange = {
                            noteText.value = it
                        },
                        placeholder = {
                            Text(
                                "Add your note...",
                                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f),
                            focusedTextColor = MaterialTheme.colorScheme.secondary,
                            unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                            cursorColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(12.dp),
                        maxLines = 6
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if(noteText.value.isNotEmpty()){
                            val songToImg = SongToImage(
                                songPath = songPath.toString(),
                                notes = noteText.value.toString(),
                                songTitle = songTitle.toString(),
                                songAuthor = songArtist.toString(),
                                imgPath = imagePath.toString()
                            )
                            imageViewModel.mapImgToSong(songToImage = songToImg)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Save Memory",
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialogueBox.value = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Cancel",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            },
            shape = RoundedCornerShape(20.dp),
            containerColor = MaterialTheme.colorScheme.background
        )
    }
}
