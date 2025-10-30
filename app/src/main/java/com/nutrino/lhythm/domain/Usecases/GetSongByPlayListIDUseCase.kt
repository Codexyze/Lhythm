package com.nutrino.lhythm.domain.Usecases

import com.nutrino.lhythm.core.StateHandeling.ResultState
import com.nutrino.lhythm.data.Local.PlayListSongMapper
import com.nutrino.lhythm.domain.Repository.SongPlayListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSongByPlayListIDUseCase @Inject constructor(private  val songPlayListRepository:SongPlayListRepository) {
    suspend operator fun invoke(id:Int): Flow<ResultState<List<PlayListSongMapper>>> {
        return songPlayListRepository.getSongByPlayListID(id)
    }
}