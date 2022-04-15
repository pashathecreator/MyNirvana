package com.example.mynirvana.presentation.backgroundSoundChoiceFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.mynirvana.databinding.FragmentSoundChoiceBinding
import com.example.mynirvana.domain.backgroundSounds.model.BackgroundSound
import com.example.mynirvana.presentation.backgroundSoundRecycler.BackgroundSoundRecyclerAdapter
import com.example.mynirvana.presentation.meditationTimerActivity.BackgroundSoundsCallback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BackGroundSoundChoiceFragmentForMeditationTimer(
    private val backgroundSoundsCallback: BackgroundSoundsCallback,
    private val userChoiceName: String
) :
    BottomSheetDialogFragment() {

    private lateinit var binding: FragmentSoundChoiceBinding
    private lateinit var backgroundSoundsAdapter: BackgroundSoundRecyclerAdapter
    private val viewModel: BackgroundSoundChoiceViewModel by viewModels()
    private lateinit var data: List<BackgroundSound>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSoundChoiceBinding.inflate(inflater)

        initRecyclerView(binding)
        addDataSetToBackgroundSounds()
        binding.backgroundSoundsPager.currentItem = findUserChoiceInData()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.backgroundSoundsPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val userChoice = data[position]

                backgroundSoundsCallback.sendPickedBackgroundSound(userChoice)
            }
        })


    }

    private fun addDataSetToBackgroundSounds() {
        data = viewModel.getBackgroundSounds()
        backgroundSoundsAdapter.submitList(data)
    }

    private fun findUserChoiceInData(): Int {
        for (backgroundSoundIndex in data.indices) {
            if (data[backgroundSoundIndex].name == userChoiceName)
                return backgroundSoundIndex
        }
        return 0
    }


    private fun initRecyclerView(binding: FragmentSoundChoiceBinding) {
        binding.backgroundSoundsPager.apply {
            backgroundSoundsAdapter = BackgroundSoundRecyclerAdapter()
            adapter = backgroundSoundsAdapter
        }

    }
}