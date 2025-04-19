package com.example.lhythm.domain.Usecases

import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.data.Local.SongEntity
import com.example.lhythm.domain.Repository.SongPlayListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSongsFromPlayListUseCase @Inject constructor(private val songPlayListRepository: SongPlayListRepository) {
    suspend operator fun invoke(): Flow<ResultState<List<SongEntity>>>{
        return songPlayListRepository.getSongsFromPlayList()
    }
}