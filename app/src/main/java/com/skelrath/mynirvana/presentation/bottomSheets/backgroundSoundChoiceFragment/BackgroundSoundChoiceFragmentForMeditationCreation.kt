package com.skelrath.mynirvana.presentation.bottomSheets.backgroundSoundChoiceFragment

import android.content.DialogInterface
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.skelrath.mynirvana.R
import com.skelrath.mynirvana.databinding.FragmentSoundChoiceBinding
import com.skelrath.mynirvana.domain.backgroundSounds.model.BackgroundSound
import com.skelrath.mynirvana.presentation.recycler.recyclerSideSpacingDecoration.HorizontalMarginItemDecoration
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.math.abs

class BackgroundSoundChoiceFragmentForMeditationCreation : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentSoundChoiceBinding
    private lateinit var backgroundSoundsAdapter: BackgroundSoundRecyclerAdapter
    private lateinit var data: List<BackgroundSound>

    private val viewModel: BackgroundSoundChoiceViewModel by viewModels()

    private var functionToLaunch: ((BackgroundSound) -> Unit?)? = null

    fun provideLambdaCallback(functionToLaunch: (backgroundSound: BackgroundSound) -> Unit) {
        this.functionToLaunch = functionToLaunch
    }

    private var userChoiceName: String = ""

    fun provideUserChoiceName(userChoiceName: String) {
        this.userChoiceName = userChoiceName
    }

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
                functionToLaunch?.let { it(userChoice) }
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
        if (sound != 0) {
            stopSound()
            mediaPlayer = MediaPlayer.create(requireContext(), sound)
            mediaPlayer?.start()
            mediaPlayer?.isLooping = true
        } else {
            stopSound()
        }
    }

    private fun stopSound() =
        mediaPlayer?.stop()


    private fun initViewPager() {
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
            page.scaleY = 1 - (0.25f * abs(position))
        }
        binding.backgroundSoundsPager.setPageTransformer(pageTransformer)


        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )
        binding.backgroundSoundsPager.addItemDecoration(itemDecoration)
    }
}