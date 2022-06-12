package com.skelrath.mynirvana.presentation.dialogs.meditation.startMeditationWithoutSavingDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.skelrath.mynirvana.R
import com.skelrath.mynirvana.databinding.FragmentStartMeditaitonWithoutSavingBinding
import com.skelrath.mynirvana.domain.meditations.model.meditation.Meditation


class StartMeditationWithoutSavingFragment : DialogFragment() {

    private lateinit var binding: FragmentStartMeditaitonWithoutSavingBinding
    private lateinit var meditation: Meditation
    private var functionToLaunch: ((Boolean) -> Unit?)? = null

    fun provideLambdaCallback(functionToLaunch: (userChoice: Boolean) -> Unit) {
        this.functionToLaunch = functionToLaunch
    }

    fun provideMeditation(meditation: Meditation) {
        this.meditation = meditation
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartMeditaitonWithoutSavingBinding.inflate(inflater)
        initButtons()
        return binding.root
    }

    private fun initButtons() {
        binding.startAndSaveMeditationButton.setOnClickListener {
            functionToLaunch?.let { function -> function(true) }
            this.dismiss()
        }

        binding.startAndDontSaveMeditationButton.setOnClickListener {
            functionToLaunch?.let { function -> function(false) }
            this.dismiss()
        }

        binding.crossButtonInStartAndSaveMeditationFragment.setOnClickListener {
            this.dismiss()
        }
    }
}