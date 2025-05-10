package com.example.lhythm.core.MusicForeground

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.media3.exoplayer.ExoPlayer
import com.example.lhythm.R
import com.example.lhythm.constants.Constants
import com.example.lhythm.core.LocalNotification.createNotificationChannel
import com.example.lhythm.presentation.Screens.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MusicForeground: Service() {

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        startForeground(101, buildNotification(this))
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        stopSelf()
        super.onDestroy()
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel(applicationContext)
    }


    fun buildNotification(context: Context): Notification {
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
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setOngoing(true)
            .setContentIntent(activityIntent)
            .build()
    }
}
