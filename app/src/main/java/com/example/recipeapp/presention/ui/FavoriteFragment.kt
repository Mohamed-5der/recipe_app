package com.example.recipeapp.presention.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.presention.ui.adapter.AdapterFavorite
import com.example.recipeapp.data.Repository.FavoriteRepository
import com.example.recipeapp.databinding.FragmentFavoriteBinding
import com.example.recipeapp.data.db.UserDatabase
import com.example.recipeapp.data.model.Favorite
import com.example.recipeapp.presention.viweModel.FavoriteViewModel
import com.example.recipeapp.Di.FavoriteViewModelFactory
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favoriteAdapter: AdapterFavorite

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        val favoriteRepository = FavoriteRepository(UserDatabase.getDatabase(requireContext()).favoriteDao())
        val favoriteViewModelFactory = FavoriteViewModelFactory(favoriteRepository)
        favoriteViewModel = ViewModelProvider(this, favoriteViewModelFactory).get(FavoriteViewModel::class.java)

        favoriteAdapter = AdapterFavorite(emptyList(), ::navToDetails, ::addFavorite)
        binding.itemRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.itemRecyclerView.adapter = favoriteAdapter

        binding.etSearch.addTextChangedListener {
            val query = binding.etSearch.text.toString()

                val filteredList = favoriteViewModel.allFavorites.value?.filter { category ->
                    category.strCategory.contains(query, ignoreCase = true)
                }
                filteredList?.let { it1 ->
                    (binding.itemRecyclerView.adapter as? AdapterFavorite)?.updateData(
                        it1)
                }


        }
        observeViewModel()

        return binding.root
    }

    private fun observeViewModel() {
        favoriteViewModel.allFavorites.observe(viewLifecycleOwner) { favorites ->
            favoriteAdapter.updateData(favorites)
        }
    }

    private fun navToDetails(id: String) {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    private fun addFavorite(addFav: Boolean, favorite: Favorite) {
        if (addFav) {
            lifecycleScope.launch {
                favoriteViewModel.insertFavorite(favorite)
                Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
            }
        } else {
            lifecycleScope.launch {
                favoriteViewModel.removeFavoriteByCategoryId(favorite.idCategory)
                Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
