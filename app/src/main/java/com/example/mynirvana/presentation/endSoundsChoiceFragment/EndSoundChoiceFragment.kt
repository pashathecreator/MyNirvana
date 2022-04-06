package com.example.mynirvana.presentation.endSoundsChoiceFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.mynirvana.databinding.FragmentEndSoundChoiceBinding
import com.example.mynirvana.domain.endSounds.model.EndSound
import com.example.mynirvana.presentation.endSoundsRecycler.EndSoundsRecyclerAdapter
import com.example.mynirvana.presentation.getDataFromBottomSheetCallback.MeditationCreatorActivityCallback
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEndSoundChoiceBinding.inflate(inflater)

        initRecyclerView(binding)
        addDataSetToBackgroundSounds()
        binding.endSoundChoicePager.currentItem = findUserChoiceInData()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.endSoundChoicePager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                val userChoice = data[position]
                meditationCreatorActivityCallback.sendPickedEndSound(userChoice)
            }
        })
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

    private fun initRecyclerView(binding: FragmentEndSoundChoiceBinding) {
        binding.endSoundChoicePager.apply {
            endSoundsAdapter = EndSoundsRecyclerAdapter()
            adapter = endSoundsAdapter
        }

    }

}