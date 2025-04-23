package com.example.lhythm.presentation.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lhythm.R
import com.example.lhythm.presentation.UIModels.SongCategory
import com.example.lhythm.presentation.Utils.LoadingScreen
import com.example.lhythm.presentation.ViewModels.GetSongCategoryViewModel
import com.example.lhythm.presentation.ViewModels.MediaManagerViewModel
import com.example.lhythm.ui.theme.BlackColor

@Composable
fun SongCategoriesScreen(viewmodel: GetSongCategoryViewModel= hiltViewModel(),
                         navController: NavController,
                         mediaPlayerViewModel: MediaManagerViewModel= hiltViewModel()) {
    val getAllSongsASCState = viewmodel.songsInASCOrderState.collectAsState()
    val currentCategory = rememberSaveable { mutableStateOf(SongCategory.ASCENDING) }
    if(getAllSongsASCState.value.isLoading){
        LoadingScreen()
    }else if(!getAllSongsASCState.value.error.isNullOrBlank()){
        Text("Error Loading Songs ${getAllSongsASCState.value.error}")
    }else if(!getAllSongsASCState.value.data.isNullOrEmpty()){
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = BlackColor)) {

            Row {
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    item(){

                            Box(modifier = Modifier.wrapContentSize().clickable{
                                currentCategory.value = SongCategory.ASCENDING
                            }){
                                Column(modifier = Modifier.clickable{
                                    currentCategory.value = SongCategory.ASCENDING
                                }, horizontalAlignment = Alignment.CenterHorizontally) {
                                    Image(
                                        painter = painterResource(R.drawable.ascasset),
                                        contentDescription = "ascending",
                                        modifier = Modifier.size(60.dp)
                                            .clip(CircleShape)
                                    )
                                    Text("Asscending")
                                }
                            }
                        Spacer(modifier = Modifier.width(16.dp))
                        Box(modifier = Modifier.wrapContentSize().clickable{
                            currentCategory.value = SongCategory.DESCENDING
                        }){
                            Column(modifier = Modifier.clickable{
                                currentCategory.value = SongCategory.DESCENDING
                            }, horizontalAlignment = Alignment.CenterHorizontally) {
                                Image(
                                    painter = painterResource(R.drawable.descasset),
                                    contentDescription = "decending",
                                    modifier = Modifier.size(60.dp) .clip(CircleShape)
                                )
                                Text("Descending")
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Box(modifier = Modifier.wrapContentSize().clickable{
                            currentCategory.value = SongCategory.ARTIST
                        }){
                            Column(modifier = Modifier.clickable{
                                currentCategory.value = SongCategory.ARTIST
                            }, horizontalAlignment = Alignment.CenterHorizontally) {
                                Image(
                                    painter = painterResource(R.drawable.artistasset),
                                    contentDescription = "artist",
                                    modifier = Modifier.size(60.dp) .clip(CircleShape)
                                )
                                Text(".  Artist  .")
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Box(modifier = Modifier.wrapContentSize().clickable{
                            currentCategory.value = SongCategory.YEARASC
                        }){
                            Column(modifier = Modifier.clickable{
                                currentCategory.value = SongCategory.YEARASC
                            }, horizontalAlignment = Alignment.CenterHorizontally) {
                                Image(
                                    painter = painterResource(R.drawable.yearaaset),
                                    contentDescription = ".   year   .",
                                    modifier = Modifier.size(60.dp) .clip(CircleShape)
                                )
                                Text("Year")
                            }
                        }
                        Box(modifier = Modifier.wrapContentSize().clickable{
                            //here
                            currentCategory.value = SongCategory.FAVSONG

                        }){
                            Column(modifier = Modifier.clickable{
                                currentCategory.value = SongCategory.FAVSONG
                                //here
                            }, horizontalAlignment = Alignment.CenterHorizontally) {
                                Image(
                                    painter = painterResource(R.drawable.favsongasset),
                                    contentDescription = "Artist",
                                    modifier = Modifier.size(60.dp) .clip(CircleShape)
                                )
                                Text("Favorite Song")
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))




                    }

                }
            }
            when(currentCategory.value){
                SongCategory.ASCENDING ->{
                    GetAllSongASCScreen(navController = navController)
                }
               SongCategory.DESCENDING->{
                    GetAllSongsDESC(navController = navController)
               }

                SongCategory.ARTIST->{

                     GetAllSongsByArtistScreen(navController = navController)
                }
                SongCategory.YEARASC -> {
                    GetSongsByYearASCScreen(navController = navController)
                }
                SongCategory.FAVSONG -> {
                    FavSongScreen()

                }
            }
        }

    }
}
