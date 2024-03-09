package com.example.greengpt.data.source

import com.example.greengpt.data.service.api.Api
import com.example.greengpt.data.dto.remote.dto.ChatDTO
import com.example.greengpt.domain.model.remote.ChatPostModel
import com.example.greengpt.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApiSourceImpl @Inject constructor(
    private val api: Api
) : ApiSource {
    override suspend fun chat(chatPostModel: ChatPostModel): Resource<ChatDTO> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.loading(null)
                val response = api.chat(chatPostModel)
                if (response.isSuccessful) {
                    response.body()?.let {
                        Resource.success(it)
                    } ?: Resource.error("null", null)
                } else {
                    val message = "error chat"
                    Resource.error(message, null)
                }
            } catch (e: Exception) {
                Resource.error(e.localizedMessage, null)
            }
        }
    }
}