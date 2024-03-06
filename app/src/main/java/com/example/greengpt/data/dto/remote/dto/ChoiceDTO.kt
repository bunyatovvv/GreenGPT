package com.example.greengpt.data.dto.remote.dto

data class ChoiceDTO(
    val finish_reason: String,
    val index: Int,
    val logprobs: Any,
    val message: MessageDTO
)