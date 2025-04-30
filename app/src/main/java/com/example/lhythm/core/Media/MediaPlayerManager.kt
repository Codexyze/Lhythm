package com.example.lhythm.core.Media

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.lhythm.constants.Constants
import com.example.lhythm.presentation.Utils.showToastMessage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaPlayerManager @Inject constructor(private val context: Context,
private var exoPlayer: ExoPlayer) {

   // private var exoPlayer: ExoPlayer? = null
    var exoPlayerExternal : ExoPlayer? = exoPlayer
     var songList: List<Uri> = emptyList()

    fun initializePlayer(uri: Uri) {
        releasePlayer()

//        exoPlayer = ExoPlayer.Builder(context).build().apply {
//            setMediaItem(MediaItem.fromUri(uri))
//            prepare()
//            playWhenReady = true
//        }
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
        //exoPlayer = null
    }

    fun isPlaying(): Boolean {
        // Safely check if exoPlayer is null and return false if it is
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
        exoPlayer = ExoPlayer.Builder(context).build().apply {
            setMediaItems(mediaItemList)
            prepare()
            playWhenReady = true
            showToastMessage(context = context, text = "Playing",type = Constants.TOASTSUCCESS)

            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    if (state == Player.STATE_ENDED) {
                        Log.d("MediaPlayerManager", "Playlist ended.")
                    }
                }

                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    val currentIndex = this@apply.currentMediaItemIndex
                    Log.d("MediaPlayerManager", "Now playing index: $currentIndex")
                }
            })
        }

    }
    fun playPlayListWithIndex(listOfSongsUri: List<Uri>,index: Int=0) {
        songList = listOfSongsUri
        releasePlayer()
        val mediaItemList = mutableListOf<MediaItem>()
        for (uri in listOfSongsUri) {
            val mediaItem = MediaItem.fromUri(uri.path!!.toUri())
            mediaItemList.add(mediaItem)
        }
        exoPlayer = ExoPlayer.Builder(context).build().apply {
            setMediaItems(mediaItemList,index, C.INDEX_UNSET.toLong())
            prepare()
            playWhenReady = true
            showToastMessage(context = context, text = "Playing",type = Constants.TOASTSUCCESS)

            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    if (state == Player.STATE_ENDED) {
                        Log.d("MediaPlayerManager", "Playlist ended.")
                    }
                }

                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    val currentIndex = this@apply.currentMediaItemIndex
                    Log.d("MediaPlayerManager", "Now playing index: $currentIndex")
                }
            })
        }

    }
}



 