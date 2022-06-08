package com.example.mynirvana.presentation.dialogs.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentTaskSavedBinding

class TaskSavedFragment : DialogFragment() {
    private lateinit var binding: FragmentTaskSavedBinding
    private var name: String = ""
    private var dateInString: String = ""

    fun provideHabitNameAndItsDataInStringFormat(name: String, dateInString: String) {
        this.name = name
        this.dateInString = dateInString
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
        binding = FragmentTaskSavedBinding.inflate(inflater)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.returnToMenuButtonInSavedTaskFragment.setOnClickListener {
            functionToLaunch?.let { function -> function() }
            this.dismiss()
        }
        binding.savedTaskTV.text = "Вы успешно сохранили дело \n\"$name\" \n на $dateInString"
    }

}