package com.example.lhythm.domain.Usecases

import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.data.Local.PlayListSongMapper
import com.example.lhythm.domain.Repository.SongPlayListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSongByPlayListIDUseCase @Inject constructor(private  val songPlayListRepository:SongPlayListRepository) {
    suspend operator fun invoke(id:Int): Flow<ResultState<List<PlayListSongMapper>>> {
        return songPlayListRepository.getSongByPlayListID(id)
    }
}