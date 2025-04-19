package com.example.lhythm.data.Local

import androidx.room.Dao
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
}