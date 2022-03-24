package com.example.mynirvana.presentation.meditationCreatorActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import com.example.mynirvana.databinding.ActivityMeditationCreatorBinding
import com.example.mynirvana.domain.backgroundSounds.model.BackgroundSound
import com.example.mynirvana.presentation.backgroundSoundChoiceFragment.BackgroundSoundChoiceFragment
import com.example.mynirvana.presentation.endSoundsChoiceFragment.EndSoundChoiceFragment
import com.example.mynirvana.presentation.getDataFromBottomSheet.GetDataFromBottomSheet
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MeditationCreatorActivity : AppCompatActivity(), GetDataFromBottomSheet {

    private lateinit var binding: ActivityMeditationCreatorBinding
    private val viewModel: MeditationCreatorViewModel by viewModels()
    private lateinit var bottomSheet: BottomSheetDialogFragment
    private lateinit var getDataFromBottomSheet: GetDataFromBottomSheet
    private lateinit var currentButtonForBottomSheet: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMeditationCreatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDataFromBottomSheet = this

        binding.backToHomeFragmentButton.setOnClickListener {
            onBackPressed()
        }

        binding.backgroundSoundButton.setOnClickListener {
            currentButtonForBottomSheet = it as Button

            bottomSheet = BackgroundSoundChoiceFragment(getDataFromBottomSheet)
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        binding.endSoundButton.setOnClickListener {
            currentButtonForBottomSheet = it as Button
            bottomSheet = EndSoundChoiceFragment()

            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }


    }

    override fun getDataFromBottomSheet(backgroundSound: BackgroundSound) {
        currentButtonForBottomSheet.text = backgroundSound.name
    }
}
