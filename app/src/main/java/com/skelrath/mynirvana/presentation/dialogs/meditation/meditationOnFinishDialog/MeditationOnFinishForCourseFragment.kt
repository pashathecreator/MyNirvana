package com.skelrath.mynirvana.presentation.dialogs.meditation.meditationOnFinishDialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.skelrath.mynirvana.R
import com.skelrath.mynirvana.databinding.FragmentMeditationOnFinishForCourseBinding


class MeditationOnFinishForCourseFragment : DialogFragment() {

    private lateinit var binding: FragmentMeditationOnFinishForCourseBinding
    private var functionToLaunch: ((Boolean) -> Unit?)? = null

    fun provideLambdaCallback(functionToLaunch: (Boolean) -> Unit) {
        this.functionToLaunch = functionToLaunch
    }

    companion object {
        var isDialogResumed: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        isDialogResumed = true
        binding = FragmentMeditationOnFinishForCourseBinding.inflate(inflater)
        initButtons()
        return binding.root
    }

    private fun initButtons() {
        with(binding) {
            backToCourseActivity.setOnClickListener {
                functionToLaunch?.let { function -> function(false) }
                this@MeditationOnFinishForCourseFragment.dismiss()
            }
            backToHomeFragmentInMeditationOnFinishForCourse.setOnClickListener {
                functionToLaunch?.let { function -> function(true) }
                this@MeditationOnFinishForCourseFragment.dismiss()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        isDialogResumed = false
        super.onDismiss(dialog)
    }


}