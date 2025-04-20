package com.example.lhythm.domain.Repository

import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.data.Local.FavSongEntity
import kotlinx.coroutines.flow.Flow


interface FavSongRepository{

    suspend fun insertOrUpdateFavSong(favSongEntity: FavSongEntity): Flow<ResultState<String>>

    suspend fun deleteFavSong(favSongEntity: FavSongEntity): Flow<ResultState<String>>

    suspend fun getAllFavSongs(): Flow<ResultState<List<FavSongEntity>>>

}