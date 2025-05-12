package com.example.lhythm.data.RepoIMPL

import android.util.Log
import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.data.Local.PlayListTable
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
            emit(ResultState.Success("Sucessfully added to playlist"))
            Log.d("DATABASE", "Sucessfully added to playlist")
        }catch (e: Exception){
            emit(ResultState.Error(e.message.toString()))
            Log.d("DATABASE", "Error ${e.message}")
        }
    }

    override suspend fun getSongsFromPlayList(): Flow<ResultState<List<SongEntity>>> =flow{
        emit(ResultState.Loading)
        try {
           val data = dataBase.SongPlayListDao().getSongsFromPlayList()
            Log.d("DATABASE", "${data.toString()}")
            emit(ResultState.Success(data))
        }catch (e: Exception){
            emit( ResultState.Error(e.message.toString()))

            Log.d("DATABASE", "Error ${e.message}")
        }
    }

    override suspend fun deleteClickedSongFromPlayList(songEntity: SongEntity): Flow<ResultState<String>> =flow{
        emit(ResultState.Loading)
        try {
            dataBase.SongPlayListDao().deleteClickedSong(songEntity = songEntity)
            emit(ResultState.Success("Sucessfully deleted"))
        }catch (e: Exception){
            emit(ResultState.Error(e.message.toString()))
        }
    }

    override suspend fun searchFromPlaylist(query: String): Flow<ResultState<List<SongEntity>>> =flow{
        emit(ResultState.Loading)
        try {
           val searchdata = dataBase.SongPlayListDao().searchFromPlaylist(query = query)
            emit(ResultState.Success(searchdata))

        }catch (e: Exception){
            emit(ResultState.Error(e.message.toString()))
        }
    }

    override suspend fun getLyricsFromPlaylist(id: Int): Flow<ResultState<String>> =flow{
        emit(ResultState.Loading)
        try {
            val lyrics = dataBase.SongPlayListDao().getLyricsFromPlaylist(id = id)
            emit(ResultState.Success(lyrics))
        }catch (e: Exception){
            emit(ResultState.Error(e.message.toString()))
        }
    }

    override suspend fun createUpdateNewPlayList(playListTable: PlayListTable): Flow<ResultState<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun deletePlayList(playListTable: PlayListTable): Flow<ResultState<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllPlayList(): Flow<ResultState<List<PlayListTable>>> {
        TODO("Not yet implemented")
    }
}