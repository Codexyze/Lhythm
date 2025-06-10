package com.example.lhythm.core.BroadCastReciver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlaramReciver: BroadcastReceiver() {

    @Inject
    lateinit var exoPlayer: ExoPlayer
    override fun onReceive(context: Context?, intent: Intent?) {
        TODO("Not yet implemented")
    }
}