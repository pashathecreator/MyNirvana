package com.example.mynirvana.presentation.dialogs.sureToDeleteMeditation

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentSureToDeleteMeditationDialogBinding
import com.example.mynirvana.domain.meditations.model.meditation.Meditation

class SureToDeleteMeditationFragment : DialogFragment() {
    private lateinit var binding: FragmentSureToDeleteMeditationDialogBinding
    private lateinit var callback: DeleteMeditationCallback
    private lateinit var meditation: Meditation

    fun provideCallback(callback: DeleteMeditationCallback) {
        this.callback = callback
    }

    fun provideMeditation(meditation: Meditation) {
        this.meditation = meditation
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSureToDeleteMeditationDialogBinding.inflate(inflater)
        initButton()
        initHeader()

        return binding.root
    }

    private fun initHeader() {
        binding.sureToDeleteMeditationTV.text =
            "Вы уверены, что хотите удалить медитацию ${meditation.header}?"
    }

    private fun initButton() {
        binding.acceptButton.setOnClickListener {
            callback.userDecidedAboutDeletingMeditation(true)
            this.dismiss()
        }

        binding.crossButtonInDeleteMeditationDialog.setOnClickListener {
            callback.userDecidedAboutDeletingMeditation(false)
            this.dismiss()
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        binding.crossButtonInDeleteMeditationDialog.performClick()
    }


}