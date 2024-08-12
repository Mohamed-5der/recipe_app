package com.example.recipeapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private val viewModel: FoodViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupUI()
        observeViewModel()

        return binding.root
    }

    private fun setupUI() {
        binding.etSearch.addTextChangedListener {
            val query = binding.etSearch.text.toString()
            val filteredList = viewModel.foodList.value.filter { category ->
                category.strCategory.contains(query, ignoreCase = true)
            }
            (binding.itemRecyclerView.adapter as? AdapterFood)?.updateData(filteredList)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.foodList.collect { foodList ->
                binding.itemRecyclerView.adapter = AdapterFood(foodList, ::navToDetails)
            }
        }

        lifecycleScope.launch {
            viewModel.loadingProgress.collect { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }

    private fun navToDetails(id: String) {
        val intent =Intent(context,DetailsActivity::class.java)
        intent.putExtra("id",id)
        startActivity(intent)
    }
    private fun addFavorite(id: String) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}