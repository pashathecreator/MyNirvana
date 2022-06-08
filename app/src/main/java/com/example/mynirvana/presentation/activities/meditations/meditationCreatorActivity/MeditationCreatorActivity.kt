package com.example.mynirvana.presentation.activities.meditations.meditationCreatorActivity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mynirvana.R
import com.example.mynirvana.databinding.ActivityMeditationCreatorBinding
import com.example.mynirvana.domain.endSounds.model.EndSound
import com.example.mynirvana.domain.meditations.model.meditation.Meditation
import com.example.mynirvana.domain.meditations.readyMeditationsData.GetRandomPictureForBackgroundOfMeditation
import com.example.mynirvana.presentation.bottomSheets.backgroundSoundChoiceFragment.BackgroundSoundChoiceFragmentForMeditationCreation
import com.example.mynirvana.presentation.bottomSheets.endSoundsChoiceFragment.EndSoundChoiceFragment
import com.example.mynirvana.presentation.bottomSheets.timeChoiceFragment.TimeChoiceFragmentForMeditationCreatorActivity
import com.example.mynirvana.presentation.dialogs.meditation.saveMeditationAndStartDialog.SaveMeditationAndStartFragment
import com.example.mynirvana.presentation.dialogs.meditation.startMeditationWithoutSavingDialog.StartMeditationWithoutSavingFragment
import com.example.mynirvana.presentation.timeConvertor.TimeWorker
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MeditationCreatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMeditationCreatorBinding
    private val viewModel: MeditationCreatorViewModel by viewModels()

    private var minutes: Int = 5
    private var seconds: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMeditationCreatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButtons()
    }

    private fun initButtons() {
        binding.backToHomeFragmentButton.setOnClickListener {
            finish()
        }

        binding.backgroundSoundButton.setOnClickListener {
            openBackgroundSoundPicker(it as Button)
        }

        binding.endSoundButton.setOnClickListener {
            openEndSoundPicker(it as Button)
        }

        binding.timeButton.setOnClickListener {
            openTimePicker()
        }

        binding.saveButton.setOnClickListener {
            openSaveMeditationAndStartDialog()
        }

        binding.startCurrentMeditationButton.setOnClickListener {
            openStartMeditationWithoutSavingDialog()
        }
    }

    private var pickedBackgroundSound: Int = 0
    private var pickedEndSound: Int = 0


    private fun openBackgroundSoundPicker(buttonForBottomSheet: Button) {
        BackgroundSoundChoiceFragmentForMeditationCreation().also {
            it.provideUserChoiceName(buttonForBottomSheet.text.toString())
            it.provideLambdaCallback { backgroundSound ->
                buttonForBottomSheet.text = backgroundSound.name
                pickedBackgroundSound = backgroundSound.sound
            }

            it.show(supportFragmentManager, it.tag)
        }
    }

    private fun openEndSoundPicker(buttonForBottomSheet: Button) {
        EndSoundChoiceFragment().also {
            it.provideUserChoiceName(buttonForBottomSheet.text.toString())
            it.provideLambdaCallback { endSound: EndSound ->
                buttonForBottomSheet.text = endSound.name
                pickedEndSound = endSound.sound
            }

            it.show(supportFragmentManager, it.tag)
        }
    }

    private fun openTimePicker() {
        TimeChoiceFragmentForMeditationCreatorActivity().also {
            it.provideLambdaCallback { minutes, seconds ->
                this.minutes = minutes
                this.seconds = seconds
                binding.timeButton.text =
                    TimeWorker.convertTimeFromMinutesAndSecondsToMinutesFormat(minutes, seconds)
            }

            it.show(supportFragmentManager, it.tag)
        }
    }

    private fun saveCurrentMeditation() {
        val meditation = deserializeMeditation()
        viewModel.saveMeditation(meditation)
    }

    private fun openStartMeditationWithoutSavingDialog() {
        StartMeditationWithoutSavingFragment().also {
            val meditation = deserializeMeditation()

            it.provideLambdaCallback { userChoice: Boolean ->
                if (userChoice) {
                    Intent().also { intent ->
                        intent.putExtra("MEDITATION_TO_START", meditation)
                        setResult(RESULT_OK, intent)
                    }
                }
                finish()
            }

            it.provideMeditation(meditation)
            it.show(supportFragmentManager, it.tag)
        }
    }

    private fun openSaveMeditationAndStartDialog() {
        SaveMeditationAndStartFragment().also {
            saveCurrentMeditation()
            it.provideLambdaCallback { userChoice: Boolean ->
                if (userChoice)
                    saveCurrentMeditation()
                Intent().also { intent ->
                    intent.putExtra("MEDITATION_TO_START", deserializeMeditation())
                    setResult(RESULT_OK, intent)
                }
                finish()
            }
            it.isCancelable = false
            it.show(supportFragmentManager, it.tag)
        }
    }


    private fun deserializeMeditation(): Meditation {
        val backgroundImage =
            GetRandomPictureForBackgroundOfMeditation.getRandomPictureForBackgroundOfMeditation()

        var header = binding.meditationNameEditText.text.toString()
        if (header.isBlank()) {
            header = "Без названия"
        }
        var backgroundSound = R.raw.fire_sound
        var endSound = R.raw.guitar_sound
        if (pickedBackgroundSound != 0) {
            backgroundSound = pickedBackgroundSound
        }
        if (pickedEndSound != 0) {
            endSound = pickedEndSound
        }
        val time = TimeWorker.convertMinutesAndSecondsToSeconds(minutes, seconds)

        return Meditation(header, time, backgroundImage, backgroundSound, endSound)
    }
}
