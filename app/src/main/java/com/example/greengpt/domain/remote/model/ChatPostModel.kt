package com.example.greengpt.domain.remote.model

data class ChatPostModel(
    val messages: List<Message>,
    val model: String = "gpt-3.5-turbo",
    val temperature: Double = 0.5
)