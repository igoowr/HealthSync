package com.example.healthsync.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthsync.data.database.entities.Measurement
import com.example.healthsync.data.repository.MeasurementRepository
import kotlinx.coroutines.launch

class HistoricoViewModel(private val repository: MeasurementRepository) : ViewModel() {

    val allMeasurements: LiveData<List<Measurement>> = repository.allMeasurements

    fun insert(measurement: Measurement) = viewModelScope.launch {
        repository.insert(measurement)
    }

    fun delete(measurement: Measurement) = viewModelScope.launch {
        repository.deleteById(measurement.id)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }
}
