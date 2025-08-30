package com.example.lhythm.presentation.Screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.lhythm.constants.Constants
import com.example.lhythm.data.Local.SongToImage
import com.example.lhythm.presentation.Navigation.MEMORYNOTESSCREEN
import com.example.lhythm.presentation.Utils.LoadingScreen
import com.example.lhythm.presentation.Utils.showToastMessage
import com.example.lhythm.presentation.ViewModels.ImageViewModel


@Composable
fun ViewMemoryScreen(navController: NavController,
                     imageViewModel: ImageViewModel= hiltViewModel()
) {
    LaunchedEffect(Unit) {
        imageViewModel.getAllMappedImgAndSong()
    }
    val getAllMapImgState = imageViewModel.getAllmappedImgAndSong.collectAsState()
    val context = LocalContext.current
    when{
        !getAllMapImgState.value.data.isNullOrEmpty()->{
            Scaffold(modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(it)) {
                    LazyColumn {
                        items(getAllMapImgState.value.data){songToImage->
                            MemoryBoxItem(songToImage = songToImage, navController = navController)


                        }
                    }


                }

            }


        }
        getAllMapImgState.value.isLoading->{
            LoadingScreen()

        }
        !getAllMapImgState.value.error.isNullOrEmpty()->{
            showToastMessage(context = context ,text="Error", type = Constants.TOASTERROR)
        }
        !getAllMapImgState.value.data.isNullOrEmpty()->{
            Text("ERROR")
        }
    }


}
@Composable
fun MemoryBoxItem(songToImage: SongToImage,navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(12.dp).clickable{
                //show notes here
                navController.navigate(MEMORYNOTESSCREEN(
                    id = songToImage.id,
                    songPath = songToImage.songPath,
                    songTitle = songToImage.songTitle,
                    notes = songToImage.notes,
                    songAuthor = songToImage.songAuthor,
                    imgPath = songToImage.imgPath
                ))


            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 🎵 Song Thumbnail Image
        AsyncImage(
            model = songToImage.imgPath.toUri(),
            contentDescription = "Song Image",
            modifier = Modifier
                .size(60.dp) // square image
                .clip(RoundedCornerShape(8.dp)), // rounded corners
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp)) // gap between image and text

        // 📑 Song Info
        Column(
            modifier = Modifier.weight(1f) // take remaining space
        ) {
            Text(
                text = songToImage.songTitle ?: "Unknown Title",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = songToImage.songAuthor ?: "Unknown Author",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
