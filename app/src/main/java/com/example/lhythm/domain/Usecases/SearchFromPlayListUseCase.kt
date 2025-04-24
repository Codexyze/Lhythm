package com.example.lhythm.domain.Usecases

import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.data.Local.SongEntity
import com.example.lhythm.domain.Repository.SongPlayListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchFromPlayListUseCase @Inject constructor(private val repository: SongPlayListRepository) {
    suspend operator  fun invoke(query: String): Flow<ResultState<List<SongEntity>>>{
        return repository.searchFromPlaylist(query = query)
    }
}