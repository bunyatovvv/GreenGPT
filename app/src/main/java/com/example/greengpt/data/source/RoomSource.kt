package com.example.greengpt.data.source

import com.example.greengpt.data.dto.local.FavoriteDTO

interface RoomSource {

    suspend fun getAllFavorites() : List<FavoriteDTO>
    suspend fun insertFavorite(favoriteDTO: FavoriteDTO)
    suspend fun deleteFavorite(favoriteDTO: FavoriteDTO)
}