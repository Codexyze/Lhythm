package com.example.lhythm.core.BroadCastReciver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.lhythm.core.Media.MediaPlayerManager
import com.example.lhythm.core.MusicForeground.MusicForeground
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class StopServiceReciver @Inject constructor(private val mediaPlayerManager: MediaPlayerManager): BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            when(intent?.action){
                 "STOPPLAYER"->{
                val intentStop= Intent(context, MusicForeground::class.java)
                context?.stopService(intentStop)
                //mediaPlayerManager.releasePlayer()
                MediaPlayerManager.MediaPlayerManager.releasePlayer()
                Log.d("BroadCast","StopBroadCast")
                Toast.makeText(context, "Music Stopped", Toast.LENGTH_SHORT).show()
                }
                "PAUSEPLAYER"->{
                    MediaPlayerManager.MediaPlayerManager.pausePlayer()
                    Toast.makeText(context, "Music Paused", Toast.LENGTH_SHORT).show()
                }
            }

        }catch (e: Exception){
            Log.d("BroadCast","${e.message}")
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
        }

    }
}