package com.example.madhu_marga

import android.app.Application
import androidx.room.Room
import com.example.madhu_marga.data.local.AppDatabase
import com.example.madhu_marga.data.remote.GroqApiService
import com.example.madhu_marga.data.repository.AiRepository
import com.example.madhu_marga.data.repository.HiveRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MadhuMargaApplication : Application() {
    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "madhu_marga_database"
        ).build()
    }

    val repository by lazy {
        HiveRepository(
            database.hiveDao(),
            database.inspectionDao(),
            database.harvestDao()
        )
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.groq.com/openai/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val groqApiService by lazy {
        retrofit.create(GroqApiService::class.java)
    }

    val aiRepository by lazy {
        AiRepository(groqApiService)
    }
}
