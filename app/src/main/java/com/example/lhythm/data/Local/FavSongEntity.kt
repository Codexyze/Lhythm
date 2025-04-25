package com.example.lhythm.data.Local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lhythm.constants.Constants

@Entity(tableName = Constants.FAVSONG)
data class FavSongEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
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
    val imagePersonal : String?=""
)
