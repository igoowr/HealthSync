package com.example.healthsync.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.healthsync.data.database.entities.Measurement

@Dao
interface MeasurementDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(measurement: Measurement)

    @Query("SELECT * FROM measurements ORDER BY id DESC")
    fun getAllMeasurements(): LiveData<List<Measurement>>

    @Query("DELETE FROM measurements WHERE id = :id")
    suspend fun deleteById(id: Long) // Sem ': Unit', sem nada. Apenas 'suspend fun'.

    @Query("DELETE FROM measurements")
    suspend fun deleteAll()
}