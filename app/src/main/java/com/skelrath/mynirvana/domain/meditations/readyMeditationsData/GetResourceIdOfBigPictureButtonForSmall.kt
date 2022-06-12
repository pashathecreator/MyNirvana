package com.skelrath.mynirvana.domain.meditations.readyMeditationsData

import com.skelrath.mynirvana.R
import kotlin.collections.HashMap

object GetResourceIdOfBigPictureButtonForSmall {
    private val resourceDictionary: HashMap<Int, Int> =
        hashMapOf(
            R.drawable.ic_rectangle_blue to R.drawable.ic_rectangle_blue_for_mini_button,
            R.drawable.ic_rectangle_dark_blue to R.drawable.ic_rectangle_dark_blue_for_mini_button,
            R.drawable.ic_rectangle_green to R.drawable.ic_rectangle_green_for_mini_button,
            R.drawable.ic_rectangle_orange to R.drawable.ic_rectangle_orange_for_mini_button,
            R.drawable.ic_rectangle_purple to R.drawable.ic_rectangle_purple_for_mini_button,
            R.drawable.ic_rectangle_red to R.drawable.ic_rectangle_red_for_mini_button
        )

    fun getResourceIdForMiniButton(imageResourceIdForBigButton: Int) =
        resourceDictionary[imageResourceIdForBigButton]
}