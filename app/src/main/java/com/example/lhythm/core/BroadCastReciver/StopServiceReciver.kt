package com.example.lhythm.core.BroadCastReciver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import com.example.lhythm.core.Media.MediaPlayerManager
import javax.inject.Inject

class StopServiceReciver: BroadcastReceiver() {

    @Inject
    lateinit var mediaPlayerManager: MediaPlayerManager //this
    val mediaPlayer = MediaPlayer()

    override fun onReceive(context: Context?, intent: Intent?) {
    }
}

