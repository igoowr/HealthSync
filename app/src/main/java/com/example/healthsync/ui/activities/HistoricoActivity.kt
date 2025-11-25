package com.example.healthsync.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthsync.HealthSyncApplication
import com.example.healthsync.data.database.entities.Measurement
import com.example.healthsync.databinding.ActivityHistoricoBinding
import com.example.healthsync.factory.ViewModelFactory
import com.example.healthsync.ui.adapters.HistoricoAdapter
import com.example.healthsync.ui.viewmodels.HistoricoViewModel

class HistoricoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoricoBinding
    private lateinit var viewModel: HistoricoViewModel
    private lateinit var adapter: HistoricoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoricoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = (application as HealthSyncApplication).repository
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[HistoricoViewModel::class.java]
        
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupRecyclerView()
        setupObservers()
        setupListeners()
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupRecyclerView() {
        // Passando a função de callback para o adapter
        adapter = HistoricoAdapter { measurement ->
            showDeleteConfirmationDialog(measurement)
        }
        binding.recyclerViewHistorico.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewHistorico.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.allMeasurements.observe(this) { measurements ->
            if (measurements.isEmpty()) {
                binding.textViewEmpty.visibility = View.VISIBLE
                binding.recyclerViewHistorico.visibility = View.GONE
            } else {
                binding.textViewEmpty.visibility = View.GONE
                binding.recyclerViewHistorico.visibility = View.VISIBLE
                adapter.submitList(measurements)
            }
        }
    }

    private fun setupListeners() {
        binding.buttonClearAll.setOnClickListener {
            showClearAllConfirmationDialog()
        }
    }
    
    private fun showDeleteConfirmationDialog(measurement: Measurement) {
        AlertDialog.Builder(this)
            .setTitle("Excluir Medição")
            .setMessage("Tem certeza que deseja excluir esta medição?")
            .setPositiveButton("Sim") { _, _ ->
                viewModel.delete(measurement)
                Toast.makeText(this, "Medição excluída", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Não", null)
            .show()
    }

    private fun showClearAllConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Limpar Histórico")
            .setMessage("Tem certeza que deseja apagar todas as medições?")
            .setPositiveButton("Sim") { _, _ ->
                viewModel.deleteAll()
                Toast.makeText(this, "Histórico limpo", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Não", null)
            .show()
    }
}
