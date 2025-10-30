package com.nutrino.lhythm.Di

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.room.Room
import com.nutrino.lhythm.constants.Constants
import com.nutrino.lhythm.core.AlaramManager.AlaramManager
import com.nutrino.lhythm.core.Media.MediaPlayerManager
import com.nutrino.lhythm.data.Local.SongPlayListDataBase
import com.nutrino.lhythm.data.RepoIMPL.AudioTimmerRepoImpl
import com.nutrino.lhythm.data.RepoIMPL.FavSongRepoImpl
import com.nutrino.lhythm.data.RepoIMPL.GetCategoryRepoImpl
import com.nutrino.lhythm.data.RepoIMPL.GetAllSongsRepoImpl
import com.nutrino.lhythm.data.RepoIMPL.ImageRepoImp
import com.nutrino.lhythm.data.RepoIMPL.SongPlayListRepoImpl
import com.nutrino.lhythm.data.UserPrefrence.UserPrefrence
import com.nutrino.lhythm.domain.Repository.AudioTrimmerRepository
import com.nutrino.lhythm.domain.Repository.FavSongRepository
import com.nutrino.lhythm.domain.Repository.GetAllSongRepository
import com.nutrino.lhythm.domain.Repository.GetCategoryRepository
import com.nutrino.lhythm.domain.Repository.ImageRepository
import com.nutrino.lhythm.domain.Repository.SongPlayListRepository
import com.nutrino.lhythm.domain.Usecases.CreateUpdateNewPlayListUseCase
import com.nutrino.lhythm.domain.Usecases.DeleteClickedPlayListUseCase
import com.nutrino.lhythm.domain.Usecases.DeletePlayListSongsUseCase
import com.nutrino.lhythm.domain.Usecases.DeletePlayListUseCase
import com.nutrino.lhythm.domain.Usecases.GetAllImagesUseCase
import com.nutrino.lhythm.domain.Usecases.GetAllMappedImgSongUseCase
import com.nutrino.lhythm.domain.Usecases.GetAllPlayListSongsUseCase
import com.nutrino.lhythm.domain.Usecases.GetAllPlayListUseCase
import com.nutrino.lhythm.domain.Usecases.GetAllSongComposerASCUseCase
import com.nutrino.lhythm.domain.Usecases.GetAllSongUseCase
import com.nutrino.lhythm.domain.Usecases.GetByYearASCUseCase
import com.nutrino.lhythm.domain.Usecases.GetLyricsFromPlayListUseCase
import com.nutrino.lhythm.domain.Usecases.GetSongByPlayListIDUseCase
import com.nutrino.lhythm.domain.Usecases.GetSongCategoryUseCase
import com.nutrino.lhythm.domain.Usecases.GetSongDESCUsecase
import com.nutrino.lhythm.domain.Usecases.GetSongsByArtistUseCase
import com.nutrino.lhythm.domain.Usecases.GetSongsFromPlayListUseCase
import com.nutrino.lhythm.domain.Usecases.InsertSongToPlayListUseCase
import com.nutrino.lhythm.domain.Usecases.MapImgToSongUseCase
import com.nutrino.lhythm.domain.Usecases.SearchFromPlayListUseCase
import com.nutrino.lhythm.domain.Usecases.TrimAudioUseCase
import com.nutrino.lhythm.domain.Usecases.UpdateLyricsFromPLUseCase
import com.nutrino.lhythm.domain.Usecases.UpsertPlayListSongsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
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
        val passPharse: ByteArray = SQLiteDatabase.getBytes(Constants.SQLENCRYPTIONKEY.toCharArray())
        val factory = SupportFactory(passPharse)
        return Room.databaseBuilder(context = context, SongPlayListDataBase::class.java,
            name = Constants.PLAYLIST).openHelperFactory (
                factory = factory
            ) .fallbackToDestructiveMigration().build()
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

    @Provides
    fun provideAudioTrimmerUseCaseObj(repository: AudioTrimmerRepository): TrimAudioUseCase{
        return TrimAudioUseCase(repository = repository)

    }

    @Provides
    fun provideImageRepository(@ApplicationContext context: Context, dataBase: SongPlayListDataBase): ImageRepository{
        return ImageRepoImp(context = context, dataBase = dataBase)
    }

    @Provides
    fun provideGetAllImageUseCaseObj(repository: ImageRepository): GetAllImagesUseCase{
        return GetAllImagesUseCase(repository = repository)
    }

    @Provides
    fun provideMapImgToSongUseCase(imageRepository: ImageRepository): MapImgToSongUseCase{
        return MapImgToSongUseCase(imageRepository = imageRepository)
    }

    @Provides
    fun provideGetAllMappedImgSongUseCase(imageRepository: ImageRepository): GetAllMappedImgSongUseCase {
        return GetAllMappedImgSongUseCase(imageRepository = imageRepository)
    }

}
