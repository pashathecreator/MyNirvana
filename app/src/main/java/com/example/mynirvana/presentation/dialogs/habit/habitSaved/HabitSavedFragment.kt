package com.example.mynirvana.presentation.dialogs.habit.habitSaved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentHabitSavedBinding
import com.example.mynirvana.presentation.activities.tasks.HabitSavedFragmentCallback


class HabitSavedFragment : DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomSheetDialog)
    }

    private lateinit var binding: FragmentHabitSavedBinding
    private lateinit var callback: HabitSavedFragmentCallback
    private var habitName: String = ""

    fun provideCallback(callback: HabitSavedFragmentCallback) {
        this.callback = callback
    }

    fun provideHabitName(name: String) {
        habitName = name
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
            callback.onHabitSavedFragmentDismiss()
            this.dismiss()
        }
        binding.savedHabitTV.text = "Вы успешно сохранили привычку \"$habitName\""
    }


}