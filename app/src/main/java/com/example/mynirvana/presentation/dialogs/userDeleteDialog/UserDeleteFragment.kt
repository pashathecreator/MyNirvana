package com.example.mynirvana.presentation.dialogs.userDeleteDialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentUserDeleteBinding

import com.example.mynirvana.domain.meditations.model.meditation.Meditation
import com.example.mynirvana.domain.pomodoro.model.Pomodoro

class UserDeleteFragment : DialogFragment() {

    private lateinit var binding: FragmentUserDeleteBinding
    private var functionToLaunch: ((Boolean) -> Unit?)? = null
    private var meditation: Meditation? = null
    private var pomodoro: Pomodoro? = null


    fun provideLambdaCallback(functionToLaunch: (userChoice: Boolean) -> Unit) {
        this.functionToLaunch = functionToLaunch
    }

    fun provideMeditation(meditation: Meditation) {
        this.meditation = meditation
    }

    fun providePomodoro(pomodoro: Pomodoro) {
        this.pomodoro = pomodoro
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDeleteBinding.inflate(inflater)
        initButton()
        initHeader()

        return binding.root
    }

    private fun initHeader() {
        meditation?.let {
            binding.sureToDeleteTV.text =
                "Вы уверены, что хотите удалить медитацию\n\"${it.name}?\""
        }

        pomodoro?.let {
            binding.sureToDeleteTV.text =
                "Вы уверены, что хотите удалить таймер\n\"${it.name}?\"?"
        }
    }

    private fun initButton() {
        binding.acceptButton.setOnClickListener {
            functionToLaunch?.let { function -> function(true) }
            this.dismiss()
        }

        binding.crossButtonInDeleteDialog.setOnClickListener {
            functionToLaunch?.let { function -> function(false) }
            this.dismiss()
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        binding.crossButtonInDeleteDialog.performClick()
    }


}