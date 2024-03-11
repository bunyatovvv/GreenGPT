package com.example.greengpt.data.source

import com.example.greengpt.data.dto.local.FavoriteDTO
import com.example.greengpt.data.service.local.RoomDAO
import javax.inject.Inject

class RoomSourceImpl @Inject constructor(
    private val dao: RoomDAO
) : RoomSource {

    override suspend fun getAllFavorites(): List<FavoriteDTO> {
        return try {
            dao.getAllFavorites()
        } catch (e: Exception) {
            emptyList()
        }
    }
    override suspend fun insertFavorite(favoriteDTO: FavoriteDTO) {
        dao.insertFavorite(favoriteDTO)
    }
    override suspend fun deleteFavorite(favoriteDTO: FavoriteDTO) {
       dao.deleteFavorite(favoriteDTO)
    }


}