package com.example.recipeapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.recipeapp.model.User

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): User?

    @Query("UPDATE users SET password = :password WHERE email = :email")
    suspend fun updatePassword(email: String, password: String)

    @Query("UPDATE users SET password = :newPassword, name = :name WHERE email = :email AND password = :oldPassword")
    suspend fun updateUserDetails(email: String, oldPassword: String, newPassword: String, name: String): Int
    @Query("DELETE FROM users WHERE email = :email")
    suspend fun deleteUserByEmail(email: String)

}
