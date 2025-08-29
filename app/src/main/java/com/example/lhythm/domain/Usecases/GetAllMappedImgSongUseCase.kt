package com.example.lhythm.domain.Usecases

import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.data.Local.SongToImage
import com.example.lhythm.domain.Repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllMappedImgSongUseCase @Inject constructor(private val imageRepository: ImageRepository) {
    suspend operator fun invoke(): Flow<ResultState<List<SongToImage>>> {
        return imageRepository.getAllMappedImgAndSong()

    }

}