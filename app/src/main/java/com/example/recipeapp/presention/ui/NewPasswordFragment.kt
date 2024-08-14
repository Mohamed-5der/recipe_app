package com.example.recipeapp.presention.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.recipeapp.R
import com.example.recipeapp.presention.viweModel.UserViewModel
import com.example.recipeapp.Di.UserViewModelFactory
import com.example.recipeapp.data.Repository.UserRepository
import com.example.recipeapp.databinding.FragmentNewPasswordBinding
import com.example.recipeapp.data.db.UserDatabase

class NewPasswordFragment : Fragment() {

    private var _binding: FragmentNewPasswordBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by viewModels {
        UserViewModelFactory(UserRepository(UserDatabase.getDatabase(requireContext()).userDao()))
    }
    private val args: NewPasswordFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener {
            val newPassword = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            if (newPassword == confirmPassword) {
                if (newPassword.isNotEmpty()) {
                    viewModel.updatePassword(args.email, newPassword){
                        Toast.makeText(requireContext(), "Password reset successfully", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_newPasswordFragment_to_mainActivity)
                    }
                } else {
                    Toast.makeText(requireContext(), "Please enter a new password", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}