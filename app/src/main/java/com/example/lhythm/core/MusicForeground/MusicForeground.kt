package com.example.lhythm.core.MusicForeground

import android.app.Service
import android.content.Intent
import android.os.IBinder
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
      startForeground(101, buildNotification(this))
      return START_STICKY
     }



    override fun onDestroy() {
        super.onDestroy()
        mediaPlayerManager.pause()
        mediaPlayerManager.releasePlayer()
    }
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel(applicationContext)
    }


}

