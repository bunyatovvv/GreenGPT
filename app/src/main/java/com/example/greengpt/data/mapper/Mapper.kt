package com.example.greengpt.data.mapper

import com.example.greengpt.data.dto.remote.dto.ChatDTO
import com.example.greengpt.data.dto.remote.dto.ChoiceDTO
import com.example.greengpt.data.dto.remote.dto.MessageDTO
import com.example.greengpt.domain.remote.model.Message
import java.lang.StringBuilder

fun ChatDTO.toMessage() : String {

    var result = ""
    this.choices.map {
        result = it.message.content
        return@map result
    }
    return result
}

