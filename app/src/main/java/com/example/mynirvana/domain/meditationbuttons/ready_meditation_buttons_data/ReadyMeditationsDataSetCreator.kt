package com.example.mynirvana.domain.meditationbuttons.ready_meditation_buttons_data

import com.example.mynirvana.R
import com.example.mynirvana.domain.meditationbuttons.model.MeditationButton

object ReadyMeditationsDataSetCreator {

    private lateinit var readyMeditationsList: ArrayList<MeditationButton>

    fun createAndReturnDataSet(): ArrayList<MeditationButton> {
        val readyMeditationsList = ArrayList<MeditationButton>()

        readyMeditationsList.add(
            MeditationButton(
                header = "Спокойствие",
                time = "7 минут",
                imageResourceId = R.drawable.ready_meditation1,
                isReadyMeditation = true
            )
        )

        readyMeditationsList.add(
            MeditationButton(
                header = "Полная жизнь",
                time = "8 минут",
                imageResourceId = R.drawable.ready_meditation2,
                isReadyMeditation = true
            )
        )

        readyMeditationsList.add(
            MeditationButton(
                header = "Концентрация",
                time = "10 минут",
                imageResourceId = R.drawable.ready_meditation3,
                isReadyMeditation = true
            )
        )

//        addListToDatabase()

        return readyMeditationsList

    }

//    private suspend fun addListToDatabase() {
//
//        for (meditationButton in readyMeditationsList) {
//
//            meditationButtonsUseCases.addMeditationButtonUseCase.invoke(meditationButton = meditationButton)
//
//        }
//
//
//    }


}