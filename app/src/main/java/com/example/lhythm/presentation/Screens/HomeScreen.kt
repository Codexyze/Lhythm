package com.example.lhythm.presentation.Screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.PlaylistAddCheck
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.lhythm.presentation.UIModels.WindowType
import com.example.lhythm.presentation.Utils.rememberWindowSize
import com.example.lhythm.ui.theme.cardColor

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(navController: NavController) {
    var selectedindex by rememberSaveable { mutableStateOf(0) }
    val bottonNavList = listOf<BottomNaviagtionItem>(
        BottomNaviagtionItem(
            title = "All songs",
            icon = Icons.Outlined.LibraryMusic
        ),
        BottomNaviagtionItem(
            title = "Play",
            icon = Icons.Outlined.MusicNote
        ),
        BottomNaviagtionItem(
            title = "Categories",
            icon = Icons.Outlined.LibraryMusic
        ),
        BottomNaviagtionItem(
            title = "PlayList",
            icon = Icons.Outlined.PlaylistAddCheck
        ),
        BottomNaviagtionItem(
            title = "Settings",
            icon = Icons.Outlined.Settings
        )


    )

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = cardColor) {
                bottonNavList.forEachIndexed{index,item->
                    NavigationBarItem(
                        selected = index == selectedindex,
                        onClick = {
                            selectedindex = index
                        },
                        icon = {
                            Icon(imageVector = item.icon, contentDescription = item.title,
                                tint = if(index==selectedindex) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.secondary)
                        },
                        label = {
                            Text(text = item.title, color = if(index==selectedindex) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.secondary)
                        }

                    )

                }
            }
        }
    ) {
       Box(modifier = Modifier.padding(it)){
           ContentScreen(navController =navController, index =  selectedindex)
       }

    }

}

data class BottomNaviagtionItem(
    val title: String,
    val icon: ImageVector
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContentScreen(navController: NavController,index: Int) {
    val screenWindowSize = rememberWindowSize()
    when(index){
        0->{
            when{
                screenWindowSize.screenWidthType==WindowType.COMPACT->{
                    ListOfAllSongsScreen(navController = navController)
                }
                screenWindowSize.screenWidthType==WindowType.MEDIUM->{
                    ListOfAllSongsMediumScreen(navController = navController)
                }
                screenWindowSize.screenWidthType==WindowType.EXPANDED->{
                    ListOfAllSongsMediumScreen(navController = navController)
                }
                else->{
                    ListOfAllSongsMediumScreen(navController = navController)
                }

            }
//           ListOfAllSongsScreen(navController = navController)
        }
        1->{
            SongControllerScreen()
        }
        2->{
            when{
                screenWindowSize.screenWidthType==WindowType.COMPACT->{
                    SongCategoriesScreen(navController = navController)
                }
                screenWindowSize.screenWidthType==WindowType.MEDIUM->{
                  //  SongCategoriesMedium(navController = navController)
                    SongCategoriesScreen(navController = navController)
                }
                screenWindowSize.screenWidthType==WindowType.EXPANDED->{
                    //SongCategoriesMedium(navController = navController)
                    SongCategoriesScreen(navController = navController)
                }
                else->{
                  //  SongCategoriesMedium(navController = navController)
                    SongCategoriesScreen(navController = navController)
                }

            }



        }
        3->{
            //PlayListExample(navController = navController)
            ListOfPlayListScreen(navController = navController)
        }
        4->{
            SettingScreen(navController = navController)
        }

    }

}
