package com.example.healthsync.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.healthsync.HealthSyncApplication
import com.example.healthsync.R
import com.example.healthsync.data.database.entities.Measurement
import com.example.healthsync.databinding.ActivityBioimpedanciaBinding
import com.example.healthsync.factory.ViewModelFactory
import com.example.healthsync.ui.viewmodels.MeasurementViewModel
import com.example.healthsync.utils.HealthCalculations

class BioimpedanciaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBioimpedanciaBinding
    private lateinit var viewModel: MeasurementViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBioimpedanciaBinding.inflate(layoutInflater)
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
            calculateBioimpedance()
        }
        binding.buttonSave.setOnClickListener {
            saveMeasurement()
        }
    }

    private fun calculateBioimpedance() {
        val weightText = binding.editTextWeight.text.toString()
        val heightText = binding.editTextHeight.text.toString()
        val ageText = binding.editTextAge.text.toString()
        val isMale = binding.radioButtonMale.isChecked
        val isFemale = binding.radioButtonFemale.isChecked

        if (weightText.isEmpty() || heightText.isEmpty() || ageText.isEmpty() || (!isMale && !isFemale)) {
            Toast.makeText(this, getString(R.string.fill_all_fields_error), Toast.LENGTH_SHORT).show()
            return
        }

        val weight = weightText.toDoubleOrNull()
        val height = heightText.toDoubleOrNull()
        val age = ageText.toIntOrNull()

        if (weight == null || height == null || age == null || weight <= 0 || height <= 0 || age <= 0) {
            Toast.makeText(this, getString(R.string.invalid_values_error), Toast.LENGTH_SHORT).show()
            return
        }

        val imc = HealthCalculations.calculateIMC(weight, height)
        val fatPercentage = HealthCalculations.calculateFatPercentage(imc, age, isMale)
        val fatMass = HealthCalculations.calculateFatMass(weight, fatPercentage)

        binding.textViewFatPercentage.text = String.format("%.1f%%", fatPercentage)
        binding.textViewFatMass.text = String.format("%.1f kg", fatMass)
        binding.cardResult.visibility = View.VISIBLE
    }

    private fun saveMeasurement() {
        val fatPercentage = binding.textViewFatPercentage.text.toString()
        val fatMass = binding.textViewFatMass.text.toString()
        val weight = binding.editTextWeight.text.toString()
        val height = binding.editTextHeight.text.toString()
        val age = binding.editTextAge.text.toString()
        val gender = if (binding.radioButtonMale.isChecked) getString(R.string.male_label) else getString(R.string.female_label)

        val details = "Peso: ${weight}kg, Altura: ${height}m, Idade: $age, Sexo: $gender"
        val result = "Gordura: $fatPercentage, Massa Gorda: $fatMass"

        val measurement = Measurement(
            type = "Bioimpedância",
            result = result,
            details = details
        )
        viewModel.insert(measurement)
        Toast.makeText(this, getString(R.string.measurement_saved_success), Toast.LENGTH_SHORT).show()
        finish()
    }
}
