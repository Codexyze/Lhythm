package com.example.lhythm.data.RepoIMPL

import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.data.Local.SongEntity
import com.example.lhythm.domain.Repository.SongPlayListRepository
import kotlinx.coroutines.flow.Flow

class SongPlayListRepoImpl: SongPlayListRepository {
    override suspend fun insertSongToPlayList(songEntity: SongEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun getSongsFromPlayList(): Flow<ResultState<List<SongEntity>>> {
        TODO("Not yet implemented")
    }
}