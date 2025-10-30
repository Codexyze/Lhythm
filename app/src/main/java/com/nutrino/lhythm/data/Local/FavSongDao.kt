package com.nutrino.lhythm.data.Local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface FavSongDao {
    @Upsert
    suspend fun insertOrUpdateFavSong(favSongEntity: FavSongEntity)

    @Delete
    suspend fun deleteFavSong(favSongEntity: FavSongEntity)

    @Query("SELECT * FROM favoritesongs")
    suspend fun getAllFavSongs(): List<FavSongEntity>

}