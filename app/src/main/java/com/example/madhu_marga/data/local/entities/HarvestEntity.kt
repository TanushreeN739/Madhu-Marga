package com.example.madhu_marga.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "harvests")
data class HarvestEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val hiveId: Int,
    val amount: Double,
    val date: Long = System.currentTimeMillis()
)
