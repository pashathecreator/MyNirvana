package com.example.mynirvana.presentation.backgroundSoundChoiceFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentSoundChoiceBinding
import com.example.mynirvana.domain.backgroundSounds.model.BackgroundSound
import com.example.mynirvana.presentation.backgroundSoundRecycler.BackgroundSoundRecyclerAdapter
import com.example.mynirvana.presentation.getDataFromBottomSheet.GetDataFromBottomSheet
import com.example.mynirvana.presentation.recyclerSideSpacingDecoration.HorizontalMarginItemDecoration
import com.example.mynirvana.presentation.recyclerSideSpacingDecoration.SideSpacingItemDecoration
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.lang.Math.abs

class BackgroundSoundChoiceFragment(val getDataFromBottomSheet: GetDataFromBottomSheet) :
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var userChoice: BackgroundSound = data[0]

        binding.backgroundSoundsPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                userChoice = data[position]
            }
        })

        getDataFromBottomSheet.getDataFromBottomSheet(userChoice)

    }

    private fun addDataSetToBackgroundSounds() {
        data = viewModel.getBackgroundSounds()
        backgroundSoundsAdapter.submitList(data)
    }


    private fun initRecyclerView(binding: FragmentSoundChoiceBinding) {
        binding.backgroundSoundsPager.apply {
            backgroundSoundsAdapter = BackgroundSoundRecyclerAdapter()
            adapter = backgroundSoundsAdapter
        }


//        binding.backgroundSoundsPager.clipToPadding = false
//        binding.backgroundSoundsPager.clipChildren = false
//        binding.backgroundSoundsPager.offscreenPageLimit = 3
//        binding.backgroundSoundsPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
//
//        val compositePageTransformer = CompositePageTransformer()
//        compositePageTransformer.addTransformer(MarginPageTransformer(40))
//        compositePageTransformer.addTransformer { page, position ->
//            val r = 1 - kotlin.math.abs(position)
//            page.scaleY = 0.85f + r * 0.15f
//
//        }
//
//        binding.backgroundSoundsPager.setPageTransformer(compositePageTransformer)


//// You need to retain one page on each side so that the next and previous items are visible
//        binding.backgroundSoundsPager.offscreenPageLimit = 1
//
//// Add a PageTransformer that translates the next and previous items horizontally
//// towards the center of the screen, which makes them visible
//        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
//        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
//        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
//        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
//            page.translationX = -pageTranslationX * position
//            // Next line scales the item's height. You can remove it if you don't want this effect
//            page.scaleY = 1 - (0.25f * abs(position))
//            // If you want a fading effect uncomment the next line:
//            // page.alpha = 0.25f + (1 - abs(position))
//        }
//        binding.backgroundSoundsPager.setPageTransformer(pageTransformer)
//
//// The ItemDecoration gives the current (centered) item horizontal margin so that
//// it doesn't occupy the whole screen width. Without it the items overlap
//        val itemDecoration = HorizontalMarginItemDecoration(
//            requireContext(),
//            R.dimen.viewpager_current_item_horizontal_margin
//        )
//        binding.backgroundSoundsPager.addItemDecoration(itemDecoration)

    }

}