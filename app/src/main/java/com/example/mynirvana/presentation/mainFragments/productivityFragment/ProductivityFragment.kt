package com.example.mynirvana.presentation.mainFragments.productivityFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mynirvana.databinding.FragmentProductivityBinding

class ProductivityFragment : Fragment() {
    private lateinit var binding: FragmentProductivityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductivityBinding.inflate(inflater)
        return binding.root
    }

}