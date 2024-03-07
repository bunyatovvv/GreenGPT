package com.example.greengpt.data.service.api

import com.example.greengpt.constants.TEXT_COMPLETIONS_TURBO
import com.example.greengpt.data.dto.remote.dto.ChatDTO
import com.example.greengpt.domain.remote.model.ChatPostModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Streaming

interface Api {
    @POST(TEXT_COMPLETIONS_TURBO)
    suspend fun chat(@Body chatPostModel: ChatPostModel) : Response<ChatDTO>
}