package com.example.lhythm.core.LocalNotification


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.core.app.NotificationCompat
import com.example.lhythm.R

import com.example.lhythm.constants.Constants
import com.example.lhythm.core.BroadCastReciver.StopServiceReciver
import kotlin.random.Random

fun createNotificationChannel(context: Context){
    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
        val notificationChannel = NotificationChannel(Constants.NOTIFICATIONID,
            Constants.NOTIFICATIONNAME, NotificationManager.IMPORTANCE_HIGH
        )
        val manager = context.getSystemService(NotificationManager::class.java).createNotificationChannel(notificationChannel)

    }

}
 fun buildNotification(context: Context): Notification {
     val stopIntent = Intent(context, StopServiceReciver::class.java)
     val pendingIntent= PendingIntent.getBroadcast(
         context,
         0,
         stopIntent,
         PendingIntent.FLAG_IMMUTABLE
     )

     return NotificationCompat.Builder(context, Constants.NOTIFICATIONID)
        .setContentTitle(Constants.APPNAME)
        .setContentText(Constants.NOTIFICATIONTEXT)
        .setSmallIcon(R.drawable.lythmlogoasset)
        .setOngoing(true)
        .addAction(R.drawable.lythmlogoasset, "Stop", pendingIntent)
        .build()
}
