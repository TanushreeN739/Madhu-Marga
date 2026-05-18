package com.example.madhu_marga.data.remote.models

data class GroqRequest(
    val model: String = "llama-3.3-70b-versatile",
    val messages: List<Message>
)

data class Message(
    val role: String,
    val content: String
)
