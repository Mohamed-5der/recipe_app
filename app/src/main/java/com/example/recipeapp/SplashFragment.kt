package com.example.recipeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class SplashFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         val view =  inflater.inflate(R.layout.fragment_splash, container, false)
        val btnStart = view.findViewById<Button>(R.id.get_start)
        btnStart.setOnClickListener {
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }
    return view

    }
}