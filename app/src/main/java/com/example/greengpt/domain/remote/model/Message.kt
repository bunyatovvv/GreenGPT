package com.example.greengpt.domain.remote.model

data class Message(
    val content: String,
    val role: String = "user"
)