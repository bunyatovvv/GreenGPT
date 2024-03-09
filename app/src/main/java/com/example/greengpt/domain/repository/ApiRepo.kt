package com.example.greengpt.domain.repository

import com.example.greengpt.data.dto.remote.dto.ChatDTO
import com.example.greengpt.domain.model.remote.ChatPostModel
import com.example.greengpt.util.Resource
import kotlinx.coroutines.flow.Flow

interface ApiRepo {

    suspend fun chat(chatPostModel: ChatPostModel) : Flow<Resource<ChatDTO>>
}