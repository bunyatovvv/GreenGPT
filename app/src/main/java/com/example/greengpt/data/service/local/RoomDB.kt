package com.example.greengpt.data.service.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.greengpt.data.dto.local.FavoriteDTO

@Database(entities = [FavoriteDTO::class], version = 1, exportSchema = true)
abstract class RoomDB : RoomDatabase() {

    abstract fun getDao(): RoomDAO
}