package com.example.lhythm.domain.Usecases

import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.data.Local.PlayListTable
import com.example.lhythm.domain.Repository.SongPlayListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeletePlayListUseCase @Inject constructor(private val songPlayListRepository: SongPlayListRepository) {
    suspend operator fun invoke(playListTable: PlayListTable): Flow<ResultState<String>> {
        return songPlayListRepository.deletePlayList(playListTable =playListTable )
    }
}
