package com.example.madhu_marga.data.repository

import com.example.madhu_marga.data.local.dao.HarvestDao
import com.example.madhu_marga.data.local.dao.HiveDao
import com.example.madhu_marga.data.local.dao.InspectionDao
import com.example.madhu_marga.data.local.entities.HarvestEntity
import com.example.madhu_marga.data.local.entities.HiveEntity
import com.example.madhu_marga.data.local.entities.InspectionEntity
import kotlinx.coroutines.flow.Flow

class HiveRepository(
    private val hiveDao: HiveDao,
    private val inspectionDao: InspectionDao,
    private val harvestDao: HarvestDao
) {
    // Hive operations
    val allHives: Flow<List<HiveEntity>> = hiveDao.getAllHives()
    
    suspend fun insertHive(hive: HiveEntity) = hiveDao.insertHive(hive)
    
    suspend fun deleteHive(hive: HiveEntity) = hiveDao.deleteHive(hive)
    
    suspend fun getHiveById(id: Int) = hiveDao.getHiveById(id)

    // Inspection operations
    val allInspections: Flow<List<InspectionEntity>> = inspectionDao.getAllInspections()

    fun getInspectionsForHive(hiveId: Int): Flow<List<InspectionEntity>> = 
        inspectionDao.getInspectionsForHive(hiveId)
        
    suspend fun insertInspection(inspection: InspectionEntity) = 
        inspectionDao.insertInspection(inspection)

    // Harvest operations
    val allHarvests: Flow<List<HarvestEntity>> = harvestDao.getAllHarvests()
    
    fun getHarvestsForHive(hiveId: Int): Flow<List<HarvestEntity>> = 
        harvestDao.getHarvestsForHive(hiveId)
        
    suspend fun insertHarvest(harvest: HarvestEntity) = 
        harvestDao.insertHarvest(harvest)
}
