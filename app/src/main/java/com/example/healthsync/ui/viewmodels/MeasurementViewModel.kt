package com.example.healthsync.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthsync.data.database.entities.Measurement
import com.example.healthsync.data.repository.MeasurementRepository
import kotlinx.coroutines.launch

class MeasurementViewModel(private val repository: MeasurementRepository) : ViewModel() {

    fun insert(measurement: Measurement) = viewModelScope.launch {
        repository.insert(measurement)
    }
}
