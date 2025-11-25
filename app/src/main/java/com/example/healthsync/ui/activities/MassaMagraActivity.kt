package com.example.healthsync.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.healthsync.HealthSyncApplication
import com.example.healthsync.R
import com.example.healthsync.data.database.entities.Measurement
import com.example.healthsync.databinding.ActivityMassaMagraBinding
import com.example.healthsync.factory.ViewModelFactory
import com.example.healthsync.ui.viewmodels.MeasurementViewModel
import com.example.healthsync.utils.HealthCalculations

class MassaMagraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMassaMagraBinding
    private lateinit var viewModel: MeasurementViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMassaMagraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = (application as HealthSyncApplication).repository
        val factory = ViewModelFactory(repository)
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
            calculateLeanMass()
        }
        binding.buttonSave.setOnClickListener {
            saveMeasurement()
        }
    }

    private fun calculateLeanMass() {
        val weightText = binding.editTextWeight.text.toString()
        val fatPercentageText = binding.editTextFatPercentage.text.toString()

        if (weightText.isEmpty() || fatPercentageText.isEmpty()) {
            Toast.makeText(this, getString(R.string.fill_all_fields_error), Toast.LENGTH_SHORT).show()
            return
        }

        val weight = weightText.toDoubleOrNull()
        val fatPercentage = fatPercentageText.toDoubleOrNull()

        if (weight == null || fatPercentage == null || weight <= 0 || fatPercentage < 0 || fatPercentage > 100) {
            Toast.makeText(this, getString(R.string.invalid_values_error), Toast.LENGTH_SHORT).show()
            return
        }

        val leanMass = HealthCalculations.calculateLeanMass(weight, fatPercentage)
        val leanMassPercentage = (leanMass / weight) * 100

        binding.textViewLeanMassValue.text = String.format("%.1f kg", leanMass)
        binding.textViewLeanMassPercentage.text = String.format("%.1f%% do peso corporal", leanMassPercentage)
        binding.cardResult.visibility = View.VISIBLE
    }

    private fun saveMeasurement() {
        val leanMass = binding.textViewLeanMassValue.text.toString()
        val leanMassPercentage = binding.textViewLeanMassPercentage.text.toString()
        val weight = binding.editTextWeight.text.toString()
        val fatPercentage = binding.editTextFatPercentage.text.toString()

        val details = "Peso: ${weight}kg, % Gordura: ${fatPercentage}%"
        val result = "Massa Magra: $leanMass ($leanMassPercentage)"

        val measurement = Measurement(
            type = "Massa Magra",
            result = result,
            details = details
        )
        viewModel.insert(measurement)
        Toast.makeText(this, getString(R.string.measurement_saved_success), Toast.LENGTH_SHORT).show()
        finish()
    }
}
