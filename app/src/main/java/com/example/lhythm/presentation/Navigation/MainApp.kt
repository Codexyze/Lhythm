package com.example.lhythm.presentation.Navigation

import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.lhythm.presentation.Screens.HomeScreen
import com.example.lhythm.presentation.Screens.ListOfAllSongsScreen
import com.example.lhythm.presentation.Screens.OnBoardingScreen
import com.example.lhythm.presentation.Screens.SongPlayerScreen
import com.example.lhythm.presentation.ViewModels.OnBoardingViewModel

@Composable
fun MainApp(viewmodel: OnBoardingViewModel = hiltViewModel()) {
    val state =viewmodel.onBoardingPrefrence.collectAsState()
    val navcontroller = rememberNavController()
    LaunchedEffect(Unit) {
        viewmodel.getLatestValueOfPref()
    }
    if (state.value==null){
        CircularProgressIndicator()
    }else{
        NavHost(navController = navcontroller, startDestination = if (state.value!!)ONBOARDING  else HOMESCREEN ){
            composable<SAMPLESCREEN>{
                ListOfAllSongsScreen(navController = navcontroller)
            }
            composable<ONBOARDING>{
                OnBoardingScreen(navController = navcontroller)
            }
            composable<MUSICPLAYERSCREEN> {navData->
                val data:MUSICPLAYERSCREEN = navData.toRoute()
                SongPlayerScreen(uri = data.path.toUri())
            }
            composable<HOMESCREEN> {
                HomeScreen(navController = navcontroller)
            }

        }
    }

    Log.d("PREFDATA",state.value.toString())

}


