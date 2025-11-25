package com.example.healthsync.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.healthsync.HealthSyncApplication
import com.example.healthsync.R
import com.example.healthsync.data.database.entities.Measurement
import com.example.healthsync.databinding.ActivityImcBinding
import com.example.healthsync.factory.ViewModelFactory
import com.example.healthsync.ui.viewmodels.MeasurementViewModel
import com.example.healthsync.utils.HealthCalculations

class IMCActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImcBinding
    private lateinit var viewModel: MeasurementViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = (application as HealthSyncApplication).repository
        val factory = ViewModelFactory(repository)
        // Usando MeasurementViewModel padronizado
        viewModel = ViewModelProvider(this, factory)[MeasurementViewModel::class.java]

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupListeners()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupListeners() {
        binding.buttonCalculate.setOnClickListener {
            calculateIMC()
        }
        binding.buttonSave.setOnClickListener {
            saveMeasurement()
        }
    }

    private fun calculateIMC() {
        val weightText = binding.editTextWeight.text.toString()
        val heightText = binding.editTextHeight.text.toString()

        if (weightText.isEmpty() || heightText.isEmpty()) {
            Toast.makeText(this, getString(R.string.fill_all_fields_error), Toast.LENGTH_SHORT).show()
            return
        }

        val weight = weightText.toDoubleOrNull()
        val height = heightText.toDoubleOrNull()

        if (weight == null || height == null || weight <= 0 || height <= 0) {
            Toast.makeText(this, getString(R.string.invalid_values_error), Toast.LENGTH_SHORT).show()
            return
        }

        val imc = HealthCalculations.calculateIMC(weight, height)
        val classification = HealthCalculations.getIMCClassification(imc)

        binding.textViewIMCValue.text = String.format("%.2f", imc)
        binding.textViewIMCClassification.text = classification
        binding.cardResult.visibility = android.view.View.VISIBLE
    }

    private fun saveMeasurement() {
        val result = binding.textViewIMCValue.text.toString()
        val classification = binding.textViewIMCClassification.text.toString()
        val weight = binding.editTextWeight.text.toString()
        val height = binding.editTextHeight.text.toString()
        val details = "Peso: ${weight}kg, Altura: ${height}m"

        val measurement = Measurement(
            type = "IMC",
            result = "$result - $classification",
            details = details
        )
        viewModel.insert(measurement)
        Toast.makeText(this, getString(R.string.measurement_saved_success), Toast.LENGTH_SHORT).show()
        finish()
    }
}
