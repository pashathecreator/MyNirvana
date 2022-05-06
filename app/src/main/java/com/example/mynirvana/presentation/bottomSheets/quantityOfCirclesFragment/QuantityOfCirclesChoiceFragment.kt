package com.example.mynirvana.presentation.bottomSheets.quantityOfCirclesFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentQuantityOfCirclesChoiceBinding
import com.example.mynirvana.presentation.activities.pomodoros.pomodoroCreatorActivity.PomodoroCreatorActivityCallback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class QuantityOfCirclesChoiceFragment(private val callback: PomodoroCreatorActivityCallback) :
    BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomSheetDialog)
    }

    private lateinit var binding: FragmentQuantityOfCirclesChoiceBinding

    private var numberOfCircles = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuantityOfCirclesChoiceBinding.inflate(inflater)
        initCirclesPicker()
        return binding.root
    }

    private fun initCirclesPicker() {
        with(binding) {
            circlesNumberPicker.apply {
                minValue = 0
                maxValue = 10
            }

            circlesNumberPicker.setOnValueChangedListener { _, _, p2 ->
                this@QuantityOfCirclesChoiceFragment.numberOfCircles = p2

                returnNumberOfCircles()
            }
        }


    }

    private fun returnNumberOfCircles() {
        callback.sendQuantityOfCircles(numberOfCircles)
    }
}
