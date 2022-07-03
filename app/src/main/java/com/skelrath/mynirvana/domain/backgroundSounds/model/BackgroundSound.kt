package com.skelrath.mynirvana.domain.backgroundSounds.model

import androidx.room.Entity
import com.skelrath.mynirvana.R

data class BackgroundSound(
    val icon: Int,
    val sound: Int,
    val name: String,
    val backgroundImage: Int = R.color.purple,
    val colorForProgressIndicator: Int,
    val buttonBackground: Int = R.drawable.button_background
)