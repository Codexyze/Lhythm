package com.example.lhythm.core.MusicForeground

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.net.toUri
import com.example.lhythm.core.LocalNotification.buildNotification
import com.example.lhythm.core.LocalNotification.createNotificationChannel
import com.example.lhythm.core.Media.MediaPlayerManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MusicForeground:Service() {
    @Inject
    lateinit var mediaPlayerManager: MediaPlayerManager
    override fun onBind(p0: Intent?): IBinder? {
       return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val songUri = intent?.getStringExtra("SONG_URI") ?: return START_NOT_STICKY
       mediaPlayerManager.initializePlayer(uri = songUri.toUri())
        startForeground(101, buildNotification(this))
        return START_STICKY
    }


    override fun onDestroy() {
        mediaPlayerManager.releasePlayer()
        super.onDestroy()
    }
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel(applicationContext)
    }


}