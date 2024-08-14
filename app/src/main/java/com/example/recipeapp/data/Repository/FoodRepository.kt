package com.example.recipeapp.data.Repository

import com.example.recipeapp.data.network.RetrofitInstance

class FoodRepository {
    val apiService = RetrofitInstance.apiService
    suspend fun getFood() =apiService.getFood()
}