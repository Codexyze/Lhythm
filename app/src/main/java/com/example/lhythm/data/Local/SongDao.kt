package com.example.lhythm.data.Local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.lhythm.constants.Constants
import com.example.lhythm.core.StateHandeling.ResultState
import kotlinx.coroutines.flow.Flow

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

}