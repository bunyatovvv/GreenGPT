package com.example.greengpt.domain.model.remote

data class ChatPostModel(
    val messages: List<Message>,
    val model: String = "gpt-3.5-turbo",
    val temperature: Double = 0.5
)