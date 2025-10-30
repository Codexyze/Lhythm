package com.nutrino.lhythm.presentation.Screens

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
import androidx.compose.material3.MaterialTheme
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
import com.nutrino.lhythm.R
import com.nutrino.lhythm.presentation.UIModels.SongCategory
import com.nutrino.lhythm.presentation.UIModels.WindowType
import com.nutrino.lhythm.presentation.Utils.LoadingScreen
import com.nutrino.lhythm.presentation.Utils.rememberWindowSize
import com.nutrino.lhythm.presentation.ViewModels.GetSongCategoryViewModel
import com.nutrino.lhythm.presentation.ViewModels.MediaManagerViewModel
import com.nutrino.lhythm.ui.theme.BlackColor

@Composable
fun SongCategoriesScreen(viewmodel: GetSongCategoryViewModel= hiltViewModel(),
                         navController: NavController,
                         mediaPlayerViewModel: MediaManagerViewModel= hiltViewModel()) {
    val getAllSongsASCState = viewmodel.songsInASCOrderState.collectAsState()
    val currentCategory = rememberSaveable { mutableStateOf(SongCategory.ASCENDING) }
    val screenWindowSize = rememberWindowSize()
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
                    item{

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
                                    Text("Asscending", color = MaterialTheme.colorScheme.primary)
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
                                Text("Descending", color = MaterialTheme.colorScheme.secondary)
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
                                Text(".  Artist  .", color = MaterialTheme.colorScheme.primary)
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
                                Text("  Year  ", color = MaterialTheme.colorScheme.secondary)
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
                                    contentDescription = "fAvSong",
                                    modifier = Modifier.size(60.dp) .clip(CircleShape)
                                )
                                Text(" Favorite", color = MaterialTheme.colorScheme.primary)
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Box(modifier = Modifier.wrapContentSize().clickable{
                            currentCategory.value = SongCategory.ASCENDING
                        }){
                            Column(modifier = Modifier.clickable{
                                currentCategory.value = SongCategory.COMPOSER
                            }, horizontalAlignment = Alignment.CenterHorizontally) {
                                Image(
                                    painter = painterResource(R.drawable.composerasset),
                                    contentDescription = "composer",
                                    modifier = Modifier.size(60.dp)
                                        .clip(CircleShape)
                                )
                                Text("Composer", color = MaterialTheme.colorScheme.secondary)
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Box(modifier = Modifier.wrapContentSize().clickable{
                            currentCategory.value = SongCategory.PLAYLIST
                        }){
                            Column(modifier = Modifier.clickable{
                                currentCategory.value = SongCategory.PLAYLIST
                            }, horizontalAlignment = Alignment.CenterHorizontally) {
                                Image(
                                    painter = painterResource(R.drawable.playlist),
                                    contentDescription = "ascending",
                                    modifier = Modifier.size(60.dp)
                                        .clip(CircleShape)
                                )
                                Text("PlayList", color = MaterialTheme.colorScheme.primary)
                            }
                        }

                    }

                }
            }
            when(currentCategory.value){
                SongCategory.ASCENDING ->{
                    when{
                        screenWindowSize.screenWidthType==WindowType.COMPACT->{
                            GetAllSongASCScreen(navController = navController)
                        }
                        screenWindowSize.screenWidthType==WindowType.MEDIUM->{
                           GetAllSongASCScreenMedium(navController = navController)
                        }
                        screenWindowSize.screenWidthType==WindowType.EXPANDED->{

                            GetAllSongASCScreenMedium(navController = navController)
                        }
                        else->{

                            GetAllSongASCScreenMedium(navController = navController)
                        }

                    }



                }
               SongCategory.DESCENDING->{
                   when{
                       screenWindowSize.screenWidthType==WindowType.COMPACT->{
                           GetAllSongsDESC(navController = navController)
                       }
                       screenWindowSize.screenWidthType==WindowType.MEDIUM->{

                       }
                       screenWindowSize.screenWidthType==WindowType.EXPANDED->{
                           GetAllSongsDESCMedium(navController = navController)
                       }
                       else->{
                           GetAllSongsDESCMedium(navController = navController)
                       }

                   }



               }

                SongCategory.ARTIST->{

                    when{
                        screenWindowSize.screenWidthType==WindowType.COMPACT->{
                            GetAllSongsByArtistScreen(navController = navController)
                        }
                        screenWindowSize.screenWidthType==WindowType.MEDIUM->{
                            GetAllSongsByArtistScreenMedium(navController = navController)

                        }
                        screenWindowSize.screenWidthType==WindowType.EXPANDED->{
                            GetAllSongsByArtistScreenMedium(navController = navController)
                        }
                        else->{
                            GetAllSongsByArtistScreenMedium(navController = navController)
                        }

                    }


                }
                SongCategory.YEARASC -> {
                    when{
                        screenWindowSize.screenWidthType==WindowType.COMPACT->{
                            GetSongsByYearASCScreen(navController = navController)
                        }
                        screenWindowSize.screenWidthType==WindowType.MEDIUM->{
                            GetSongsByYearASCScreenMedium(navController = navController)

                        }
                        screenWindowSize.screenWidthType==WindowType.EXPANDED->{
                            GetSongsByYearASCScreenMedium(navController = navController)
                        }
                        else->{
                            GetSongsByYearASCScreenMedium(navController = navController)
                        }

                    }

                }
                SongCategory.FAVSONG -> {

                    when{
                        screenWindowSize.screenWidthType==WindowType.COMPACT->{
                            FavSongScreen()
                        }
                        screenWindowSize.screenWidthType==WindowType.MEDIUM->{
                            FavSongScreenMedium()

                        }
                        screenWindowSize.screenWidthType==WindowType.EXPANDED->{
                            FavSongScreenMedium()
                        }
                        else->{
                            FavSongScreenMedium()
                        }

                    }


                }
                SongCategory.COMPOSER -> {

                    when{
                        screenWindowSize.screenWidthType==WindowType.COMPACT->{
                            ComposerCategory(navController = navController)
                        }
                        screenWindowSize.screenWidthType==WindowType.MEDIUM->{
                            ComposerCategoryMedium(navController = navController)

                        }
                        screenWindowSize.screenWidthType==WindowType.EXPANDED->{
                            ComposerCategoryMedium(navController = navController)
                        }
                        else->{
                            ComposerCategoryMedium(navController = navController)
                        }

                    }


                }

                SongCategory.PLAYLIST -> {
                    //Final screen
                    PlayListExample(navController = navController)
                }
            }
        }

    }
}
