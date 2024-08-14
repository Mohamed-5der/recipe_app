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
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.recipeapp.R
import com.example.recipeapp.presention.viweModel.UserViewModel
import com.example.recipeapp.Di.UserViewModelFactory
import com.example.recipeapp.data.Repository.UserRepository
import com.example.recipeapp.databinding.FragmentLoginBinding
import com.example.recipeapp.data.db.UserDatabase

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    lateinit var sh : SharedPreferences
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory(UserRepository(UserDatabase.getDatabase(requireContext()).userDao()))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        sh = requireContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            userViewModel.login(email, password) { user ->
                if (user != null) {
                    Toast.makeText(context, "User Login successfully", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(
                        R.id.action_loginFragment_to_mainActivity,
                        null,
                        navOptions {
                            popUpTo(R.id.loginFragment) { inclusive = true }
                        })
                } else {
                    Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.signup.setOnClickListener {
            findNavController().navigate(
                R.id.action_loginFragment_to_signUpFragment,
                null,
                navOptions {
                    popUpTo(R.id.loginFragment) { inclusive = true }
                })
        }
        binding.forgotPassword.setOnClickListener {
            findNavController().navigate(
                R.id.action_loginFragment_to_forgotPasswordFragment,
                null,
                navOptions {
                    popUpTo(R.id.loginFragment) { inclusive = true }
                })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}