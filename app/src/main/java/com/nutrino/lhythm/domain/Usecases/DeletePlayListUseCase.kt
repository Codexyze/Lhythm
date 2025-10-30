package com.nutrino.lhythm.domain.Usecases

import com.nutrino.lhythm.core.StateHandeling.ResultState
import com.nutrino.lhythm.data.Local.PlayListTable
import com.nutrino.lhythm.domain.Repository.SongPlayListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeletePlayListUseCase @Inject constructor(private val songPlayListRepository: SongPlayListRepository) {
    suspend operator fun invoke(playListTable: PlayListTable): Flow<ResultState<String>> {
        return songPlayListRepository.deletePlayList(playListTable =playListTable )
    }
}
