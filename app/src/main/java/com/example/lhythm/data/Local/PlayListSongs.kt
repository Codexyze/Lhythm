package com.example.lhythm.data.Local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.lhythm.constants.Constants

@Dao
interface PlayListSongs {
    @Upsert
    suspend fun upsertPlayListSongs(playListSongMapper: PlayListSongMapper)

    @Delete
    suspend fun deletePlayListSongs(playListSongMapper: PlayListSongMapper)

    @Query("SELECT * FROM ${ Constants.PLAYLISTSONGTABEL}")
    suspend fun getAllPlayListSongs(): List<PlayListSongMapper>

    @Query("SELECT * FROM ${Constants.PLAYLISTSONGTABEL} WHERE playListID = :playlistId")
    suspend fun getSongByPlayListID(playlistId: Int): List<PlayListSongMapper>

}