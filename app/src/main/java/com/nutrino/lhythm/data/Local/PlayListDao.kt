package com.nutrino.lhythm.data.Local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.nutrino.lhythm.constants.Constants

@Dao
interface PlayListDao {
    @Upsert
    suspend fun addPlayList(playListTable:PlayListTable)

    @Delete
    suspend fun deletePlayList(playListTable:PlayListTable)

    @Query("SELECT * FROM ${Constants.PLAYLISTTABLE}")
    suspend fun getAllPlayList(): List<PlayListTable>


}