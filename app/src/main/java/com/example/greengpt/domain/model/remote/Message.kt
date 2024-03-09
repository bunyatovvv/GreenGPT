package com.example.greengpt.domain.model.remote

data class Message(
    val content: String,
    val role: String = "user"
)