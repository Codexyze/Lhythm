package com.example.lhythm.data.RepoIMPL

import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.data.Local.FavSongEntity
import com.example.lhythm.data.Local.SongPlayListDataBase
import com.example.lhythm.domain.Repository.FavSongRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavSongRepoImpl @Inject constructor(private val dataBase: SongPlayListDataBase) : FavSongRepository {

    override suspend fun insertOrUpdateFavSong(favSongEntity: FavSongEntity): Flow<ResultState<String>> =flow{
        emit(ResultState.Loading)
        try {
            dataBase.FavSongDao().insertOrUpdateFavSong(favSongEntity = favSongEntity)
            emit(ResultState.Success("Sucessfully added to playlist"))
        }catch (e: Exception){
            emit(ResultState.Error(e.message.toString()))
        }
    }

    override suspend fun deleteFavSong(favSongEntity: FavSongEntity): Flow<ResultState<String>> =flow{
        emit(ResultState.Loading)
        try {
            dataBase.FavSongDao().deleteFavSong(favSongEntity = favSongEntity)
            emit(ResultState.Success("Sucessfully deleted"))
        }catch (e: Exception){
            emit(ResultState.Error(e.message.toString()))
        }
    }

    override suspend fun getAllFavSongs(): Flow<ResultState<List<FavSongEntity>>> =flow{
        emit(ResultState.Loading)
        try {
            val data = dataBase.FavSongDao().getAllFavSongs()
            emit(ResultState.Success(data))
        }catch (e: Exception){
            emit(ResultState.Error(e.message.toString()))
        }
    }
}