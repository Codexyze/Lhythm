package com.example.lhythm.core.BroadCastReciver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import androidx.media3.exoplayer.ExoPlayer
import com.example.lhythm.Di.ExoPlayerEntryPoint
import com.example.lhythm.core.Media.MediaPlayerManager
import com.example.lhythm.core.MusicForeground.MusicForeground
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class StopServiceReciver: BroadcastReceiver() {

    @Inject
    lateinit var mediaPlayerManager: MediaPlayerManager //this
    val mediaPlayer = MediaPlayer()

    override fun onReceive(context: Context?, intent: Intent?) {
//        val hiltEntryPoint = EntryPointAccessors.fromApplication(
//            context!!,
//            ExoPlayerEntryPoint::class.java
//        )
//
//        val exoPlayer = hiltEntryPoint.getExoPlayer()
//
//        try {
//            when(intent?.action){
//                 "STOPPLAYER"->{
//                val intentStop= Intent(context, MusicForeground::class.java)
//                     mediaPlayer.release()
//                    exoPlayer.pause()
//                context?.stopService(intentStop)
//                //mediaPlayerManager.releasePlayer()
//               // MediaPlayerManager.MediaPlayerManager.releasePlayer()
//
//                Log.d("BroadCast","StopBroadCast")
//                Toast.makeText(context, "Music Stopped", Toast.LENGTH_SHORT).show()
//                }
//                "PAUSEPLAYER"->{
//                   // MediaPlayerManager.MediaPlayerManager.pausePlayer()
//                    Log.d("Exoplayer",exoPlayer.toString())
//                  // exoPlayer.pause()
//                    mediaPlayer.pause()
//                    Toast.makeText(context, "Music Paused", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//        }catch (e: Exception){
//            Log.d("BroadCast","${e.message}")
//            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
//        }

    }
}
