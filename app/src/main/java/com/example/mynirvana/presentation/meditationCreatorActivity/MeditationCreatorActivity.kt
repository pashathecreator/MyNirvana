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
import com.example.mynirvana.presentation.backgroundSoundChoiceFragment.BackgroundSoundChoiceFragmentForMeditationCreation
import com.example.mynirvana.presentation.dialogs.saveMeditationAndStartIt.SaveMeditationAndStartFragmentDialog
import com.example.mynirvana.presentation.dialogs.startMeditationWithoutSavingDialog.StartMeditationWithoutSavingFragmentDialog
import com.example.mynirvana.presentation.endSoundsChoiceFragment.EndSoundChoiceFragment
import com.example.mynirvana.presentation.homeFragment.AskingForStartMeditation
import com.example.mynirvana.presentation.timeChoiceFragment.TimeChoiceFragment
import com.example.mynirvana.presentation.userChoiceCallback.UserChoiceAboutMeditationFragmentDialogCallback
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

        meditationCreatorActivityCallback = this

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

            bottomSheet = TimeChoiceFragment(meditationCreatorActivityCallback)

            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        binding.saveButton.setOnClickListener {
            startSaveMeditationDialog()
        }

        binding.startCurrentMeditationButton.setOnClickListener {
            startCurrentMeditation()
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
        currentButtonForBottomSheet.text = "$minutes:$secondsToString $minuteWord"
    }

    private fun saveCurrentMeditation() {
        val meditation = deserializeMeditation()
        viewModel.saveMeditation(meditation)
    }

    private fun startCurrentMeditation() {
        val meditation = deserializeMeditation()
        val dialog = StartMeditationWithoutSavingFragmentDialog()

        dialog.provideCallBack(this)
        dialog.provideMeditation(meditation)

        dialog.show(supportFragmentManager, dialog.tag)
    }

    private fun startSaveMeditationDialog() {
        saveCurrentMeditation()
        val dialog = SaveMeditationAndStartFragmentDialog()
        dialog.provideCallback(this)
        dialog.show(supportFragmentManager, dialog.tag)
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
        val time = (minutes * 60 + seconds).toLong()
        return Meditation(header, time, backgroundImage)
    }

    override fun sendUserChoiceFromFragmentDialog(userChoice: Boolean) {
        this.isMeditationNeedToBeStartedAndSaved = userChoice
    }


    override fun userChoiceFragmentDialogDismissed(isDismissedByCrossButton: Boolean) {
        if (!isDismissedByCrossButton) {
            if (isMeditationNeedToBeStartedAndSaved)
                saveCurrentMeditation()
            val meditation = deserializeMeditation()
            askingForStartMeditation.asksForStartMeditation(
                meditation
            )

            onBackPressed()
        }


    }

    override fun onDestroy() {
        askingForStartMeditation.onMeditationCreatorActivityDestroyed()
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
