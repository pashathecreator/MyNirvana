package com.example.mynirvana.presentation.endSoundsChoiceFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynirvana.databinding.FragmentEndSoundChoiceBinding
import com.example.mynirvana.databinding.FragmentSoundChoiceBinding
import com.example.mynirvana.presentation.backgroundSoundRecycler.BackgroundSoundRecyclerAdapter
import com.example.mynirvana.presentation.endSoundsRecycler.EndSoundsRecyclerAdapter
import com.example.mynirvana.presentation.recyclerSideSpacingDecoration.SideSpacingItemDecoration
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EndSoundChoiceFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentEndSoundChoiceBinding
    private lateinit var endSoundsAdapter: EndSoundsRecyclerAdapter
    private val viewModel: EndSoundChoiceViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEndSoundChoiceBinding.inflate(inflater)
        initRecyclerView(binding)
        addDataSetToBackgroundSounds()

        return binding.root
    }

    private fun addDataSetToBackgroundSounds() {
        val data = viewModel.getEndSounds()
        endSoundsAdapter.submitList(data)
    }


    private fun initRecyclerView(binding: FragmentEndSoundChoiceBinding) {
        binding.endSoundChoice.apply {
            endSoundsAdapter = EndSoundsRecyclerAdapter()
            adapter = endSoundsAdapter
        }
    }

}