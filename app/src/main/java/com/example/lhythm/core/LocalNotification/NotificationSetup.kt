package com.example.lhythm.core.LocalNotification


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerNotificationManager
import com.example.lhythm.R
import com.example.lhythm.constants.Constants
import com.example.lhythm.presentation.Screens.MainActivity
import javax.inject.Inject

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
        1,
        "media_channel"
    )
        .setMediaDescriptionAdapter(DescriptionAdapter(context = context)) // use the class we defined above
        .setSmallIconResourceId(R.drawable.ic_launcher_foreground) // change this to your music note or app icon
        .setNotificationListener(object : PlayerNotificationManager.NotificationListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
                super.onNotificationCancelled(notificationId, dismissedByUser)
               // val intent = Intent(context, MusicForeground::class.java)
                exoPlayer.stop()
                exoPlayer.release()
               // context.stopService(intent)
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onNotificationPosted(
                notificationId: Int,
                notification: Notification,
                ongoing: Boolean
            ) {
               // val intent = Intent(context, MusicForeground::class.java)
              // context.startForegroundService(intent)
            }
        })
        .build()

    notificationManager.setPlayer(exoPlayer)
}


@UnstableApi
class DescriptionAdapter @Inject constructor(private val context: Context): PlayerNotificationManager.MediaDescriptionAdapter {
    override fun getCurrentContentTitle(player: Player): String {
        return player.mediaMetadata.title.toString()
    }

    override fun createCurrentContentIntent(player: Player): PendingIntent? {
        val intent = Intent(context, MainActivity::class.java)
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    override fun getCurrentContentText(player: Player): CharSequence? {
        return player.mediaMetadata.artist
    }

    override fun getCurrentLargeIcon(
        player: Player,
        callback: PlayerNotificationManager.BitmapCallback
    ): Bitmap? {
        return BitmapFactory.decodeResource(context.resources,R.drawable.lythmlogoasset)
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
