package com.example.recipeapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class SplashFragment : Fragment() {
  lateinit var sh:SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         val view =  inflater.inflate(R.layout.fragment_splash, container, false)
        val btnStart = view.findViewById<Button>(R.id.get_start)
         sh = requireContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)

        btnStart.setOnClickListener {
            if (sh.getString("email", null) != null){
                findNavController().navigate(R.id.action_splashFragment_to_mainActivity)
            }else{
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }
        }
    return view

    }
}