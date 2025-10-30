package com.nutrino.lhythm.domain.Usecases

import com.nutrino.lhythm.core.StateHandeling.ResultState
import com.nutrino.lhythm.data.Local.SongEntity
import com.nutrino.lhythm.domain.Repository.SongPlayListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchFromPlayListUseCase @Inject constructor(private val repository: SongPlayListRepository) {
    suspend operator  fun invoke(query: String): Flow<ResultState<List<SongEntity>>>{
        return repository.searchFromPlaylist(query = query)
    }
}