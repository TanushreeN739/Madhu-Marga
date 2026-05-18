package com.example.madhu_marga.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.madhu_marga.data.local.dao.HarvestDao
import com.example.madhu_marga.data.local.dao.HiveDao
import com.example.madhu_marga.data.local.dao.InspectionDao
import com.example.madhu_marga.data.local.entities.HarvestEntity
import com.example.madhu_marga.data.local.entities.HiveEntity
import com.example.madhu_marga.data.local.entities.InspectionEntity

@Database(
    entities = [HiveEntity::class, InspectionEntity::class, HarvestEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun hiveDao(): HiveDao
    abstract fun inspectionDao(): InspectionDao
    abstract fun harvestDao(): HarvestDao
}
