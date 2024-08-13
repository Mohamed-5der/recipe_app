package com.example.recipeapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.recipeapp.model.Favorite

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): LiveData<List<Favorite>>

    @Query("SELECT * FROM favorites WHERE idCategory = :idCategory")
    fun getFavoriteByCategoryId(idCategory: String): LiveData<Favorite?>

    @Insert
    suspend fun insertFavorite(favorite: Favorite)

    @Query("DELETE FROM favorites WHERE idCategory = :idCategory")
    suspend fun removeFavoriteByCategoryId(idCategory: String)
}
