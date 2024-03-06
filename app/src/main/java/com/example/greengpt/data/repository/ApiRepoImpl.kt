package com.example.greengpt.data.repository

import com.example.greengpt.data.dto.remote.dto.ChatDTO
import com.example.greengpt.data.source.ApiSource
import com.example.greengpt.domain.remote.model.ChatPostModel
import com.example.greengpt.domain.repository.ApiRepo
import com.example.greengpt.util.Resource
import com.example.greengpt.util.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class ApiRepoImpl @Inject constructor(
    private val source : ApiSource
) : ApiRepo {
    override suspend fun chat(chatPostModel: ChatPostModel): Flow<Resource<ChatDTO>> = flow {
        emit(Resource.loading(null))
        val response = source.chat(chatPostModel)
        when (response.status) {
            Status.SUCCESS -> {
                emit(Resource.success(response.data))
            }

            Status.ERROR -> {
                emit(Resource.error(response.message ?: "Error", null))
            }

            else -> {
            }
        }
    }
}