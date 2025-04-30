package com.example.lhythm.core.MusicForeground

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.os.IBinder
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerNotificationManager
import com.example.lhythm.core.LocalNotification.buildNotification
import com.example.lhythm.core.LocalNotification.createNotificationChannel
import com.example.lhythm.core.Media.MediaPlayerManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MusicForeground @Inject constructor(private val mediaPlayerManager: MediaPlayerManager): Service() {

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        startForeground(101, buildNotification(this))
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel(applicationContext)
    }
}
