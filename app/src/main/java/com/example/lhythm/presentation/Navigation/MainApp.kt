package com.example.lhythm.presentation.Navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.lhythm.presentation.Screens.HomeScreen
import com.example.lhythm.presentation.Screens.ListOfAllSongsScreen
import com.example.lhythm.presentation.Screens.LyricsFullScreenView
import com.example.lhythm.presentation.Screens.OnBoardingScreen
import com.example.lhythm.presentation.Screens.SoundFXScreen
import com.example.lhythm.presentation.Screens.UserPlayListScreen
import com.example.lhythm.presentation.Utils.LoadingScreen
import com.example.lhythm.presentation.ViewModels.OnBoardingViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainApp(viewmodel: OnBoardingViewModel = hiltViewModel()) {
    val state =viewmodel.onBoardingPrefrence.collectAsState()

    val navcontroller = rememberNavController()
    LaunchedEffect(Unit) {
        viewmodel.getLatestValueOfPref()
    }
    if (state.value==null){
        LoadingScreen()
    }else{
        NavHost(navController = navcontroller, startDestination = if (state.value!!)ONBOARDING  else HOMESCREEN ){
            composable<SAMPLESCREEN>{
                ListOfAllSongsScreen(navController = navcontroller)
            }
            composable<ONBOARDING>{
                OnBoardingScreen(navController = navcontroller)
            }

            composable<HOMESCREEN> {
                HomeScreen(navController = navcontroller)
            }
            composable<USERPLAYLISTSCREEN> {bacstackEntry->
                val data: USERPLAYLISTSCREEN = bacstackEntry.toRoute()
                UserPlayListScreen(navController = navcontroller, playListID = data.playListID)
            }
            composable<LYRICSFULLSCREEN> {bacstackEntry->
                val data: LYRICSFULLSCREEN = bacstackEntry.toRoute()
                LyricsFullScreenView(lyrics = data.lyrics)
            }
            composable<SOUNDFXSCREEN> {
                SoundFXScreen(navController = navcontroller)
            }

        }
    }


}


