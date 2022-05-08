package com.example.mynirvana.presentation.dialogs.startMeditationDialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentStartMeditationBinding
import com.example.mynirvana.presentation.dialogs.userChoiceCallback.UserChoiceAboutMeditationFragmentDialogCallback


class StartMeditationFragment() :
    DialogFragment() {


    private lateinit var binding: FragmentStartMeditationBinding
    private lateinit var startMeditationFragmentDialogCallback: UserChoiceAboutMeditationFragmentDialogCallback
    private lateinit var meditationName: String
    private var isDismissedByCrossButton: Boolean = false

    fun provideCallback(startMeditationFragmentDialogCallback: UserChoiceAboutMeditationFragmentDialogCallback) {
        this.startMeditationFragmentDialogCallback = startMeditationFragmentDialogCallback
    }

    fun provideMeditationName(meditationName: String) {
        this.meditationName = meditationName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartMeditationBinding.inflate(inflater)

        binding.sureForMeditationTV.text = "Начать медитацию $meditationName?"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.crossButton.setOnClickListener {
            startMeditationFragmentDialogCallback.sendUserChoiceFromFragmentDialog(false)
            this.dismiss()
        }

        binding.startMeditationButton.setOnClickListener {
            startMeditationFragmentDialogCallback.sendUserChoiceFromFragmentDialog(true)
            this.dismiss()
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        binding.crossButton.performClick()
    }

    override fun onDismiss(dialog: DialogInterface) {
        startMeditationFragmentDialogCallback.userChoiceFragmentDialogDismissed()
        super.onDismiss(dialog)
    }
}