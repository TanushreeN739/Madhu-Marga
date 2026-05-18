package com.example.madhu_marga.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.madhu_marga.data.local.entities.HarvestEntity
import com.example.madhu_marga.data.local.entities.HiveEntity
import com.example.madhu_marga.data.local.entities.InspectionEntity
import com.example.madhu_marga.data.repository.HiveRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HiveViewModel(private val repository: HiveRepository) : ViewModel() {

    val allHives: StateFlow<List<HiveEntity>> = repository.allHives
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allHarvests: StateFlow<List<HarvestEntity>> = repository.allHarvests
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allInspections: StateFlow<List<InspectionEntity>> = repository.allInspections
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addHive(hiveId: String, location: String, condition: String) {
        viewModelScope.launch {
            repository.insertHive(HiveEntity(hiveId = hiveId, location = location, condition = condition))
        }
    }

    fun deleteHive(hive: HiveEntity) {
        viewModelScope.launch {
            repository.deleteHive(hive)
        }
    }

    fun addInspection(
        hiveId: Int,
        queenPresent: Boolean,
        pestsSeen: Boolean,
        honeyFlow: String,
        activity: String,
        notes: String,
        date: Long = System.currentTimeMillis()
    ) {
        viewModelScope.launch {
            repository.insertInspection(
                InspectionEntity(
                    hiveId = hiveId,
                    queenPresent = queenPresent,
                    pestsSeen = pestsSeen,
                    honeyFlowLevel = honeyFlow,
                    activityLevel = activity,
                    notes = notes,
                    date = date
                )
            )
        }
    }

    fun addHarvest(hiveId: Int, amount: Double, date: Long = System.currentTimeMillis()) {
        viewModelScope.launch {
            repository.insertHarvest(HarvestEntity(hiveId = hiveId, amount = amount, date = date))
        }
    }
    
    fun getInspectionsForHive(hiveId: Int) = repository.getInspectionsForHive(hiveId)
}

class HiveViewModelFactory(private val repository: HiveRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HiveViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HiveViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
