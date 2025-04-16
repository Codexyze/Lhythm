package com.example.lhythm.Di

import android.content.Context
import com.example.lhythm.core.Media.MediaPlayerManager
import com.example.lhythm.data.RepoIMPL.GetCategoryRepoImpl
import com.example.lhythm.data.RepoIMPL.GetAllSongsRepoImpl
import com.example.lhythm.data.UserPrefrence.UserPrefrence
import com.example.lhythm.domain.Repository.GetAllSongRepository
import com.example.lhythm.domain.Repository.GetCategoryRepository
import com.example.lhythm.domain.Usecases.GetAllSongUseCase
import com.example.lhythm.domain.Usecases.GetSongCategoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


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

}