package com.example.lhythm.data.Local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.lhythm.constants.Constants

@Dao
interface SongToImgDao {

    @Upsert
    suspend fun upsertSongToImg(songToImage: SongToImage)

    @Delete
    suspend fun deleteSongToImg(songToImage: SongToImage)

    @Query("SELECT * FROM ${Constants.SONGTOIMGTABLE}")
    suspend fun getAllSongsToImg(): List<SongToImage>

}