package com.nutrino.lhythm.presentation.Screens

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.media3.common.MediaItem
import androidx.media3.common.util.Log
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import com.nutrino.lhythm.core.LocalNotification.createMusicNotificationChannel
import com.nutrino.lhythm.core.MusicForeground.MusicForeground
import com.nutrino.lhythm.presentation.Navigation.MainApp
import com.nutrino.lhythm.ui.theme.LhythmCustomTheme
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
        val dataUri = intent?.data
        if (dataUri != null) {
            Log.d("AudioIntent", "Received audio file: $dataUri")

            // You can now pass this to your player
            // Example:
            val mediaItem = MediaItem.fromUri(dataUri)
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.play()
        }
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
