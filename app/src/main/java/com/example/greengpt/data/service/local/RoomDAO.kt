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
    fun getAllFavorites() : List<FavoriteDTO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteDTO: FavoriteDTO)

    @Delete
    suspend fun deleteFavorite(favoriteDTO: FavoriteDTO)
}