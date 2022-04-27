package com.example.mynirvana.presentation.bottomSheets.endSoundsChoiceFragment

import android.content.DialogInterface
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentEndSoundChoiceBinding
import com.example.mynirvana.domain.endSounds.model.EndSound
import com.example.mynirvana.presentation.activities.meditationCreatorActivity.MeditationCreatorActivityCallback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EndSoundChoiceFragment(
    private val meditationCreatorActivityCallback: MeditationCreatorActivityCallback,
    private val userChoiceName: String
) :
    BottomSheetDialogFragment() {

    private lateinit var binding: FragmentEndSoundChoiceBinding
    private lateinit var endSoundsAdapter: EndSoundsRecyclerAdapter
    private val viewModel: EndSoundChoiceViewModel by viewModels()
    private lateinit var data: List<EndSound>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEndSoundChoiceBinding.inflate(inflater)

        initRecyclerView(binding)
        addDataSetToBackgroundSounds()
        initFirstItem()

        return binding.root
    }

    private fun initFirstItem() {
        binding.endSoundChoicePager.currentItem = findUserChoiceInData()
        startSound(data[binding.endSoundChoicePager.currentItem].sound)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.endSoundChoicePager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val userChoice = data[position]
                startSound(userChoice.sound)
                meditationCreatorActivityCallback.sendPickedEndSound(userChoice)
            }
        })
    }

    override fun onCancel(dialog: DialogInterface) {
        stopSound()
        super.onCancel(dialog)
    }

    private fun addDataSetToBackgroundSounds() {
        data = viewModel.getEndSounds()
        endSoundsAdapter.submitList(data)
    }

    private fun findUserChoiceInData(): Int {
        for (endSoundIndex in data.indices) {
            if (data[endSoundIndex].name == userChoiceName)
                return endSoundIndex
        }
        return 0
    }

    private var mediaPlayer: MediaPlayer? = null

    private fun startSound(sound: Int) {
        mediaPlayer?.stop()
        mediaPlayer = MediaPlayer.create(requireContext(), sound)
        mediaPlayer?.start()
        mediaPlayer?.isLooping = true


    }

    private fun stopSound() {
        mediaPlayer?.stop()
    }

    private fun initRecyclerView(binding: FragmentEndSoundChoiceBinding) {
        binding.endSoundChoicePager.apply {
            endSoundsAdapter = EndSoundsRecyclerAdapter()
            adapter = endSoundsAdapter
        }

    }

}