package com.example.mynirvana.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mynirvana.R
import com.example.mynirvana.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}