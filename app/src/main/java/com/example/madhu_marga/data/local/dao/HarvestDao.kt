package com.example.madhu_marga.data.local.dao

import androidx.room.*
import com.example.madhu_marga.data.local.entities.HarvestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HarvestDao {
    @Query("SELECT * FROM harvests ORDER BY date DESC")
    fun getAllHarvests(): Flow<List<HarvestEntity>>

    @Query("SELECT * FROM harvests WHERE hiveId = :hiveId ORDER BY date DESC")
    fun getHarvestsForHive(hiveId: Int): Flow<List<HarvestEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHarvest(harvest: HarvestEntity)

    @Delete
    suspend fun deleteHarvest(harvest: HarvestEntity)
}
