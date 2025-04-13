package com.example.lhythm.presentation.Screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lhythm.presentation.Navigation.SAMPLESCREEN
import com.example.lhythm.presentation.ViewModels.OnBoardingViewModel

@Composable
fun OnBoardingScreen(viewmodel: OnBoardingViewModel = hiltViewModel(),navController: NavController) {
    Text("Oboarding")

    Button(onClick = {
        viewmodel.upDateOnBoardingPref()
        navController.navigate(SAMPLESCREEN){
            popUpTo(0)
        }
    }) {
        Text("Skip")
    }
}