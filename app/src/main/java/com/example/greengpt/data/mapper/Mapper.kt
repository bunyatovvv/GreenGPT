package com.example.greengpt.data.mapper

import com.example.greengpt.data.dto.remote.dto.ChatDTO

fun ChatDTO.toMessage() : String {

    var result = ""
    this.choices.map {
        result = it.message.content
        return@map result
    }
    return result
}

