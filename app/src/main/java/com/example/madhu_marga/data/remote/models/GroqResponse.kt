package com.example.madhu_marga.data.remote.models

data class GroqResponse(
    val id: String,
    val choices: List<Choice>
)

data class Choice(
    val message: Message
)
