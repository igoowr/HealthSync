package com.example.healthsync.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.healthsync.data.repository.MeasurementRepository
import com.example.healthsync.ui.viewmodels.HistoricoViewModel
import com.example.healthsync.ui.viewmodels.APIAlimentosViewModel
import com.example.healthsync.ui.viewmodels.MeasurementViewModel

class ViewModelFactory(private val repository: MeasurementRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MeasurementViewModel::class.java) -> MeasurementViewModel(repository) as T
            modelClass.isAssignableFrom(HistoricoViewModel::class.java) -> HistoricoViewModel(repository) as T
            modelClass.isAssignableFrom(APIAlimentosViewModel::class.java) -> APIAlimentosViewModel() as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
