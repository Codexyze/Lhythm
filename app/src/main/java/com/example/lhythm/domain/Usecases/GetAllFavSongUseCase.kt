package com.example.lhythm.domain.Usecases

import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.data.Local.FavSongEntity
import com.example.lhythm.domain.Repository.FavSongRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavSongUseCase @Inject constructor(private val repository: FavSongRepository)  {

    suspend operator fun invoke(): Flow<ResultState<List<FavSongEntity>>>{
        return  repository.getAllFavSongs()
    }
}