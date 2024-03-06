package com.example.greengpt.data.mapper

import com.example.greengpt.data.dto.remote.dto.ChoiceDTO
import com.example.greengpt.data.dto.remote.dto.MessageDTO
import com.example.greengpt.domain.remote.model.Message

fun MessageDTO.toContent() : String{
    return this.content
}

