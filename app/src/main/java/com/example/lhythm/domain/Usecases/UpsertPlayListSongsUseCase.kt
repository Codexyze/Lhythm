package com.example.lhythm.domain.Usecases

import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.data.Local.PlayListSongMapper
import com.example.lhythm.domain.Repository.SongPlayListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpsertPlayListSongsUseCase @Inject constructor(private  val songPlayListRepository:SongPlayListRepository) {
    suspend operator fun invoke(playListSongMapper: PlayListSongMapper): Flow<ResultState<String>> {
        return songPlayListRepository.upsertPlayListSongs(playListSongMapper = playListSongMapper)
    }
}