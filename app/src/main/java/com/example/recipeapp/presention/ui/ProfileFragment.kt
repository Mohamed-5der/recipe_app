package com.example.recipeapp.presention.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.recipeapp.presention.viweModel.UserViewModel
import com.example.recipeapp.Di.UserViewModelFactory
import com.example.recipeapp.data.Repository.UserRepository
import com.example.recipeapp.databinding.FragmentProfileBinding
import com.example.recipeapp.data.db.UserDatabase

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var sh: SharedPreferences
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory(UserRepository(UserDatabase.getDatabase(requireContext()).userDao()))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        sh = requireContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        binding.etName.setText(sh.getString("name", ""))
        binding.etEmail.setText(sh.getString("email", ""))
        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val oldPassword = binding.etPass.text.toString()
            val newPassword = binding.etNewPassword.text.toString()
                if (newPassword.isNotEmpty()) {
                    userViewModel.updateProfile(email, oldPassword, newPassword, name) {
                        Toast.makeText(requireContext(), "Update Profile successfully", Toast.LENGTH_SHORT).show()
                        sh.edit().putString("email", email).apply()
                        sh.edit().putString("name", name).apply()
                    }
                } else {
                    Toast.makeText(requireContext(), "Password Invalid", Toast.LENGTH_SHORT).show()
                }

        }


        return binding.root
    }

}