package com.example.lhythm.presentation.Screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lhythm.presentation.ViewModels.MediaManagerViewModel

@Composable
fun TestLandingScreen(mediaManagerViewModel: MediaManagerViewModel= hiltViewModel()) {

    val exoPlayerState = mediaManagerViewModel.getExoplayer()



}