package com.example.lhythm.core.BroadCastReciver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.lhythm.core.MusicForeground.MusicForeground

class StopServiceReciver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            val intentStop= Intent(context, MusicForeground::class.java)
            context?.stopService(intentStop)
            Log.d("BroadCast","StopBroadCast")
            Toast.makeText(context, "Music Stopped", Toast.LENGTH_SHORT).show()
        }catch (e: Exception){
            Log.d("BroadCast","${e.message}")
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
        }

    }
}