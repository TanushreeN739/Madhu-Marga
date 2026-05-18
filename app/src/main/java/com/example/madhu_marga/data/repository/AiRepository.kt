package com.example.madhu_marga.data.repository

import com.example.madhu_marga.BuildConfig
import com.example.madhu_marga.data.remote.GroqApiService
import com.example.madhu_marga.data.remote.models.GroqRequest
import com.example.madhu_marga.data.remote.models.Message

class AiRepository(private val apiService: GroqApiService) {
    private val apiKey = "Bearer ${BuildConfig.GROQ_API_KEY}"

    suspend fun getHiveSuggestions(observations: String): String {
        val prompt = """
            You are a professional beekeeping assistant. 
            Based on the following hive observations, provide:
            1. Hive health assessment.
            2. Smart recommendations for the keeper.
            3. Any warning alerts if necessary.
            
            Observations:
            ${observations}
            
            Please provide the response in a clear, encouraging, and structured format.
        """.trimIndent()

        val request = GroqRequest(
            messages = listOf(
                Message(role = "system", content = "You are a helpful and expert beekeeping assistant."),
                Message(role = "user", content = prompt)
            )
        )

        return try {
            val response = apiService.getChatCompletion(apiKey, request)
            response.choices.firstOrNull()?.message?.content ?: "No suggestion available at the moment."
        } catch (e: Exception) {
            "Error: ${e.message}. Please check your internet connection or API key."
        }
    }
}
