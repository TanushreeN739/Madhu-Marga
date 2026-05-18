package com.example.madhu_marga.data.local.dao

import androidx.room.*
import com.example.madhu_marga.data.local.entities.InspectionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InspectionDao {
    @Query("SELECT * FROM inspections ORDER BY date DESC")
    fun getAllInspections(): Flow<List<InspectionEntity>>

    @Query("SELECT * FROM inspections WHERE hiveId = :hiveId ORDER BY date DESC")
    fun getInspectionsForHive(hiveId: Int): Flow<List<InspectionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInspection(inspection: InspectionEntity)

    @Delete
    suspend fun deleteInspection(inspection: InspectionEntity)
}
