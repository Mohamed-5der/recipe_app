package com.example.recipeapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.Repository.FoodRepository
import com.example.recipeapp.model.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FoodViewModel : ViewModel() {

    private val _foodList = MutableStateFlow<List<Category>>(emptyList())
    val foodList: StateFlow<List<Category>> get() = _foodList

    private val _loadingProgress = MutableStateFlow(false)
    val loadingProgress: StateFlow<Boolean> get() = _loadingProgress

    private val foodRepository = FoodRepository()

    init {
        fetchFood()
    }

    private fun fetchFood() {
        viewModelScope.launch {
            _loadingProgress.value = true
            try {
                val response = foodRepository.getFood()
                if (response.isSuccessful) {
                    _foodList.value = response.body()?.categories ?: emptyList()
                } else {
                    Log.e("moh","Error load data")
                }
            } catch (e: Exception) {
                Log.e("moh",e.message.toString())
            } finally {
                _loadingProgress.value = false
            }
        }
    }

}
