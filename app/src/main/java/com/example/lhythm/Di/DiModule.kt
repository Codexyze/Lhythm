package com.example.lhythm.Di

import android.content.Context
import androidx.room.Room
import com.example.lhythm.constants.Constants
import com.example.lhythm.core.Media.MediaPlayerManager
import com.example.lhythm.data.Local.SongPlayListDataBase
import com.example.lhythm.data.RepoIMPL.GetCategoryRepoImpl
import com.example.lhythm.data.RepoIMPL.GetAllSongsRepoImpl
import com.example.lhythm.data.RepoIMPL.SongPlayListRepoImpl
import com.example.lhythm.data.UserPrefrence.UserPrefrence
import com.example.lhythm.domain.Repository.GetAllSongRepository
import com.example.lhythm.domain.Repository.GetCategoryRepository
import com.example.lhythm.domain.Repository.SongPlayListRepository
import com.example.lhythm.domain.Usecases.DeleteClickedPlayListUseCase
import com.example.lhythm.domain.Usecases.GetAllSongUseCase
import com.example.lhythm.domain.Usecases.GetByYearASCUseCase
import com.example.lhythm.domain.Usecases.GetSongCategoryUseCase
import com.example.lhythm.domain.Usecases.GetSongDESCUsecase
import com.example.lhythm.domain.Usecases.GetSongsByArtistUseCase
import com.example.lhythm.domain.Usecases.GetSongsFromPlayListUseCase
import com.example.lhythm.domain.Usecases.InsertSongToPlayListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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

    @Provides
    fun MediaPlayerManagerInstance(@ApplicationContext context: Context): MediaPlayerManager{
         return MediaPlayerManager(context = context)
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
            name = Constants.PLAYLIST).build()
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

}