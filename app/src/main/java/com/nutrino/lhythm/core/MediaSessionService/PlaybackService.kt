package com.nutrino.lhythm.core.MediaSessionService

import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.ui.PlayerNotificationManager
import com.nutrino.lhythm.R
import com.nutrino.lhythm.core.LocalNotification.DescriptionAdapter
import com.nutrino.lhythm.core.LocalNotification.createMusicNotificationChannel
import com.nutrino.lhythm.core.LocalNotification.createNotificationChannel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@UnstableApi
@AndroidEntryPoint
class PlaybackService : MediaSessionService() {

    @Inject
    lateinit var exoPlayer: ExoPlayer
    @Inject
    lateinit var mediaSession: MediaSession


//override fun onCreate() {
//    super.onCreate()
//
//    createNotificationChannel(this) // Your original one (optional if unused)
//    createMusicNotificationChannel(this) // ✅ use this one for media channel
//    createMusicExoNotification(exoPlayer, this)
//
//
//
//}
override fun onCreate() {
    super.onCreate()

    createNotificationChannel(this)
    createMusicNotificationChannel(this)

    // Create the notification manager
    val notificationManager = PlayerNotificationManager.Builder(
        this,
        1,
        "media_channel"
    )
        .setMediaDescriptionAdapter(DescriptionAdapter(this))
        .setSmallIconResourceId(R.drawable.ic_launcher_foreground)
        .build()

    notificationManager.setPlayer(exoPlayer)

    // ✅ Create a dummy Notification to satisfy startForeground
    val notification = NotificationCompat.Builder(this, "media_channel")
        .setContentTitle("Lythm is loading 🎶")
        .setContentText("Preparing music playback...")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .build()

    // 🚨 This is the missing piece — START THE FOREGROUND SERVICE!
    startForeground(1, notification)
}



    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }


    override fun onDestroy() {
        exoPlayer.release()
        mediaSession?.release()
        val playBackIntent = Intent(this, PlaybackService::class.java)
        stopService(playBackIntent)
        super.onDestroy()

    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        exoPlayer.release()
        stopSelf()
    }

}
