package com.example.lhythm.core.Media

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaPlayerManager @Inject constructor(private val context: Context) {

    private var exoPlayer: ExoPlayer? = null

    fun initializePlayer(uri: Uri) {
        releasePlayer() // Clean old instance if any

        exoPlayer = ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
            playWhenReady = true
        }
    }

    fun getPlayer(): ExoPlayer? = exoPlayer

    fun pause() {
        exoPlayer?.pause()
    }

    fun play() {
        exoPlayer?.play()
    }

    fun releasePlayer() {
        exoPlayer?.release()
        exoPlayer = null
    }

}