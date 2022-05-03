package com.example.mynirvana.presentation.bottomSheets.backgroundSoundChoiceFragment

import android.content.DialogInterface
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.mynirvana.R
import com.example.mynirvana.databinding.FragmentSoundChoiceBinding
import com.example.mynirvana.domain.backgroundSounds.model.BackgroundSound
import com.example.mynirvana.presentation.activities.meditationCreatorActivity.MeditationCreatorActivityCallback
import com.example.mynirvana.presentation.recycler.recyclerSideSpacingDecoration.HorizontalMarginItemDecoration
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.math.abs

class BackgroundSoundChoiceFragmentForMeditationCreation(
    private val meditationCreatorActivityCallback: MeditationCreatorActivityCallback,
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
        initViewPager()
        addDataSetToBackgroundSounds()
        initFirstItem()

        return binding.root
    }


    private fun initFirstItem() {
        binding.backgroundSoundsPager.currentItem = findUserChoiceInData()
        startSound(data[binding.backgroundSoundsPager.currentItem].sound)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backgroundSoundsPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val userChoice = data[position]
                startSound(userChoice.sound)
                meditationCreatorActivityCallback.sendPickedBackgroundSound(userChoice)
            }
        })
    }

    override fun onCancel(dialog: DialogInterface) {
        stopSound()
        super.onCancel(dialog)
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


    private fun initViewPager() {
//        val compositePageTransformer = CompositePageTransformer()
//        compositePageTransformer.addTransformer(MarginPageTransformer(40))
//        compositePageTransformer.addTransformer { page, position ->
//            val v = 1 - kotlin.math.abs(position)
//            page.scaleY = 0.8f + v * 0.2f
//        }
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


//        binding.backgroundSoundsPager.clipToPadding = false
//        binding.backgroundSoundsPager.clipChildren = false
//        binding.backgroundSoundsPager.offscreenPageLimit = 3
//        binding.backgroundSoundsPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
//

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

//    fun ViewPager2.showHorizontalPreview(offsetDpLeft : Int, offsetDpRight : Int, marginBtwItems : Int){
//        this.apply {
//            clipToPadding = false   // allow full width shown with padding
//            clipChildren = false    // allow left/right item is not clipped
//            offscreenPageLimit = 2  // make sure left/right item is rendered
//        }
//
//        // increase this offset to show more of left/right
//        val offsetPxLeft = offsetDpLeft.toPx()
//        val offsetPxRight = offsetDpRight.toPx()
//        this.setPadding(offsetPxLeft, 0, offsetPxRight, 0)
//
//        // increase this offset to increase distance between 2 items
//        val pageMarginPx = marginBtwItems.toPx()
//        val marginTransformer = MarginPageTransformer(pageMarginPx)
//        this.setPageTransformer(marginTransformer)
//    }

}