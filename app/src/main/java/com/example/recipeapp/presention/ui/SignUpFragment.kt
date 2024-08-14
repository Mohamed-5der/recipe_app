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
import com.example.recipeapp.databinding.FragmentSignUpBinding
import com.example.recipeapp.data.db.UserDatabase
import com.example.recipeapp.data.model.User

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var sh: SharedPreferences

    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory(UserRepository(UserDatabase.getDatabase(requireContext()).userDao()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        binding.txtSignin.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
        sh = requireContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignup.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            } else {
                val user = User(name = name, email = email, password = password)
                userViewModel.insertUser(user) {
                    // Save email and name to SharedPreferences
                    with(sh.edit()) {
                        putString("email", email)
                        putString("name", name)
                        apply() // Use apply() for async saving
                    }

                    Toast.makeText(context, "User registered successfully", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(
                        R.id.action_signUpFragment_to_mainActivity,
                        null,
                        navOptions {
                            popUpTo(R.id.signUpFragment) { inclusive = true }
                        })

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
