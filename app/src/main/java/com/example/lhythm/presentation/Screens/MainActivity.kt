package com.example.lhythm.presentation.Screens

import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
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
    lateinit var exoPlayer: ExoPlayer
    @Inject
    lateinit var mediaSession: MediaSession
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        createMusicNotificationChannel(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LhythmCustomTheme {
                MainApp()
                //ListOfPlayListScreen()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        val notificationManager =getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.cancel("media_channel", 1) // 1 = your notification ID
        exoPlayer.release()
        mediaSession.release()
        val intent = Intent(this, MusicForeground::class.java)
        stopService(intent)

    }
}
