package com.example.greengpt.data.repository

import com.example.greengpt.data.dto.local.FavoriteDTO
import com.example.greengpt.data.source.RoomSourceImpl
import com.example.greengpt.domain.repository.RoomRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RoomRepoImpl @Inject constructor(
   private val roomSource: RoomSourceImpl
) : RoomRepo {
    override suspend fun getAllFavorites(): Flow<List<FavoriteDTO>> = flow {
        emit(roomSource.getAllFavorites())
    }
    override suspend fun insertFavorite(favoriteDTO: FavoriteDTO) {
        roomSource.insertFavorite(favoriteDTO)
    }
    override suspend fun deleteFavorite(favoriteDTO: FavoriteDTO) {
        roomSource.deleteFavorite(favoriteDTO)
    }

}