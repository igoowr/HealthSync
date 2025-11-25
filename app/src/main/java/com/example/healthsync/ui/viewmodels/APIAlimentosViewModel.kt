package com.example.healthsync.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthsync.data.remote.api.EdamamApiService
import com.example.healthsync.data.remote.models.Food
import com.example.healthsync.utils.Constants
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIAlimentosViewModel : ViewModel() {

    private val apiService = Retrofit.Builder()
        .baseUrl("https://api.edamam.com/api/food-database/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(EdamamApiService::class.java)

    private val _foods = MutableLiveData<List<Food>>()
    val foods: LiveData<List<Food>> = _foods

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun searchFood(ingredient: String) {
        if (Constants.EDAMAM_APP_ID == "SUA_APP_ID_AQUI") {
            _errorMessage.postValue("Configure suas chaves da API EDAMAM em Constants.kt")
            return
        }

        viewModelScope.launch {
            _isLoading.postValue(true)
            _errorMessage.postValue(null)
            try {
                val response = apiService.searchFood(ingredient)
                // Combina resultados parsed e hints
                val allFoods = mutableListOf<Food>()
                allFoods.addAll(response.parsed.map { it.food })
                allFoods.addAll(response.hints.map { it.food })
                
                _foods.postValue(allFoods)
            } catch (e: Exception) {
                _errorMessage.postValue("Erro ao buscar dados: ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}
