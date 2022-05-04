package com.example.mynirvana.domain.pomodoro.readyPomodorosData

import com.example.mynirvana.R
import com.example.mynirvana.domain.pomodoro.model.Pomodoro

enum class ReadyPomodoros(val pomodoro: Pomodoro) {
    Pomodoro1(
        Pomodoro(
            name = "Короткая помидорка",
            workTimeInSeconds = 1500,
            relaxTimeInSeconds = 300,
            quantityOfCircles = 3,
            imageResourceId = R.drawable.ic_rectangle_dark_blue
        )
    ),
    Pomodoro2(
        Pomodoro(
            name = "Продуктивный день",
            workTimeInSeconds = 3000,
            relaxTimeInSeconds = 600,
            quantityOfCircles = 8,
            imageResourceId = R.drawable.ic_rectangle_orange
        )
    ),
    Pomodoro3(
        Pomodoro(
            name = "Час концентрации",
            workTimeInSeconds = 1500,
            relaxTimeInSeconds = 300,
            quantityOfCircles = 1,
            imageResourceId = R.drawable.ic_rectangle_purple
        )
    ),
    Pomodoro4(
        Pomodoro(
            name = "Тестовая помидорка",
            workTimeInSeconds = 10,
            relaxTimeInSeconds = 5,
            quantityOfCircles = 3,
            imageResourceId = R.drawable.ic_rectangle_red
        )
    )
}