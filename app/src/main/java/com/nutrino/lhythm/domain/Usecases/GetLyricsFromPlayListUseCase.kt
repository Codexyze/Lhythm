package com.nutrino.lhythm.domain.Usecases

import com.nutrino.lhythm.core.StateHandeling.ResultState
import com.nutrino.lhythm.domain.Repository.SongPlayListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLyricsFromPlayListUseCase @Inject constructor(
    private val songPlayListRepository: SongPlayListRepository
) {
    suspend operator fun  invoke(id:Int): Flow<ResultState<String>>{
        return songPlayListRepository.getLyricsFromPlaylist(id=id)
    }
}