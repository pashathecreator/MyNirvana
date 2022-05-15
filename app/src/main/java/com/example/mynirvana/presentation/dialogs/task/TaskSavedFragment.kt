package com.example.mynirvana.presentation.dialogs.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentTaskSavedBinding
import com.example.mynirvana.presentation.activities.tasks.HabitSavedFragmentCallback
import com.example.mynirvana.presentation.activities.tasks.TaskSavedFragmentCallback

class TaskSavedFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomSheetDialog)
    }

    private lateinit var binding: FragmentTaskSavedBinding
    private lateinit var callback: TaskSavedFragmentCallback
    private var name: String = ""
    private var dateInString: String = ""

    fun provideCallback(callback: TaskSavedFragmentCallback) {
        this.callback = callback
    }

    fun provideHabitNameAndItsDataInStringFormat(name: String, dateInString: String) {
        this.name = name
        this.dateInString = dateInString
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskSavedBinding.inflate(inflater)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.returnToMenuButtonInSavedTaskFragment.setOnClickListener {
            callback.onTaskSavedFragmentDismiss()
            this.dismiss()
        }
        binding.savedTaskTV.text = "Вы успешно сохранили дело \n\"$name\" \n на $dateInString"
    }

}