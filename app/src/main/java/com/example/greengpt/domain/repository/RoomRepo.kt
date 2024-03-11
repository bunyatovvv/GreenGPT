package com.example.greengpt.domain.repository

import com.example.greengpt.data.dto.local.FavoriteDTO
import kotlinx.coroutines.flow.Flow

interface RoomRepo {

    suspend fun getAllFavorites() : Flow<List<FavoriteDTO>>
    suspend fun insertFavorite(favoriteDTO: FavoriteDTO)
    suspend fun deleteFavorite(favoriteDTO: FavoriteDTO)



}