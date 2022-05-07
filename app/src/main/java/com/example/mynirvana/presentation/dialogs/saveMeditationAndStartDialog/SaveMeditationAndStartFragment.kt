package com.example.mynirvana.presentation.dialogs.saveMeditationAndStartDialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentSaveMeditationAndStartDialogBinding
import com.example.mynirvana.presentation.activities.meditations.meditationCreatorActivity.SaveMeditationAndStartCallback


class SaveMeditationAndStartFragment : DialogFragment() {
    private lateinit var binding: FragmentSaveMeditationAndStartDialogBinding
    private lateinit var saveMeditationAndStartCallback: SaveMeditationAndStartCallback
    private var isDismissedByCrossButton: Boolean = false


    fun provideCallback(saveMeditationAndStartCallback: SaveMeditationAndStartCallback) {
        this.saveMeditationAndStartCallback = saveMeditationAndStartCallback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSaveMeditationAndStartDialogBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backToMainScreenButton.setOnClickListener {
            saveMeditationAndStartCallback.saveMeditationAndStartFragmentDialogAskForStartMeditation(false)
            this.dismiss()
        }

        binding.startSavedMeditation.setOnClickListener {
            saveMeditationAndStartCallback.saveMeditationAndStartFragmentDialogAskForStartMeditation(true)
            this.dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        saveMeditationAndStartCallback.saveMeditationAndStartFragmentDialogDismissed(isDismissedByCrossButton)
        super.onDismiss(dialog)
    }


}