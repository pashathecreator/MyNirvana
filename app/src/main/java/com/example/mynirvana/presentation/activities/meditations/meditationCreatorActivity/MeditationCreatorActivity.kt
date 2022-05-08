package com.example.mynirvana.presentation.activities.meditations.meditationCreatorActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import com.example.mynirvana.R
import com.example.mynirvana.databinding.ActivityMeditationCreatorBinding
import com.example.mynirvana.domain.backgroundSounds.model.BackgroundSound
import com.example.mynirvana.domain.endSounds.model.EndSound
import com.example.mynirvana.domain.meditations.model.meditation.Meditation
import com.example.mynirvana.presentation.bottomSheets.backgroundSoundChoiceFragment.BackgroundSoundChoiceFragmentForMeditationCreation
import com.example.mynirvana.presentation.dialogs.saveMeditationAndStartDialog.SaveMeditationAndStartFragment
import com.example.mynirvana.presentation.dialogs.startMeditationWithoutSavingDialog.StartMeditationWithoutSavingFragment
import com.example.mynirvana.presentation.bottomSheets.endSoundsChoiceFragment.EndSoundChoiceFragment
import com.example.mynirvana.presentation.mainFragments.homeFragment.AskingForStartMeditation
import com.example.mynirvana.presentation.bottomSheets.timeChoiceFragment.TimeChoiceFragmentForMeditationCreatorActivity
import com.example.mynirvana.presentation.dialogs.userChoiceCallback.UserChoiceAboutMeditationFragmentDialogCallback
import com.example.mynirvana.presentation.timeConvertor.TimeConvertor
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MeditationCreatorActivity : AppCompatActivity(), MeditationCreatorActivityCallback,
    UserChoiceAboutMeditationFragmentDialogCallback, SaveMeditationAndStartCallback {

    private lateinit var binding: ActivityMeditationCreatorBinding
    private val viewModel: MeditationCreatorViewModel by viewModels()

    private lateinit var bottomSheet: BottomSheetDialogFragment
    private lateinit var meditationCreatorActivityCallback: MeditationCreatorActivityCallback
    private lateinit var currentButtonForBottomSheet: Button

    private var minutes: Int = 5
    private var seconds: Int = 0

    private var isMeditationNeedToBeStartedAndSaved = false

    companion object {
        private lateinit var askingForStartMeditation: AskingForStartMeditation
    }

    fun provideCallback(askingForStartMeditation: AskingForStartMeditation) {
        MeditationCreatorActivity.askingForStartMeditation = askingForStartMeditation
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMeditationCreatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButtons()
        meditationCreatorActivityCallback = this


    }

    private fun initButtons() {
        binding.backToHomeFragmentButton.setOnClickListener {
            onBackPressed()
        }

        binding.backgroundSoundButton.setOnClickListener {
            currentButtonForBottomSheet = it as Button

            bottomSheet =
                BackgroundSoundChoiceFragmentForMeditationCreation(
                    meditationCreatorActivityCallback,
                    it.text as String
                )
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

            bottomSheet =
                TimeChoiceFragmentForMeditationCreatorActivity(meditationCreatorActivityCallback)

            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        binding.saveButton.setOnClickListener {
            startSaveMeditationDialog()
        }

        binding.startCurrentMeditationButton.setOnClickListener {
            startCurrentMeditation()
        }
    }

    private var pickedBackgroundSound: Int = 0
    private var pickedEndSound: Int = 0


    override fun sendPickedBackgroundSound(backgroundSound: BackgroundSound) {
        currentButtonForBottomSheet.text = backgroundSound.name
        pickedBackgroundSound = backgroundSound.sound
    }


    override fun sendPickedEndSound(endSound: EndSound) {
        currentButtonForBottomSheet.text = endSound.name
        pickedEndSound = endSound.sound
    }

    override fun sendPickedTime(minutes: Int, seconds: Int) {
        this.minutes = minutes
        this.seconds = seconds
        binding.timeButton.text =
            TimeConvertor.convertTimeFromMinutesAndSecondsToMinutesFormat(minutes, seconds)
    }

    private fun saveCurrentMeditation() {
        val meditation = deserializeMeditation()
        viewModel.saveMeditation(meditation)
    }

    private fun startCurrentMeditation() {
        val meditation = deserializeMeditation()

        StartMeditationWithoutSavingFragment().also {
            it.provideCallBack(this)
            it.provideMeditation(meditation)
            it.show(supportFragmentManager, it.tag)
        }
    }

    private fun startSaveMeditationDialog() {
        saveCurrentMeditation()
        SaveMeditationAndStartFragment().also {
            it.provideCallback(this)
            it.isCancelable = false
            it.show(supportFragmentManager, it.tag)
        }
    }


    private fun deserializeMeditation(): Meditation {
        val backGroundImages = arrayOf(
            R.drawable.ic_rectangle_blue,
            R.drawable.ic_rectangle_dark_blue,
            R.drawable.ic_rectangle_green,
            R.drawable.ic_rectangle_orange,
            R.drawable.ic_rectangle_orange,
            R.drawable.ic_rectangle_purple,
            R.drawable.ic_rectangle_red
        )
        val backgroundImage = backGroundImages.random()
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
        val time = TimeConvertor.convertMinutesAndSecondsToSeconds(minutes, seconds)

        return Meditation(header, time, backgroundImage, backgroundSound, endSound)
    }

    override fun sendUserChoiceFromFragmentDialog(userChoice: Boolean) {
        this.isMeditationNeedToBeStartedAndSaved = userChoice
    }

    override fun userChoiceFragmentDialogDismissed() {
        if (isMeditationNeedToBeStartedAndSaved)
            saveCurrentMeditation()
        val meditation = deserializeMeditation()
        askingForStartMeditation.asksForStartMeditation(
            meditation
        )
        onBackPressed()
    }

    override fun onDestroy() {
        askingForStartMeditation.onReadyToStartMeditation()
        super.onDestroy()
    }


    private var isDialogAskingForStartMeditation = false

    override fun saveMeditationAndStartFragmentDialogAskForStartMeditation(
        isDialogAskingForStartMeditation: Boolean
    ) {
        this.isDialogAskingForStartMeditation = isDialogAskingForStartMeditation

    }

    override fun saveMeditationAndStartFragmentDialogDismissed(isDismissedByCrossButton: Boolean) {
        if (!isDismissedByCrossButton) {
            if (isDialogAskingForStartMeditation) {
                val meditation = deserializeMeditation()
                askingForStartMeditation.asksForStartMeditation(meditation)
            }
            onBackPressed()
        }

    }
}
