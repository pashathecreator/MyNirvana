package com.example.mynirvana.presentation.activities.mainActivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mynirvana.R
import com.example.mynirvana.databinding.ActivityMainBinding
import com.example.mynirvana.presentation.activities.onBoardingAcitivity.OnBoardingActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var hostFragment: Fragment
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkIsAppRanFirstTimeAndIfTrueCreateMeditationCourses()
        setTheme(R.style.Theme_MyNirvana)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.findFragmentById(binding.fragmentContainerView.id)?.let {
            hostFragment = it
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it) {
                R.id.miHome -> {
                    hostFragment.findNavController().navigate(R.id.homeFragment)
                }

                R.id.miMeditation -> {
                    hostFragment.findNavController().navigate(R.id.meditationFragment)
                }

                else -> {
                    hostFragment.findNavController().navigate(R.id.productivityFragment)
                }
            }
        }


    }

    private fun checkIsAppRanFirstTimeAndIfTrueCreateMeditationCourses() {
        if (viewModel.isAppRanFirstTime) {
            viewModel.createMeditationCourses()
            openOnBoardingActivity()
            viewModel.setFalseToAppRanFirstTime()
        }
    }

    private fun openOnBoardingActivity() {
        Intent(this, OnBoardingActivity::class.java).also {
            startActivity(it)
        }
    }


}