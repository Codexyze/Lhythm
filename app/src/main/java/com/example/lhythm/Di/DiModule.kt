package com.example.lhythm.Di

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.room.Room
import com.example.lhythm.constants.Constants
import com.example.lhythm.core.AlaramManager.AlaramManager
import com.example.lhythm.core.Media.MediaPlayerManager
import com.example.lhythm.data.Local.SongPlayListDataBase
import com.example.lhythm.data.RepoIMPL.AudioTimmerRepoImpl
import com.example.lhythm.data.RepoIMPL.FavSongRepoImpl
import com.example.lhythm.data.RepoIMPL.GetCategoryRepoImpl
import com.example.lhythm.data.RepoIMPL.GetAllSongsRepoImpl
import com.example.lhythm.data.RepoIMPL.SongPlayListRepoImpl
import com.example.lhythm.data.UserPrefrence.UserPrefrence
import com.example.lhythm.domain.Repository.AudioTrimmerRepository
import com.example.lhythm.domain.Repository.FavSongRepository
import com.example.lhythm.domain.Repository.GetAllSongRepository
import com.example.lhythm.domain.Repository.GetCategoryRepository
import com.example.lhythm.domain.Repository.SongPlayListRepository
import com.example.lhythm.domain.Usecases.CreateUpdateNewPlayListUseCase
import com.example.lhythm.domain.Usecases.DeleteClickedPlayListUseCase
import com.example.lhythm.domain.Usecases.DeletePlayListSongsUseCase
import com.example.lhythm.domain.Usecases.DeletePlayListUseCase
import com.example.lhythm.domain.Usecases.GetAllPlayListSongsUseCase
import com.example.lhythm.domain.Usecases.GetAllPlayListUseCase
import com.example.lhythm.domain.Usecases.GetAllSongComposerASCUseCase
import com.example.lhythm.domain.Usecases.GetAllSongUseCase
import com.example.lhythm.domain.Usecases.GetByYearASCUseCase
import com.example.lhythm.domain.Usecases.GetLyricsFromPlayListUseCase
import com.example.lhythm.domain.Usecases.GetSongByPlayListIDUseCase
import com.example.lhythm.domain.Usecases.GetSongCategoryUseCase
import com.example.lhythm.domain.Usecases.GetSongDESCUsecase
import com.example.lhythm.domain.Usecases.GetSongsByArtistUseCase
import com.example.lhythm.domain.Usecases.GetSongsFromPlayListUseCase
import com.example.lhythm.domain.Usecases.InsertSongToPlayListUseCase
import com.example.lhythm.domain.Usecases.SearchFromPlayListUseCase
import com.example.lhythm.domain.Usecases.TrimAudioUseCase
import com.example.lhythm.domain.Usecases.UpdateLyricsFromPLUseCase
import com.example.lhythm.domain.Usecases.UpsertPlayListSongsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.UUID
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)

object DiModule {

    @Provides
    fun userPrefObject(@ApplicationContext context: Context): UserPrefrence{
       return UserPrefrence(context = context)
    }

    @Provides
    fun getAllSongsRepoIntefcae(@ApplicationContext context: Context): GetAllSongRepository{
        return GetAllSongsRepoImpl(context = context )
    }
    @Provides
    fun getAllSongUseCase(@ApplicationContext context: Context): GetAllSongUseCase{
        return GetAllSongUseCase(getAllSongRepository = getAllSongsRepoIntefcae(context = context))
    }

    @OptIn(UnstableApi::class)
    @Provides
    @Singleton
    fun MediaPlayerManagerInstance(@ApplicationContext context: Context,exoPlayer: ExoPlayer,mediaSession: MediaSession): MediaPlayerManager{
         return MediaPlayerManager(exoPlayer = exoPlayer, mediaSession =
             mediaSession, context = context)
    }

    @Provides
    fun getCategoryRepoInterface(@ApplicationContext context: Context): GetCategoryRepository{
        return GetCategoryRepoImpl(context = context)
    }

    @Provides
    fun getAllSongsASCUsecase(@ApplicationContext context: Context): GetSongCategoryUseCase{
        return GetSongCategoryUseCase(getCategoryRepository = getCategoryRepoInterface(context = context))
    }

    @Provides
    fun getAllSongsDESCUsecase(@ApplicationContext context: Context): GetSongDESCUsecase{
        return GetSongDESCUsecase(getCategoryRepository = getCategoryRepoInterface(context = context))
    }

    @Provides
    fun getSongsByArtistUseCase(@ApplicationContext context: Context):GetSongsByArtistUseCase{
        return GetSongsByArtistUseCase(repository = getCategoryRepoInterface(context = context))
    }

    @Provides
    fun getSongsByAscUseCase(@ApplicationContext context: Context): GetByYearASCUseCase{
        return GetByYearASCUseCase(repository = getCategoryRepoInterface(context = context))
    }

    @Singleton
    @Provides
    fun provideDataBaseBuilderObj(@ApplicationContext context: Context): SongPlayListDataBase{
        return Room.databaseBuilder(context = context, SongPlayListDataBase::class.java,
            name = Constants.PLAYLIST) .fallbackToDestructiveMigration().build()
    }

    @Provides
    fun SongPlayListRepoInterfaceObj(@ApplicationContext context: Context): SongPlayListRepository{
        return SongPlayListRepoImpl(dataBase = provideDataBaseBuilderObj(context = context))
    }

    @Provides
    fun GetSongsFromPlayListUseCaseObj(@ApplicationContext context: Context): GetSongsFromPlayListUseCase{
        return GetSongsFromPlayListUseCase(songPlayListRepository = SongPlayListRepoInterfaceObj(context = context))
    }

    @Provides
    fun InsertSongToPlayListUseCaseObj(@ApplicationContext context: Context): InsertSongToPlayListUseCase{
        return InsertSongToPlayListUseCase(songPlayListRepository = SongPlayListRepoInterfaceObj(context = context))
    }
    @Provides
    fun deleteSongFromPlayListUseCaseObj(@ApplicationContext context: Context): DeleteClickedPlayListUseCase{
        return DeleteClickedPlayListUseCase(songPlayListRepository = SongPlayListRepoInterfaceObj(context = context))
    }

    @Provides
    fun provideFavSongInterfaceObj(@ApplicationContext context: Context): FavSongRepository{
        return FavSongRepoImpl(dataBase = provideDataBaseBuilderObj(context = context))
    }

    @Provides
    fun SearchFromPlayListUseCaseObj(@ApplicationContext context: Context):SearchFromPlayListUseCase{
       return SearchFromPlayListUseCase(repository = SongPlayListRepoInterfaceObj(context = context))
    }

   @Provides
   fun GetAllSongsByComposerAscObjUseCase(@ApplicationContext context: Context): GetAllSongComposerASCUseCase{
       return GetAllSongComposerASCUseCase(getCategoryRepository = GetCategoryRepoImpl(context = context))
   }

    @Provides
    fun GetLyricsFromPlaylistUsecaseobj(@ApplicationContext context: Context): GetLyricsFromPlayListUseCase{
        return GetLyricsFromPlayListUseCase(songPlayListRepository = SongPlayListRepoInterfaceObj(context = context))
    }

    @Singleton
    @Provides
    fun exoplayerObjectBuilder(@ApplicationContext context: Context): ExoPlayer{
        return ExoPlayer.Builder(context).build()
    }

    @Singleton
    @Provides
    fun MediaSessionBuilderObj(@ApplicationContext context: Context,player: ExoPlayer): MediaSession{
        return MediaSession.Builder(context,player).setId(UUID.randomUUID().toString()).build()
    }

    //Playlist Injections
    @Provides
    fun createUpdatePlayListObj(@ApplicationContext context: Context):CreateUpdateNewPlayListUseCase{
        return CreateUpdateNewPlayListUseCase(songPlayListRepository = SongPlayListRepoInterfaceObj(context = context))
    }

    @Provides
    fun deletePlayListObj(@ApplicationContext context: Context):DeletePlayListUseCase{
        return DeletePlayListUseCase(songPlayListRepository = SongPlayListRepoInterfaceObj(context = context))
    }

    @Provides
    fun getAllPlayListObj(@ApplicationContext context: Context): GetAllPlayListUseCase{
        return GetAllPlayListUseCase(songPlayListRepository = SongPlayListRepoInterfaceObj(context = context))
    }

    //playlist usecase

    @Provides
    fun getAllPlayListSongs(songPlayListRepository: SongPlayListRepository): GetAllPlayListSongsUseCase{
        return GetAllPlayListSongsUseCase(songPlayListRepository = songPlayListRepository)
    }

    @Provides
    fun deletePlayListSongsUseCase(songPlayListRepository: SongPlayListRepository): DeletePlayListSongsUseCase{
        return DeletePlayListSongsUseCase(songPlayListRepository = songPlayListRepository)
    }

    @Provides
    fun upsertPlayListSongsUseCase(songPlayListRepository: SongPlayListRepository): UpsertPlayListSongsUseCase{
        return UpsertPlayListSongsUseCase(songPlayListRepository = songPlayListRepository)
    }

    @Provides
    fun getSongByPlayListIDUseCase(songPlayListRepository: SongPlayListRepository): GetSongByPlayListIDUseCase{
        return GetSongByPlayListIDUseCase(songPlayListRepository = songPlayListRepository)
    }

    @Provides
    fun updateLyricsFromPLUseCase(songPlayListRepository: SongPlayListRepository): UpdateLyricsFromPLUseCase{
        return UpdateLyricsFromPLUseCase(songPlayListRepository = songPlayListRepository)
    }
    @Provides
    fun getAlaramManagerObject(@ApplicationContext context: Context): AlaramManager{
        return AlaramManager(context = context)
    }

    @UnstableApi
    @Provides
    fun provideAudioTrimmerRepositoryObj(@ApplicationContext context: Context): AudioTrimmerRepository{
        return AudioTimmerRepoImpl()
    }

    fun provideAudioTrimmerUseCaseObj(repository: AudioTrimmerRepository): TrimAudioUseCase{
        return TrimAudioUseCase(repository = repository)

    }

}
