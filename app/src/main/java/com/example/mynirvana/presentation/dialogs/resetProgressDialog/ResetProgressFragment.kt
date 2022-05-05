package com.example.mynirvana.presentation.dialogs.resetProgressDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentResetProgressBinding
import com.example.mynirvana.presentation.activities.meditations.meditationCoursesActivity.ResetProgressCallback

class ResetProgressFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomSheetDialog)
    }

    private lateinit var binding: FragmentResetProgressBinding
    private lateinit var callback: ResetProgressCallback

    fun providesCallback(callback: ResetProgressCallback) {
        this.callback = callback
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResetProgressBinding.inflate(inflater)
        initButtons()
        return binding.root
    }

    private fun initButtons() {
        with(binding) {
            crossButtonInResetProgressFragment.setOnClickListener {
                this@ResetProgressFragment.dismiss()
            }

            resetProgressButtonInDialog.setOnClickListener {
                callback.resetProgress(true)
                this@ResetProgressFragment.dismiss()
            }
        }
    }
}