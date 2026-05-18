package com.example.madhu_marga.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inspections")
data class InspectionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val hiveId: Int,
    val queenPresent: Boolean,
    val pestsSeen: Boolean,
    val honeyFlowLevel: String,
    val activityLevel: String,
    val notes: String,
    val date: Long = System.currentTimeMillis()
)
