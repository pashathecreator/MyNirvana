package com.example.mynirvana.presentation.bottomSheets.timeChoiceFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentTimeChoiceBinding
import com.example.mynirvana.presentation.activities.meditations.meditationCreatorActivity.MeditationCreatorActivityCallback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TimeChoiceFragment(
    private val meditationCreatorActivityCallback: MeditationCreatorActivityCallback
) :
    BottomSheetDialogFragment() {

    private lateinit var binding: FragmentTimeChoiceBinding

    private var numberOfSeconds = 0
    private var numberOfMinutes = 0
    private var numberOfHours = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTimeChoiceBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            hoursNumberPicker.apply {
                minValue = 0
                maxValue = 3
            }

            minutesNumberPicker.apply {
                minValue = 0
                maxValue = 59
            }

            secondsNumberPicker.apply {
                minValue = 0
                maxValue = 59
            }
        }

        binding.hoursNumberPicker.setOnValueChangedListener { _, _, p2 ->
            this.numberOfHours = p2

            calculatePickedTime()
        }

        binding.minutesNumberPicker.setOnValueChangedListener { _, _, p2 ->
            this.numberOfMinutes = p2

            calculatePickedTime()
        }

        binding.secondsNumberPicker.setOnValueChangedListener { _, _, p2 ->
            this.numberOfSeconds = p2

            calculatePickedTime()
        }
    }

    private fun calculatePickedTime() {
        val minutesToReturn = numberOfMinutes + numberOfHours * 60

        if (minutesToReturn == 0 && numberOfSeconds == 0) {
            meditationCreatorActivityCallback.sendPickedTime(5, 0)
        } else {
            meditationCreatorActivityCallback.sendPickedTime(minutesToReturn, numberOfSeconds)
        }
    }
}