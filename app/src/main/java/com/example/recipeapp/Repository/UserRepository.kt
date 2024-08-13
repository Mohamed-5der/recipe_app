package com.example.recipeapp.Repository

import com.example.recipeapp.db.UserDao
import com.example.recipeapp.model.User

class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    suspend fun login(email: String, password: String): User? {
        return userDao.login(email, password)
    }

    suspend fun updatePassword(email: String, password: String) {
        userDao.updatePassword(email, password)
    }


}
