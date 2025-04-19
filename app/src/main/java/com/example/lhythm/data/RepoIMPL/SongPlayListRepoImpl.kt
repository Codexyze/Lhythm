package com.example.lhythm.data.RepoIMPL

import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.data.Local.SongEntity
import com.example.lhythm.data.Local.SongPlayListDataBase
import com.example.lhythm.domain.Repository.SongPlayListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SongPlayListRepoImpl @Inject constructor(private val dataBase: SongPlayListDataBase): SongPlayListRepository {
    override suspend fun insertSongToPlayList(songEntity: SongEntity): Flow<ResultState<String>> = flow {
         emit(ResultState.Loading)
        try {
            dataBase.SongPlayListDao().insertSongToPlayList(songEntity = songEntity)
            ResultState.Success("Sucessfully added to playlist")
        }catch (e: Exception){
            ResultState.Error(e.message.toString())
        }
    }

    override suspend fun getSongsFromPlayList(): Flow<ResultState<List<SongEntity>>> =flow{
        emit(ResultState.Loading)
        try {
           val data = dataBase.SongPlayListDao().getSongsFromPlayList()
            ResultState.Success(data)
        }catch (e: Exception){
            ResultState.Error(e.message.toString())
        }
    }
}