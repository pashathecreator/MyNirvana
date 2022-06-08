package com.example.mynirvana.presentation.dialogs.meditation.meditationOnFinishDialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentMeditationOnFinishBinding

class MeditationOnFinishFragment : DialogFragment() {

    private lateinit var binding: FragmentMeditationOnFinishBinding
    private var time: Long = 0L

    fun provideTimeForMeditation(time: Long) {
        this.time = time
    }

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
        binding = FragmentMeditationOnFinishBinding.inflate(inflater)

        binding.backToHomeFragmentInMeditationOnFinish.setOnClickListener {
            functionToLaunch?.let { function -> function(true) }
            this.dismiss()
        }

        binding.startMeditationOneMoreTime.setOnClickListener {
            functionToLaunch?.let { function -> function(false) }
            this.dismiss()
        }

        binding.meditationTimeTV.text = "Вы медитировали ${secondsInLongToStringFormat(time)}!"

        return binding.root
    }


    private fun secondsInLongToStringFormat(seconds: Long): String {
        var tempSeconds = seconds
        val minutes = seconds / 60
        tempSeconds -= 60 * minutes
        val secondsToString = if (tempSeconds < 10) "0$tempSeconds" else tempSeconds.toString()

        return "$minutes:$secondsToString"
    }

    override fun onDismiss(dialog: DialogInterface) {
        isDialogResumed = false
        super.onDismiss(dialog)
    }

}