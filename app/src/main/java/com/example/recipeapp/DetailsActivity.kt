package com.example.recipeapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.recipeapp.databinding.ActivityDetailsBinding
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class DetailsActivity : AppCompatActivity() {
    private var _binding: ActivityDetailsBinding? = null
    private val binding get() = _binding!!
    private val foodViewModel : FoodViewModel by viewModels()
    var strCategory: String? = null
    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityDetailsBinding.inflate(layoutInflater)
        val getId = intent.getStringExtra("id")
        binding.progressBar.visibility = android.view.View.VISIBLE
        lifecycleScope.launch {
            foodViewModel.foodList.filter { it.isNotEmpty() }.collect { foodList ->
                val item = foodList.find { it.idCategory == getId }
                item?.let {
                    binding.recipeName.text = it.strCategory
                    strCategory=it.strCategory
                    binding.description.text = it.strCategoryDescription
                    binding.foodImage.load(it.strCategoryThumb)
                    binding.progressBar.visibility = android.view.View.INVISIBLE
                } ?: run {
                    binding.recipeName.text = "Item not found"
                    binding.description.text = ""
                    binding.foodImage.setImageDrawable(null)
                    binding.progressBar.visibility = android.view.View.INVISIBLE

                }
            }
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=recipe $strCategory"))
                startActivity(intent)

        }




        setContentView(binding.root)

    }
}