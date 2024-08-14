package com.example.recipeapp.data.Repository

import com.example.recipeapp.data.db.UserDao
import com.example.recipeapp.data.model.User

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
    suspend fun updateUserDetails(email: String, oldPassword: String, newPassword: String, name: String)=userDao.updateUserDetails(email, oldPassword, newPassword, name)
    suspend fun deleteUser(email: String) {
        userDao.deleteUserByEmail(email)
    }

}
