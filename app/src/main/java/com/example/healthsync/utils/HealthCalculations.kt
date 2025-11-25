package com.example.healthsync.utils

import kotlin.math.pow

object HealthCalculations {

    fun calculateIMC(weight: Double, height: Double): Double {
        return weight / height.pow(2)
    }

    fun getIMCClassification(imc: Double): String {
        return when {
            imc < 18.5 -> "Abaixo do peso"
            imc < 25 -> "Peso normal"
            imc < 30 -> "Sobrepeso"
            imc < 35 -> "Obesidade Grau I"
            imc < 40 -> "Obesidade Grau II"
            else -> "Obesidade Grau III (Morbida)"
        }
    }

    fun calculateWaistHipRatio(waist: Double, hip: Double): Double {
        if (hip == 0.0) return 0.0
        return waist / hip
    }

    fun getRatioClassification(ratio: Double, isMale: Boolean): String {
        return if (isMale) {
            when {
                ratio < 0.90 -> "Baixo risco"
                ratio < 1.00 -> "Risco moderado"
                else -> "Alto risco"
            }
        } else {
            when {
                ratio < 0.80 -> "Baixo risco"
                ratio < 0.85 -> "Risco moderado"
                else -> "Alto risco"
            }
        }
    }

    // Fórmula de Deurenberg (simulada para bioimpedância)
    fun calculateFatPercentage(imc: Double, age: Int, isMale: Boolean): Double {
        val sexValue = if (isMale) 1 else 0
        return (1.20 * imc) + (0.23 * age) - (10.8 * sexValue) - 5.4
    }

    fun calculateFatMass(weight: Double, fatPercentage: Double): Double {
        return weight * (fatPercentage / 100)
    }

    fun calculateLeanMass(weight: Double, fatPercentage: Double): Double {
        return weight - calculateFatMass(weight, fatPercentage)
    }
}