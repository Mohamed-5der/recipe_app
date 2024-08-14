package com.example.recipeapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.recipeapp.Repository.UserRepository
import com.example.recipeapp.databinding.ActivityMainBinding
import com.example.recipeapp.db.UserDatabase


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var navController: NavController
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)

        enableEdgeToEdge()

        val userRepository = UserRepository(UserDatabase.getDatabase(this).userDao())
        val userViewModelFactory = UserViewModelFactory(userRepository)
        userViewModel = ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, HomeFragment())
                .commit()
        }

        setSupportActionBar(binding.toolbar)
        binding.navigationHome.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    navigateToFragment(HomeFragment())
                    true
                }
                R.id.Favourite -> {
                    navigateToFragment(FavoriteFragment())
                    true
                }
                R.id.profile -> {
                    navigateToFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }

        binding.navigationHome.setOnItemReselectedListener { item ->
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.splash_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sign_out -> {
                handleLogout()
                true
            }
            R.id.contact -> {
                navigateToFragment(ContactUsFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun handleLogout() {
        val email = sharedPreferences.getString("email", null)
        if (email != null) {
            userViewModel.deleteUser(email)
            with(sharedPreferences.edit()) {
                clear()
                apply()
            }
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
