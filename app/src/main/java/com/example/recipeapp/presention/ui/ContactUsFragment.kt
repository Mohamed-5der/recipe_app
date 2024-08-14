package com.example.recipeapp.presention.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentContactUsBinding

class ContactUsFragment : Fragment() {
    val binding: FragmentContactUsBinding? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact_us, container, false)
    }
}