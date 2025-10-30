package com.nutrino.lhythm.data.Local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.nutrino.lhythm.constants.Constants

@Dao
interface SongDao {
    @Upsert
   suspend fun insertSongToPlayList(songEntity: SongEntity)
    @Query("SELECT * FROM ${Constants.PLAYLIST}")
   suspend fun getSongsFromPlayList(): List<SongEntity>
   @Delete
   suspend fun deleteClickedSong(songEntity: SongEntity)

   @Query("SELECT * FROM ${Constants.PLAYLIST} WHERE title LIKE '%' || :query || '%' ")
   suspend fun searchFromPlaylist(query: String): List<SongEntity>

   @Query("SELECT lyrics FROM ${Constants.PLAYLIST} WHERE id = :id")
   suspend fun getLyricsFromPlaylist(id: Int): String

}