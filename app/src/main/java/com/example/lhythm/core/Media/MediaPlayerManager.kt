package com.example.lhythm.core.Media

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaPlayerManager @Inject constructor(private val context: Context) {

    private var exoPlayer: ExoPlayer? = null

    fun initializePlayer(uri: Uri) {
        releasePlayer()

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
        exoPlayer?.apply {
           this.stop()
            this.release()
        }
        exoPlayer = null
    }

    fun isPlaying(): Boolean {
        // Safely check if exoPlayer is null and return false if it is
        return exoPlayer?.isPlaying ?: false
    }

    fun getDuration(): Long? {
        return exoPlayer?.duration
    }

    fun getCurrentPosition():Float {
      return  exoPlayer?.currentPosition!!.toFloat()
    }

    fun seekTo(position: Long) {
        exoPlayer?.seekTo(position)
    }
    object MediaPlayerManager {
        private var mediaPlayer: MediaPlayer? = null

        fun releasePlayer() {
            mediaPlayer?.release()
            mediaPlayer = null
        }

        fun pausePlayer() {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    it.pause()
                }
            } ?: Log.e("MediaPlayerManager", "pausePlayer() called but mediaPlayer is null")
        }


        // Optional: add create/start functions too
    }


}