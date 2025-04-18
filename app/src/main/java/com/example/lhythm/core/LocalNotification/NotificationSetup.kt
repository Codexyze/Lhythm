package com.example.lhythm.core.LocalNotification


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.lhythm.R

import com.example.lhythm.constants.Constants
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
    return NotificationCompat.Builder(context, Constants.NOTIFICATIONID)
        .setContentTitle(Constants.APPNAME)
        .setContentText(Constants.NOTIFICATIONTEXT)
        .setSmallIcon(R.drawable.lythmlogoasset)
        .setOngoing(true) // Notification can’t be dismissed
        .build()
}
