package com.example.lhythm.presentation.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    var selectedindex by rememberSaveable { mutableStateOf(0) }
    val bottonNavList = listOf<BottomNaviagtionItem>(
        BottomNaviagtionItem(
            title = "All songs",
            icon = Icons.Outlined.LibraryMusic
        ),
        BottomNaviagtionItem(
            title = "Nav 2",
            icon = Icons.Outlined.LibraryMusic
        ),
        BottomNaviagtionItem(
            title = "Nav 3",
            icon = Icons.Outlined.LibraryMusic
        ),
        BottomNaviagtionItem(
            title = "Nav 3",
            icon = Icons.Outlined.LibraryMusic
        )

    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottonNavList.forEachIndexed{index,item->
                    NavigationBarItem(
                        selected = index == selectedindex,
                        onClick = {
                            selectedindex = index
                        },
                        icon = {
                            Icon(imageVector = item.icon, contentDescription = item.title)
                        },
                        label = {
                            Text(text = item.title)
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

@Composable
fun ContentScreen(navController: NavController,index: Int) {
    when(index){
        0->{
           ListOfAllSongsScreen(navController = navController)
        }
        1->{

        }
        2->{
            Text("3")
        }
        3->{
            Text("4")
        }

    }

}
