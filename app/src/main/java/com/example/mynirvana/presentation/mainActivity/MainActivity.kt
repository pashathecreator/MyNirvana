package com.example.mynirvana.presentation.mainActivity

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mynirvana.R
import com.example.mynirvana.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
//import eightbitlab.com.blurview.BlurView
//import eightbitlab.com.blurview.RenderScriptBlur


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MyNirvana)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val blurView = binding.blurView
//        blurBackground(blurView)
    }

//    private fun blurBackground(blurView: BlurView) {
//        val radius = 20f
//        val decorView = window.decorView
//        val windowBackground = decorView.background
//
//        blurView.setupWith(decorView.findViewById(android.R.id.content))
//            .setFrameClearDrawable(windowBackground)
//            .setBlurAlgorithm(RenderScriptBlur(this))
//            .setBlurRadius(radius)
//            .setBlurAutoUpdate(true)
//            .setHasFixedTransformationMatrix(true) // Or false if it's in a scrolling container or might be animated
//
//    }


}