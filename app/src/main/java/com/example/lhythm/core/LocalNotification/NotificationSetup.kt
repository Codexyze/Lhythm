package com.example.lhythm.core.LocalNotification


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.OptIn
import androidx.core.app.NotificationCompat
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerNotificationManager
import com.example.lhythm.R

import com.example.lhythm.constants.Constants
import com.example.lhythm.core.BroadCastReciver.StopServiceReciver
import com.example.lhythm.core.Media.MediaPlayerManager
import com.example.lhythm.presentation.Screens.MainActivity

fun createNotificationChannel(context: Context){
    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
        val notificationChannel = NotificationChannel(Constants.NOTIFICATIONID,
            Constants.NOTIFICATIONNAME, NotificationManager.IMPORTANCE_HIGH
        )
        context.getSystemService(NotificationManager::class.java).createNotificationChannel(notificationChannel)

    }

}

@OptIn(UnstableApi::class)
fun createMusicExoNotification(exoPlayer: ExoPlayer, context: Context) {
    val notificationManager = PlayerNotificationManager.Builder(
        context,
        1, // Unique notification ID
        "media_channel" // Channel ID (should be created beforehand)
    )
        .setMediaDescriptionAdapter(DescriptionAdapter()) // use the class we defined above
        .setSmallIconResourceId(R.drawable.ic_launcher_foreground) // change this to your music note or app icon
        .setNotificationListener(object : PlayerNotificationManager.NotificationListener {
            override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
                super.onNotificationCancelled(notificationId, dismissedByUser)
                exoPlayer.pause()
            }

            override fun onNotificationPosted(
                notificationId: Int,
                notification: Notification,
                ongoing: Boolean
            ) {
                // Optional: Can be used to manage foreground service
            }
        })
        .build()

    notificationManager.setPlayer(exoPlayer)
}


@UnstableApi
class DescriptionAdapter : PlayerNotificationManager.MediaDescriptionAdapter {
    override fun getCurrentContentTitle(player: Player): CharSequence {
        return "🎶 Now Playing"
    }

    override fun createCurrentContentIntent(player: Player): PendingIntent? {
        // You can create a PendingIntent to open your app when the notification is tapped.
        return null
    }

    override fun getCurrentContentText(player: Player): CharSequence? {
        return "Streaming your awesome track!"
    }

    override fun getCurrentLargeIcon(
        player: Player,
        callback: PlayerNotificationManager.BitmapCallback
    ): Bitmap? {
        // You can return a bitmap here if you have album art
        return null
    }
}
fun createMusicNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            "media_channel",
            "Media Playback",
            NotificationManager.IMPORTANCE_HIGH
        )
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
}
