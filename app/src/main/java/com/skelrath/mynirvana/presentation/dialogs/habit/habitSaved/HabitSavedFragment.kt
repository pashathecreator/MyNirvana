package com.skelrath.mynirvana.presentation.dialogs.habit.habitSaved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.skelrath.mynirvana.R
import com.skelrath.mynirvana.databinding.FragmentHabitSavedBinding


class HabitSavedFragment : DialogFragment() {

    private lateinit var binding: FragmentHabitSavedBinding
    private var habitName: String = ""

    fun provideHabitName(name: String) {
        habitName = name
    }

    private var functionToLaunch: (() -> Unit?)? = null

    fun provideLambdaCallback(functionToLaunch: () -> Unit) {
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
        binding = FragmentHabitSavedBinding.inflate(inflater)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.returnToMenuButtonInSavedHabitFragment.setOnClickListener {
            functionToLaunch?.let { function -> function() }
            this.dismiss()
        }
        binding.savedHabitTV.text = "Вы успешно сохранили привычку \"$habitName\""
    }


}