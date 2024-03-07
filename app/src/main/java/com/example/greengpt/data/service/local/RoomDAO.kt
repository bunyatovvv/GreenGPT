package com.example.greengpt.data.service.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.greengpt.data.dto.local.FavoriteDTO

@Dao
interface RoomDAO {

    @Query("SELECT * FROM favorite_table")
    suspend fun getAllFavorites(): List<FavoriteDTO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoritesDTO: FavoriteDTO)

    @Delete
    suspend fun deleteFavorite(favoritesDTO: FavoriteDTO)
}