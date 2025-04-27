package com.example.lhythm.domain.Usecases

import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.domain.Repository.SongPlayListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLyricsFromPlayListUseCase @Inject constructor(
    private val songPlayListRepository: SongPlayListRepository
) {
    suspend operator fun  invoke(id:Int): Flow<ResultState<String>>{
        return songPlayListRepository.getLyricsFromPlaylist(id=id)
    }
}