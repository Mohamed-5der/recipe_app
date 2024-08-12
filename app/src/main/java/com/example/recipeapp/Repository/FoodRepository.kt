package com.example.recipeapp.Repository

import com.example.recipeapp.network.RetrofitInstance

class FoodRepository {
    val apiService =RetrofitInstance.apiService
    suspend fun getFood() =apiService.getFood()
}