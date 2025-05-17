package com.example.lhythm.presentation.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lhythm.constants.Constants
import com.example.lhythm.data.Local.PlayListTable
import com.example.lhythm.presentation.Navigation.USERPLAYLISTSCREEN
import com.example.lhythm.presentation.Utils.LoadingScreen
import com.example.lhythm.presentation.Utils.showToastMessage
import com.example.lhythm.presentation.ViewModels.PlayListViewModel

@Composable
fun ListOfPlayListScreen(playListViewModel: PlayListViewModel = hiltViewModel(),navController: NavController) {
    LaunchedEffect(Unit) {
        playListViewModel.getAllPlayList()
    }
    val playListState = playListViewModel.getAllPlayListState.collectAsState()
    val createOrUpdatePlayListState = playListViewModel.createOrUpdatePlayListState.collectAsState()
    val createUpdateDialogue = rememberSaveable { mutableStateOf(false) }
    val playListName = rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    when{
        createOrUpdatePlayListState.value.isLoading -> {
            LoadingScreen()
        }
        !createOrUpdatePlayListState.value.error.isNullOrEmpty() -> {
            Text(text = createOrUpdatePlayListState.value.error.toString())
        }
        !createOrUpdatePlayListState.value.data.isNullOrEmpty()->{
            playListViewModel.getAllPlayList()
            showToastMessage(context= context, text = "New Playlist Created", type = Constants.TOASTSUCCESS)

        }
    }
    when{
        playListState.value.isLoading -> {
            LoadingScreen()
        }
        !playListState.value.error.isNullOrEmpty() -> {
            Text(text = playListState.value.error.toString())
        }
        !playListState.value.data.isNullOrEmpty()->{
            Scaffold(modifier = Modifier.fillMaxSize(),
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                           createUpdateDialogue.value = true
                        },
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add", tint = MaterialTheme.colorScheme.secondary)
                    }
                }) {
                it
                LazyColumn(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                    items(playListState.value.data) { playListTable ->
                        EachPlayListNameItem(playListTable = playListTable, navController = navController)
                    }
                }

            }
        }
        playListState.value.data.isNullOrEmpty()->{
            Scaffold(modifier = Modifier.fillMaxSize(),
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            createUpdateDialogue.value = true
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                    }
                }) {
                it
                LazyColumn(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                    items(playListState.value.data) { playListTable ->
                        EachPlayListNameItem(playListTable = playListTable, navController = navController)
                    }
                }

            }
        }

    }
    if(createUpdateDialogue.value){
        AlertDialog(
            onDismissRequest = {
                createUpdateDialogue.value = false
            },
            confirmButton = {
                Button(
                    onClick = {
                        if(!playListName.value.isNullOrEmpty() && playListName.value != ""){
                            val playListTable = PlayListTable(playListName =playListName.value )
                            playListViewModel.createOrInsertPlayList(playListTable = playListTable)
                        }else{
                            showToastMessage(context = context, text = "Please Enter PlayList Name", type = Constants.TOASTERROR)
                        }

                    }
                ) {
                    Text("Create")
                }

            },
            dismissButton = {
                Button(
                    onClick = {
                        createUpdateDialogue.value = false
                    }
                ) {
                    Text("Close")
                }

            },
            title = {
                Text("Create New PlayList", color = MaterialTheme.colorScheme.secondary)

            },
            text = {
                Column {
                    OutlinedTextField(
                        value = playListName.value,
                        onValueChange = {
                            playListName.value = it
                        },
                        label = {
                            Text("PlayList Name")
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                            errorTextColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                        ),textStyle = TextStyle(color = MaterialTheme.colorScheme.primary)
                    )
                }

            },
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }



}

@Composable
fun EachPlayListNameItem(playListTable: PlayListTable,
                         navController: NavController,
                         playListViewModel: PlayListViewModel=hiltViewModel()) {
    val deletePlayListState = playListViewModel.deletePlayListState.collectAsState()
    when{
        deletePlayListState.value.isLoading -> {
            LoadingScreen()
        }
        !deletePlayListState.value.error.isNullOrEmpty() -> {
            showToastMessage(context = LocalContext.current, text = "PlayList Not Deleted", type = Constants.TOASTERROR)
        }
        !deletePlayListState.value.data.isNullOrEmpty()->{
            showToastMessage(context = LocalContext.current, text = "PlayList Deleted", type = Constants.TOASTSUCCESS)
        }
    }
    Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = {
                navController.navigate(USERPLAYLISTSCREEN(playListID = playListTable.id))

            },
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Row {
                Text(text = playListTable.playListName, color = MaterialTheme.colorScheme.secondary)
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Favorite",
                    tint = MaterialTheme.colorScheme.background, modifier = Modifier.weight(1f).clickable {
                        playListViewModel.deletePlayList(playListTable = playListTable)
                    }
                )
            }

        }
    }

}