package com.example.greengpt.data.source

import com.example.greengpt.data.dto.remote.dto.ChatDTO
import com.example.greengpt.domain.remote.model.ChatPostModel
import com.example.greengpt.util.Resource

interface ApiSource {

    suspend fun chat(chatPostModel: ChatPostModel) : Resource<ChatDTO>
}