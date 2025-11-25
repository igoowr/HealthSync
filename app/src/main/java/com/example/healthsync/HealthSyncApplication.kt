package com.example.healthsync

import android.app.Application
import com.example.healthsync.data.database.AppDatabase
import com.example.healthsync.data.repository.MeasurementRepository

class HealthSyncApplication : Application() {
    // Usando 'lazy' para garantir que o banco de dados e o repositório sejam criados apenas uma vez
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { MeasurementRepository(database.measurementDao()) }
}