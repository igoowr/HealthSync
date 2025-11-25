package com.example.healthsync.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "measurements")
data class Measurement(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: String, // "IMC", "Perimetria", etc.
    val result: String, // "23.5 - Normal"
    val details: String // "Peso: 70kg, Altura: 1.73m"
)