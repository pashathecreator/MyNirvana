package com.example.mynirvana.presentation.timeChoiceFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mynirvana.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TimerChoiceFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_timer_choice, container, false)
    }
}