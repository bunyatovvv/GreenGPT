package com.example.greengpt.di

import com.example.greengpt.data.service.api.Api
import com.example.greengpt.data.repository.ApiRepoImpl
import com.example.greengpt.data.source.ApiSource
import com.example.greengpt.data.source.ApiSourceImpl
import com.example.greengpt.domain.repository.ApiRepo
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
    fun provideApiRepo(apiSource: ApiSource) = ApiRepoImpl(apiSource) as ApiRepo
}