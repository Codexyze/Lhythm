package com.nutrino.lhythm.domain.Usecases

import com.nutrino.lhythm.core.StateHandeling.ResultState
import com.nutrino.lhythm.data.Local.SongToImage
import com.nutrino.lhythm.domain.Repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllMappedImgSongUseCase @Inject constructor(private val imageRepository: ImageRepository) {
    suspend operator fun invoke(): Flow<ResultState<List<SongToImage>>> {
        return imageRepository.getAllMappedImgAndSong()

    }

}