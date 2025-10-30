package com.nutrino.lhythm.data.Local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nutrino.lhythm.constants.Constants

@Entity(tableName = Constants.PLAYLISTSONGTABEL)
data class PlayListSongMapper(
    @PrimaryKey(autoGenerate = true)
    val id: Int =0,
    val playListID: Int,
    val path: String,
    val size: String?="",
    val album: String?="Unknown",
    val title: String ?="Unknown",
    val artist: String?="Unknown",
    val duration: String?="0",
    val year: String?="0",
    val composer: String?="Unknown",
    val albumId: String?="",
    val lyrics: String?="Empty Lyrics",
    val priority: String?="Default",
    val link: String?="No link avaliable"
)
