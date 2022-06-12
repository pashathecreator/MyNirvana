package com.skelrath.mynirvana.presentation.dialogs.meditation.saveMeditationAndStartDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.skelrath.mynirvana.R
import com.skelrath.mynirvana.databinding.FragmentSaveMeditationAndStartBinding


class SaveMeditationAndStartFragment : DialogFragment() {

    private lateinit var binding: FragmentSaveMeditationAndStartBinding
    private var functionToLaunch: ((Boolean) -> Unit?)? = null

    fun provideLambdaCallback(functionToLaunch: (userChoice: Boolean) -> Unit) {
        this.functionToLaunch = functionToLaunch
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSaveMeditationAndStartBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backToMainScreenButton.setOnClickListener {
            functionToLaunch?.let { function -> function(false) }
            this.dismiss()
        }

        binding.startSavedMeditation.setOnClickListener {
            functionToLaunch?.let { function -> function(true) }
            this.dismiss()
        }
    }
}