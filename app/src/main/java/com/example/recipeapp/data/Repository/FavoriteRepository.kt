package com.example.recipeapp.data.Repository

import androidx.lifecycle.LiveData
import com.example.recipeapp.data.db.FavoriteDao
import com.example.recipeapp.data.model.Favorite

class FavoriteRepository(private val favoriteDao: FavoriteDao) {

    fun getAllFavorites(): LiveData<List<Favorite>> {
        return favoriteDao.getAllFavorites()
    }

    fun getFavoriteByCategoryId(idCategory: String): LiveData<Favorite?> {
        return favoriteDao.getFavoriteByCategoryId(idCategory)
    }

    suspend fun insertFavorite(favorite: Favorite) {
        favoriteDao.insertFavorite(favorite)
    }

    suspend fun removeFavoriteByCategoryId(idCategory: String) {
        favoriteDao.removeFavoriteByCategoryId(idCategory)
    }
}
