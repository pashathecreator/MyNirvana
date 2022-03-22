package com.example.mynirvana.presentation.newMeditationSettingsFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mynirvana.R

class MeditationCreatorFragment : Fragment() {

    private lateinit var viewModel: MeditationCreatorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.meditation_creator_fragment, container, false)
    }



}