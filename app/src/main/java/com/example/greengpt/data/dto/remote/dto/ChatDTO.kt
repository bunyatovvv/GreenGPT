package com.example.greengpt.data.dto.remote.dto

import com.google.gson.annotations.SerializedName

data class ChatDTO(
    val choices: List<ChoiceDTO>,
    val created: Int,
    val id: String,
    val model: String,
    @SerializedName("object")
    val `object`: String,
    val system_fingerprint: String,
    val usage: UsageDTO
)