package com.example.healthsync.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.healthsync.HealthSyncApplication
import com.example.healthsync.R
import com.example.healthsync.data.database.entities.Measurement
import com.example.healthsync.databinding.ActivityPerimetriaBinding
import com.example.healthsync.factory.ViewModelFactory
import com.example.healthsync.ui.viewmodels.MeasurementViewModel
import com.example.healthsync.utils.HealthCalculations

class PerimetriaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPerimetriaBinding
    private lateinit var viewModel: MeasurementViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerimetriaBinding.inflate(layoutInflater)
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
            calculateRatio()
        }
        binding.buttonSave.setOnClickListener {
            saveMeasurement()
        }
    }

    private fun calculateRatio() {
        val waistText = binding.editTextWaist.text.toString()
        val hipText = binding.editTextHip.text.toString()
        val isMale = binding.radioButtonMale.isChecked
        val isFemale = binding.radioButtonFemale.isChecked

        if (waistText.isEmpty() || hipText.isEmpty() || (!isMale && !isFemale)) {
            Toast.makeText(this, getString(R.string.fill_all_fields_error), Toast.LENGTH_SHORT).show()
            return
        }

        val waist = waistText.toDoubleOrNull()
        val hip = hipText.toDoubleOrNull()

        if (waist == null || hip == null || waist <= 0 || hip <= 0) {
            Toast.makeText(this, getString(R.string.invalid_values_error), Toast.LENGTH_SHORT).show()
            return
        }

        val ratio = HealthCalculations.calculateWaistHipRatio(waist, hip)
        val classification = HealthCalculations.getRatioClassification(ratio, isMale)

        binding.textViewRatioValue.text = String.format("%.2f", ratio)
        binding.textViewRatioClassification.text = classification
        binding.cardResult.visibility = View.VISIBLE
    }

    private fun saveMeasurement() {
        val result = binding.textViewRatioValue.text.toString()
        val classification = binding.textViewRatioClassification.text.toString()
        val waist = binding.editTextWaist.text.toString()
        val hip = binding.editTextHip.text.toString()
        val gender = if (binding.radioButtonMale.isChecked) getString(R.string.male_label) else getString(R.string.female_label)
        val details = "Cintura: ${waist}cm, Quadril: ${hip}cm, Sexo: $gender"

        val measurement = Measurement(
            type = "Perimetria",
            result = "$result - $classification",
            details = details
        )
        viewModel.insert(measurement)
        Toast.makeText(this, getString(R.string.measurement_saved_success), Toast.LENGTH_SHORT).show()
        finish()
    }
}
