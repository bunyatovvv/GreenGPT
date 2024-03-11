package com.example.greengpt.di

import android.content.Context
import androidx.room.Room
import com.example.greengpt.data.service.local.RoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Singleton
    @Provides
    fun provideRoomDB(@ApplicationContext context : Context) = Room.databaseBuilder(
        context,
        RoomDB::class.java,"gpt-db"
    ).build()
    @Singleton
    @Provides
    fun provideDao(db : RoomDB) = db.getDao()
}