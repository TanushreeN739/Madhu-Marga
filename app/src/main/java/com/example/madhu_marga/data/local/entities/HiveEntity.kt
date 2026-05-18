package com.example.madhu_marga.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hives")
data class HiveEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val hiveId: String,
    val location: String,
    val condition: String,
    val createdAt: Long = System.currentTimeMillis()
)
