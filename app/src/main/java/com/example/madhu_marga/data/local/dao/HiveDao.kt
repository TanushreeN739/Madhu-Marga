package com.example.madhu_marga.data.local.dao

import androidx.room.*
import com.example.madhu_marga.data.local.entities.HiveEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HiveDao {
    @Query("SELECT * FROM hives ORDER BY createdAt DESC")
    fun getAllHives(): Flow<List<HiveEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHive(hive: HiveEntity)

    @Delete
    suspend fun deleteHive(hive: HiveEntity)

    @Query("SELECT * FROM hives WHERE id = :id")
    suspend fun getHiveById(id: Int): HiveEntity?
}
