package com.example.healthsync.data.repository

import androidx.lifecycle.LiveData
import com.example.healthsync.data.database.dao.MeasurementDao
import com.example.healthsync.data.database.entities.Measurement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MeasurementRepository(private val measurementDao: MeasurementDao) {

    val allMeasurements: LiveData<List<Measurement>> = measurementDao.getAllMeasurements()

    suspend fun insert(measurement: Measurement) = withContext(Dispatchers.IO) {
        measurementDao.insert(measurement)
    }

    suspend fun deleteById(id: Long) = withContext(Dispatchers.IO) {
        measurementDao.deleteById(id)
    }

    suspend fun deleteAll() = withContext(Dispatchers.IO) {
        measurementDao.deleteAll()
    }
}