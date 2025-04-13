package com.example.lhythm.presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lhythm.presentation.Screens.TestScreen

@Composable
fun MainApp() {
    val navcontroller = rememberNavController()

    NavHost(navController = navcontroller, startDestination = SAMPLESCREEN ){
        composable<SAMPLESCREEN>{
            TestScreen()
        }

    }

}