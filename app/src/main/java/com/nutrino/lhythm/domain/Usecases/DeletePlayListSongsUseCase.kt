package com.nutrino.lhythm.domain.Usecases

import com.nutrino.lhythm.core.StateHandeling.ResultState
import com.nutrino.lhythm.data.Local.PlayListSongMapper
import com.nutrino.lhythm.domain.Repository.SongPlayListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeletePlayListSongsUseCase @Inject constructor(private  val songPlayListRepository:SongPlayListRepository) {
    suspend operator fun invoke(playListSongMapper: PlayListSongMapper): Flow<ResultState<String>>{
        return songPlayListRepository.deletePlayListSongs(playListSongMapper = playListSongMapper)
    }
}