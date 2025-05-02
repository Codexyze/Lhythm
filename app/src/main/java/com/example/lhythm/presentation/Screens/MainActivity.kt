package com.example.lhythm.presentation.Screens

import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.lhythm.core.LocalNotification.createMusicNotificationChannel
import com.example.lhythm.core.Media.MediaPlayerManager
import com.example.lhythm.core.MusicForeground.MusicForeground
import com.example.lhythm.presentation.Navigation.MainApp
import com.example.lhythm.ui.theme.LhythmCustomTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var mediaPlayerManager: MediaPlayerManager
    override fun onCreate(savedInstanceState: Bundle?) {
        createMusicNotificationChannel(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LhythmCustomTheme {
                MainApp()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val notificationManager =getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel("media_channel", 1) // 1 = your notification ID
        mediaPlayerManager.releasePlayer()
        val intent = Intent(this, MusicForeground::class.java)
        stopService(intent)

    }
}
