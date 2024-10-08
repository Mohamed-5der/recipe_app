package com.example.recipeapp.presention.viweModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.Repository.FavoriteRepository
import com.example.recipeapp.data.model.Favorite
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: FavoriteRepository) : ViewModel() {

    val allFavorites: LiveData<List<Favorite>> = repository.getAllFavorites()

    fun insertFavorite(favorite: Favorite) {
        viewModelScope.launch {
            repository.insertFavorite(favorite)
        }
    }

    fun removeFavoriteByCategoryId(idCategory: String) {
        viewModelScope.launch {
            repository.removeFavoriteByCategoryId(idCategory)
        }
    }

    fun getFavoriteByCategoryId(idCategory: String): LiveData<Favorite?> {
        return repository.getFavoriteByCategoryId(idCategory)
    }
}
