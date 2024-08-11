package com.example.recipeapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.Repository.UserRepository
import com.example.recipeapp.model.User
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    fun insertUser(user: User, onComplete: () -> Unit) {
        viewModelScope.launch {
            repository.insertUser(user)
            onComplete()
        }
    }

    fun login(email: String, password: String, onResult: (User?) -> Unit) {
        viewModelScope.launch {
            val user = repository.login(email, password)
            onResult(user)
        }
    }

    fun forgotPassword(email: String, newPassword: String, onComplete: () -> Unit) {
        viewModelScope.launch {
            repository.updatePassword(email, newPassword)
            onComplete()
        }
    }

    fun getUserByEmail(email: String, onResult: (User?) -> Unit) {
        viewModelScope.launch {
            val user = repository.getUserByEmail(email)
            onResult(user)
        }
    }
}
