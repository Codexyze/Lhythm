package com.example.lhythm.core.AlaramManager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.lhythm.constants.Constants
import com.example.lhythm.core.BroadCastReciver.AlaramReciver

class AlaramManager(private val context: Context) {
    val alaramManager = context.getSystemService(AlarmManager::class.java)
    val intent = Intent(context, AlaramReciver::class.java).apply {
        this.putExtra("${Constants.ALARAMBROADCAST}","Alaram has been triggred")
    }
    val pendingIntent = PendingIntent.getBroadcast(context,1,intent,
        PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    fun setAlaram(time: Long){
        alaramManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,time,pendingIntent)
    }

    fun cancelAlaram(){
        alaramManager.cancel(pendingIntent)
    }
}