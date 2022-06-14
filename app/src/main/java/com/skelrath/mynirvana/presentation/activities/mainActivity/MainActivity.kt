package com.skelrath.mynirvana.presentation.activities.mainActivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.skelrath.mynirvana.R
import com.skelrath.mynirvana.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var hostFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MyNirvana)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.findFragmentById(binding.fragmentContainerView.id)?.let {
            hostFragment = it
        }

        hostFragment.findNavController().navigate(R.id.homeFragment)

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
}