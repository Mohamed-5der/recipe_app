package com.example.recipeapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.Repository.FavoriteRepository
import com.example.recipeapp.databinding.FragmentHomeBinding
import com.example.recipeapp.db.UserDatabase
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Favorite
import com.example.recipeapp.viewmodel.FavoriteViewModel
import com.example.recipeapp.viewmodel.FavoriteViewModelFactory
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val viewModel: FoodViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Initialize FavoriteViewModel
        val favoriteRepository = FavoriteRepository(UserDatabase.getDatabase(requireContext()).favoriteDao())
        val favoriteViewModelFactory = FavoriteViewModelFactory(favoriteRepository)
        favoriteViewModel = ViewModelProvider(this, favoriteViewModelFactory).get(FavoriteViewModel::class.java)

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
                binding.itemRecyclerView.adapter = AdapterFood(foodList, ::navToDetails, ::addFavorite)
            }
        }

        lifecycleScope.launch {
            viewModel.loadingProgress.collect { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }

    private fun navToDetails(id: String) {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    private fun addFavorite(addFav: Boolean, category: Category) {
        if (addFav) {
            val favorite = Favorite(
                idCategory = category.idCategory,
                strCategory = category.strCategory,
                strCategoryDescription = category.strCategoryDescription,
                strCategoryThumb = category.strCategoryThumb
            )
            lifecycleScope.launch {
                favoriteViewModel.insertFavorite(favorite)
                Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
            }
        } else {
            lifecycleScope.launch {
                favoriteViewModel.removeFavoriteByCategoryId(category.idCategory)
                Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
