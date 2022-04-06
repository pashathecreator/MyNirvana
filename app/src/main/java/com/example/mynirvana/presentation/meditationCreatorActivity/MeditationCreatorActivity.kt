package com.example.mynirvana.presentation.meditationCreatorActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import com.example.mynirvana.R
import com.example.mynirvana.databinding.ActivityMeditationCreatorBinding
import com.example.mynirvana.domain.backgroundSounds.model.BackgroundSound
import com.example.mynirvana.domain.endSounds.model.EndSound
import com.example.mynirvana.domain.meditations.model.Meditation
import com.example.mynirvana.presentation.backgroundSoundChoiceFragment.BackgroundSoundChoiceFragment
import com.example.mynirvana.presentation.endSoundsChoiceFragment.EndSoundChoiceFragment
import com.example.mynirvana.presentation.getDataFromBottomSheetCallback.MeditationCreatorActivityCallback
import com.example.mynirvana.presentation.timeChoiceFragment.TimeChoiceFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeditationCreatorActivity : AppCompatActivity(), MeditationCreatorActivityCallback {

    private lateinit var binding: ActivityMeditationCreatorBinding
    private val viewModel: MeditationCreatorViewModel by viewModels()
    private lateinit var bottomSheet: BottomSheetDialogFragment
    private lateinit var meditationCreatorActivityCallback: MeditationCreatorActivityCallback
    private lateinit var currentButtonForBottomSheet: Button
    private var minutes: Int = 5
    private var seconds: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMeditationCreatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        meditationCreatorActivityCallback = this

        binding.backToHomeFragmentButton.setOnClickListener {
            onBackPressed()
        }

        binding.backgroundSoundButton.setOnClickListener {
            currentButtonForBottomSheet = it as Button

            bottomSheet =
                BackgroundSoundChoiceFragment(meditationCreatorActivityCallback, it.text as String)
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        binding.endSoundButton.setOnClickListener {
            currentButtonForBottomSheet = it as Button

            bottomSheet = EndSoundChoiceFragment(
                meditationCreatorActivityCallback,
                it.text as String
            )

            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        binding.timeButton.setOnClickListener {
            currentButtonForBottomSheet = it as Button

            bottomSheet = TimeChoiceFragment(meditationCreatorActivityCallback)

            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        binding.saveButton.setOnClickListener {
            saveCurrentMeditation()
        }

    }

    override fun sendPickedBackgroundSound(backgroundSound: BackgroundSound) {
        currentButtonForBottomSheet.text = backgroundSound.name
    }


    override fun sendPickedEndSound(endSound: EndSound) {
        currentButtonForBottomSheet.text = endSound.name
    }

    override fun sendPickedTime(minutes: Int, seconds: Int) {
        this.minutes = minutes
        this.seconds = seconds

        val secondsToString = if (seconds < 10) "0$seconds" else seconds.toString()
        val minuteWord = when (minutes) {
            1 -> "минута"
            2, 3, 4 -> "минуты"
            else -> "минут"
        }
        currentButtonForBottomSheet.text = "$minutes:${secondsToString} ${minuteWord}"
    }

    private fun saveCurrentMeditation() {
        val header = binding.meditationNameEditText.text.toString()
        val time = (minutes * 60 + seconds).toLong()

        val meditation = Meditation(header, time, R.drawable.guitar)

        viewModel.saveMeditation(meditation)
    }
}
