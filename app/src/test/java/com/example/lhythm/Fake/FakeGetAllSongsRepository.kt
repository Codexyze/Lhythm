package com.example.lhythm.Fake

import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.data.Song.Song
import com.example.lhythm.domain.Repository.GetAllSongRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeGetAllSongsRepository: GetAllSongRepository {

    val fakeSongList = listOf(
        Song(
            id = "1",
            path = "/music/track1.mp3",
            size = "4000",
            album = "Chill Vibes",
            title = "Peaceful Breeze",
            artist = "Lofi King",
            duration = "180000",
            year = "2022",
            composer = "DJ Breeze",
            albumId = "101",
            albumArt = null
        ),
        Song(
            id = "2",
            path = "/music/track2.mp3",
            size = "5200",
            album = "Workout Beats",
            title = "Pump It Up",
            artist = "PowerMode",
            duration = "210000",
            year = "2021",
            composer = "BeatSmith",
            albumId = "102",
            albumArt = null
        ),
        Song(
            id = "3",
            path = "/music/track3.mp3",
            size = "4800",
            album = "Retro Hits",
            title = "Cassette Days",
            artist = "OldSchool",
            duration = "200000",
            year = "1995",
            composer = "MC Tape",
            albumId = "103",
            albumArt = null
        ),
        Song(
            id = "4",
            path = "/music/track4.mp3",
            size = "3500",
            album = "Relaxation",
            title = "Deep Ocean",
            artist = "ZenBeats",
            duration = "230000",
            year = "2023",
            composer = "Calm Lord",
            albumId = "104",
            albumArt = null
        )

    )

    override suspend fun getAllSongs(): Flow<ResultState<List<Song>>> =flow{
       emit(ResultState.Success(fakeSongList))
    }
}