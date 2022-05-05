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
import com.example.mynirvana.presentation.activities.meditations.meditationCreatorActivity.MeditationCreatorActivityCallback
import com.example.mynirvana.presentation.recycler.recyclerSideSpacingDecoration.HorizontalMarginItemDecoration
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.math.abs

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
        binding.endSoundChoicePager.setPageTransformer(pageTransformer)

// The ItemDecoration gives the current (centered) item horizontal margin so that
// it doesn't occupy the whole screen width. Without it the items overlap
        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )
        binding.endSoundChoicePager.addItemDecoration(itemDecoration)


    }

}