package com.example.mynirvana.domain.meditations.readyMeditationsData

import com.example.mynirvana.R

object GetRandomPictureForBackgroundOfMeditation {
    fun getRandomPictureForBackgroundOfMeditation(): Int {
        val backGroundImages = arrayOf(
            R.drawable.ic_rectangle_blue,
            R.drawable.ic_rectangle_dark_blue,
            R.drawable.ic_rectangle_green,
            R.drawable.ic_rectangle_orange,
            R.drawable.ic_rectangle_orange,
            R.drawable.ic_rectangle_purple,
            R.drawable.ic_rectangle_red
        )

        return backGroundImages.random()
    }
}