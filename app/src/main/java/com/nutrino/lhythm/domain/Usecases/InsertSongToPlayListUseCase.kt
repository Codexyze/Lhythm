package com.nutrino.lhythm.domain.Usecases

import com.nutrino.lhythm.core.StateHandeling.ResultState
import com.nutrino.lhythm.data.Local.SongEntity
import com.nutrino.lhythm.domain.Repository.SongPlayListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertSongToPlayListUseCase @Inject constructor(private val songPlayListRepository: SongPlayListRepository) {
    suspend operator fun invoke(songEntity: SongEntity): Flow<ResultState<String>>{
       return songPlayListRepository.insertSongToPlayList(songEntity = songEntity)
    }
}