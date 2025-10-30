package com.nutrino.lhythm.core.BroadCastReciver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.media3.exoplayer.ExoPlayer
import com.nutrino.lhythm.constants.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlaramReciver: BroadcastReceiver() {

    @Inject
    lateinit var exoPlayer: ExoPlayer
    override fun onReceive(context: Context?, intent: Intent?) {
        val alarmMessage = if (intent != null) {
            intent.getStringExtra(Constants.ALARAMBROADCAST) ?: return
        } else {
            return
        }
       if(alarmMessage=="Alaram has been triggred" && exoPlayer.isPlaying){
           exoPlayer.stop()
       }else if(!exoPlayer.isPlaying){
           //
       }
       else{
           return
       }
    }
}