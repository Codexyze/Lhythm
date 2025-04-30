package com.example.lhythm.core.LocalNotification


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.lhythm.R

import com.example.lhythm.constants.Constants
import com.example.lhythm.core.BroadCastReciver.StopServiceReciver
import com.example.lhythm.presentation.Screens.MainActivity

fun createNotificationChannel(context: Context){
    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
        val notificationChannel = NotificationChannel(Constants.NOTIFICATIONID,
            Constants.NOTIFICATIONNAME, NotificationManager.IMPORTANCE_HIGH
        )
        context.getSystemService(NotificationManager::class.java).createNotificationChannel(notificationChannel)

    }

}
 fun buildNotification(context: Context): Notification {
//     val stopIntent = Intent(context, StopServiceReciver::class.java).apply {
//         action="STOPPLAYER"
//     }
//     val pauseIntent = Intent(context,StopServiceReciver::class.java).apply {
//         action="PAUSEPLAYER"
//     }
//     val pendingIntent= PendingIntent.getBroadcast(
//         context,
//         0,
//         stopIntent,
//         PendingIntent.FLAG_IMMUTABLE
//     )
//     val pendingPauseIntent = PendingIntent.getBroadcast(
//         context,
//         1,
//         pauseIntent,
//         PendingIntent.FLAG_IMMUTABLE
//     )

     val intent = Intent(context, MainActivity::class.java)

     val activityIntent = PendingIntent.getActivity(
         context,
         0,
         intent,
         PendingIntent.FLAG_IMMUTABLE
     )

     return NotificationCompat.Builder(context, Constants.NOTIFICATIONID)
        .setContentTitle(Constants.APPNAME)
        .setContentText(Constants.NOTIFICATIONTEXT)
        .setSmallIcon(R.drawable.lythmlogoasset)
        .setOngoing(true)
        .setContentIntent(activityIntent)
        .build()
}
