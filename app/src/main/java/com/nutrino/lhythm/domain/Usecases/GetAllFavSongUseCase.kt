package com.nutrino.lhythm.domain.Usecases

import com.nutrino.lhythm.core.StateHandeling.ResultState
import com.nutrino.lhythm.data.Local.FavSongEntity
import com.nutrino.lhythm.domain.Repository.FavSongRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavSongUseCase @Inject constructor(private val repository: FavSongRepository)  {

    suspend operator fun invoke(): Flow<ResultState<List<FavSongEntity>>>{
        return  repository.getAllFavSongs()
    }
}