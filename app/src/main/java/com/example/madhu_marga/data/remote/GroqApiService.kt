package com.example.madhu_marga.data.remote

import com.example.madhu_marga.data.remote.models.GroqRequest
import com.example.madhu_marga.data.remote.models.GroqResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface GroqApiService {
    @POST("v1/chat/completions")
    suspend fun getChatCompletion(
        @Header("Authorization") apiKey: String,
        @Body request: GroqRequest
    ): GroqResponse
}
