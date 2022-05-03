package com.example.mynirvana.presentation.bottomSheets.backgroundSoundChoiceFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentSoundChoiceBinding
import com.example.mynirvana.domain.backgroundSounds.model.BackgroundSound
import com.example.mynirvana.presentation.activities.meditationTimerActivity.BackgroundSoundsCallback
import com.example.mynirvana.presentation.recycler.recyclerSideSpacingDecoration.HorizontalMarginItemDecoration
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.math.abs

class BackGroundSoundChoiceFragmentForMeditationTimer(
    private val backgroundSoundsCallback: BackgroundSoundsCallback,
    private val userChoiceName: String
) :
    BottomSheetDialogFragment() {

    private lateinit var binding: FragmentSoundChoiceBinding
    private lateinit var backgroundSoundsAdapter: BackgroundSoundRecyclerAdapter
    private val viewModel: BackgroundSoundChoiceViewModel by viewModels()
    private lateinit var data: List<BackgroundSound>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSoundChoiceBinding.inflate(inflater)

        initRecyclerView()
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


    private fun initRecyclerView() {
        binding.backgroundSoundsPager.apply {
            backgroundSoundsAdapter = BackgroundSoundRecyclerAdapter()
            adapter = backgroundSoundsAdapter
            offscreenPageLimit = 2
            clipToPadding = false
            clipToPadding = false
        }

        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            // Next line scales the item's height. You can remove it if you don't want this effect
            page.scaleY = 1 - (0.25f * abs(position))
            // If you want a fading effect uncomment the next line:
            // page.alpha = 0.25f + (1 - abs(position))
        }
        binding.backgroundSoundsPager.setPageTransformer(pageTransformer)

// The ItemDecoration gives the current (centered) item horizontal margin so that
// it doesn't occupy the whole screen width. Without it the items overlap
        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )
        binding.backgroundSoundsPager.addItemDecoration(itemDecoration)


    }
}