package com.example.mynirvana.presentation.dialogs.meditationCourseCompletedDialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentMeditationCourseCompletedBinding
import com.example.mynirvana.domain.meditations.model.meditationCourse.MeditationCourse
import com.example.mynirvana.presentation.activities.meditations.meditationCoursesActivity.MeditationCourseCompletedFragmentOnDismissCallback


class MeditationCourseCompletedFragment : DialogFragment() {

    companion object {
        var isDialogResumed: Boolean = false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomSheetDialog)
    }

    private lateinit var binding: FragmentMeditationCourseCompletedBinding
    private lateinit var currentMeditationCourse: MeditationCourse
    private lateinit var callback: MeditationCourseCompletedFragmentOnDismissCallback

    fun providesCurrentMeditationCourse(currentMeditationCourse: MeditationCourse) {
        this.currentMeditationCourse = currentMeditationCourse
    }

    fun providesCallback(callback: MeditationCourseCompletedFragmentOnDismissCallback) {
        this.callback = callback
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        isDialogResumed = true
        binding = FragmentMeditationCourseCompletedBinding.inflate(inflater)
        initView()
        return binding.root
    }

    private fun initView() {
        with(binding) {
            courseCompletedTV.text = "Вы полностью прошли курс \"${currentMeditationCourse.name}\"!"

            backToMeditationFragmentButtonInMeditationCourseCompletedFragment.setOnClickListener {
                this@MeditationCourseCompletedFragment.dismiss()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        callback.onDismissMeditationCourseCompletedFragment()
        isDialogResumed = false
        super.onDismiss(dialog)
    }

}