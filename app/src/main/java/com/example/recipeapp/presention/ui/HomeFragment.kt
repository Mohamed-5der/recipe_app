package com.example.recipeapp.presention.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.presention.ui.adapter.AdapterFood
import com.example.recipeapp.presention.viweModel.FoodViewModel
import com.example.recipeapp.presention.viweModel.UserViewModel
import com.example.recipeapp.Di.UserViewModelFactory
import com.example.recipeapp.data.Repository.FavoriteRepository
import com.example.recipeapp.data.Repository.UserRepository
import com.example.recipeapp.databinding.FragmentHomeBinding
import com.example.recipeapp.data.db.UserDatabase
import com.example.recipeapp.data.model.Category
import com.example.recipeapp.data.model.Favorite
import com.example.recipeapp.presention.viweModel.FavoriteViewModel
import com.example.recipeapp.Di.FavoriteViewModelFactory

import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val viewModel: FoodViewModel by viewModels()
    private lateinit var userViewModel: UserViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        val userRepository = UserRepository(UserDatabase.getDatabase(requireContext()).userDao())
        val userViewModelFactory = UserViewModelFactory(userRepository)
        userViewModel = ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        sharedPreferences = requireContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)

        val favoriteRepository = FavoriteRepository(UserDatabase.getDatabase(requireContext()).favoriteDao())
        val favoriteViewModelFactory = FavoriteViewModelFactory(favoriteRepository)
        favoriteViewModel = ViewModelProvider(this, favoriteViewModelFactory).get(FavoriteViewModel::class.java)



        setupUI()
        observeViewModel()

        return binding.root
    }


    private fun setupUI() {
        binding.itemRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.itemRecyclerView.adapter = AdapterFood(emptyList(), ::navToDetails, ::addFavorite)

        binding.etSearch.addTextChangedListener {
            val query = it?.toString() ?: ""
            val filteredList = viewModel.foodList.value.filter { category ->
                category.strCategory.contains(query, ignoreCase = true)
            }
            (binding.itemRecyclerView.adapter as? AdapterFood)?.updateData(filteredList)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.foodList.collect { foodList ->
                (binding.itemRecyclerView.adapter as? AdapterFood)?.updateData(foodList)
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
