package com.example.recipeapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import com.example.recipeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, HomeFragment())
                .commit()
        }
        binding.navigationHome.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, HomeFragment())
                        .commit()
                    true
                }
                R.id.Favourite -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, FavoriteFragment())
                        .commit()
                    true
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, ProfileFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }

        binding.navigationHome.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    // Handle reselection of Home if needed
                }
                R.id.Favourite -> {
                    // Handle reselection of Favorite if needed
                }
                R.id.profile -> {
                    // Handle reselection of Profile if needed
                }
                else -> { }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
