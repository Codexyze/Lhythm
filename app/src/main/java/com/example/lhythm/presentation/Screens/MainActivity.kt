package com.example.lhythm.presentation.Screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.lhythm.core.Media.MediaPlayerManager
import com.example.lhythm.presentation.Navigation.MainApp
import com.example.lhythm.ui.theme.LhythmTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

//Master Branch
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var mediaPlayerManager: MediaPlayerManager
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LhythmTheme {
                MainApp()
            }
        }
    }

    override fun onDestroy() {
       mediaPlayerManager.releasePlayer()
        super.onDestroy()

    }
}
