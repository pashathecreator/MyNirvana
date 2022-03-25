package com.example.mynirvana.presentation.timeChoiceFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentTimeChoiceBinding
import com.example.mynirvana.presentation.getDataFromBottomSheet.MeditationCreatorActivityCallback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.math.round

class TimeChoiceFragment(private val meditationCreatorActivityCallback: MeditationCreatorActivityCallback) :
    BottomSheetDialogFragment() {

    private lateinit var binding: FragmentTimeChoiceBinding

    private var numberOfSeconds = 0
    private var numberOfMinutes = 0
    private var numberOfHours = 0

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

        meditationCreatorActivityCallback.sendPickedTime(minutesToReturn, numberOfSeconds)
    }
}