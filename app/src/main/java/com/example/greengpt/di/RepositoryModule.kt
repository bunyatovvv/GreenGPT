package com.example.greengpt.di

import com.example.greengpt.data.service.api.Api
import com.example.greengpt.data.repository.ApiRepoImpl
import com.example.greengpt.data.repository.RoomRepoImpl
import com.example.greengpt.data.service.local.RoomDAO
import com.example.greengpt.data.source.ApiSource
import com.example.greengpt.data.source.ApiSourceImpl
import com.example.greengpt.data.source.RoomSource
import com.example.greengpt.data.source.RoomSourceImpl
import com.example.greengpt.domain.repository.ApiRepo
import com.example.greengpt.domain.repository.RoomRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideApiSource(api: Api) = ApiSourceImpl(api) as ApiSource
    @Singleton
    @Provides
    fun provideApiRepo(apiSource: ApiSourceImpl) = ApiRepoImpl(apiSource) as ApiRepo
    @Singleton
    @Provides
    fun provideRoomSource(dao : RoomDAO) = RoomSourceImpl(dao) as RoomSource
    @Singleton
    @Provides
    fun provideRoomRepo(roomSource: RoomSourceImpl) = RoomRepoImpl(roomSource) as RoomRepo


}