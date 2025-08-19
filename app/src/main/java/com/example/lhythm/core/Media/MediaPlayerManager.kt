package com.example.lhythm.core.Media

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.annotation.OptIn
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.example.lhythm.constants.Constants
import com.example.lhythm.core.LocalNotification.createMusicExoNotification
import com.example.lhythm.core.MediaSessionService.PlaybackService
import com.example.lhythm.presentation.Utils.showToastMessage
import javax.inject.Inject


class MediaPlayerManager
@UnstableApi
@Inject constructor(private val context: Context,
private var  exoPlayer: ExoPlayer, private val mediaSession: MediaSession
) {

   // private var exoPlayer: ExoPlayer? = null
    var exoPlayerExternal : ExoPlayer? = exoPlayer
    var songList: List<Uri> = emptyList()

    fun initializePlayer(uri: Uri) {
        releasePlayer()
        exoPlayer.apply {
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
    }

    fun isPlaying(): Boolean {
        return exoPlayer?.isPlaying ?: false
    }

    fun getDuration(): Long {
       val duration = exoPlayer?.duration
        if(duration != null && duration >= 0L) {
            return duration
        }else{
            return 0L
        }
    }

    fun getCurrentPosition(): Float {
        return exoPlayer?.currentPosition?.toFloat() ?: 0f
    }


    fun seekTo(position: Long) {
        exoPlayer?.seekTo(position)
    }


    fun playListPlay(listOfSongsUri: List<Uri>) {

        releasePlayer()
        val mediaItemList = mutableListOf<MediaItem>()
        for (uri in listOfSongsUri) {
            val mediaItem = MediaItem.fromUri(uri.path!!.toUri())
            mediaItemList.add(mediaItem)
        }
        exoPlayer.apply {
            setMediaItems(mediaItemList)
            prepare()
            playWhenReady = true



        }

    }



    @OptIn(UnstableApi::class)
    fun playPlayListWithIndex(listOfSongsUri: List<Uri>, index: Int = 0) {
        val mediaItemList = listOfSongsUri.map {
            MediaItem.fromUri(it.path!!.toUri())
        }
        if (exoPlayer.isReleased) {
            Log.w("MediaPlayerManager", "playPlayListWithIndex() aborted: ExoPlayer is released")
            exoPlayer = ExoPlayer.Builder(context).build()
        }

        songList = listOfSongsUri



        try {
            exoPlayer.apply {
                val intent = Intent(context, PlaybackService::class.java)
                ContextCompat.startForegroundService(context, intent)

                addListener(object : Player.Listener {
                    override fun onAudioSessionIdChanged(audioSessionId: Int) {
                        super.onAudioSessionIdChanged(audioSessionId)
                        Log.d("AUDIOSESSION", "Audio session ID: $audioSessionId")
                    }
                })

                setMediaItems(mediaItemList, index, C.INDEX_UNSET.toLong())
                prepare()
                playWhenReady = true

                showToastMessage(context = context, text = "Playing", type = Constants.TOASTSUCCESS)
            }
        } catch (e: IllegalStateException) {
            Log.e("MediaPlayerManager", "Error using released ExoPlayer: ${e.message}")
            showToastMessage(context = context, text = "Playback failed 💥", type = Constants.TOASTERROR)
        }
    }

}


