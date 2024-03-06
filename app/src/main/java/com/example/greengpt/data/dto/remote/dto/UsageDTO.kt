package com.example.greengpt.data.dto.remote.dto

data class UsageDTO(
    val completion_tokens: Int,
    val prompt_tokens: Int,
    val total_tokens: Int
)