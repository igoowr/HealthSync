package com.example.healthsync.data.remote.api

import com.example.healthsync.data.remote.models.FoodResponse
import com.example.healthsync.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface EdamamApiService {
    @GET("parser")
    suspend fun searchFood(
        @Query("ingr") ingredient: String,
        @Query("app_id") appId: String = Constants.EDAMAM_APP_ID,
        @Query("app_key") appKey: String = Constants.EDAMAM_APP_KEY
    ): FoodResponse
}