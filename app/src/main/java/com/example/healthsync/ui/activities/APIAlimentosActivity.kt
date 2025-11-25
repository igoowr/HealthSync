package com.example.healthsync.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthsync.databinding.ActivityApiAlimentosBinding
import com.example.healthsync.factory.ViewModelFactory
import com.example.healthsync.ui.adapters.FoodAdapter
import com.example.healthsync.ui.viewmodels.APIAlimentosViewModel

class APIAlimentosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApiAlimentosBinding
    private lateinit var viewModel: APIAlimentosViewModel
    private lateinit var adapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiAlimentosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Este ViewModel não depende do Repository, então pode ser criado sem a Factory
        viewModel = ViewModelProvider(this)[APIAlimentosViewModel::class.java]
        setupRecyclerView()
        setupObservers()
        setupListeners()
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupRecyclerView() {
        adapter = FoodAdapter()
        binding.recyclerViewFoods.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewFoods.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.foods.observe(this) { foods ->
            adapter.submitList(foods)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            if (errorMessage != null) {
                binding.textViewError.visibility = View.VISIBLE
                binding.textViewError.text = errorMessage
            } else {
                binding.textViewError.visibility = View.GONE
            }
        }
    }

    private fun setupListeners() {
        binding.buttonSearch.setOnClickListener {
            val foodName = binding.editTextFood.text.toString()
            if (foodName.isNotEmpty()) {
                viewModel.searchFood(foodName)
            } else {
                Toast.makeText(this, "Digite o nome de um alimento", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
