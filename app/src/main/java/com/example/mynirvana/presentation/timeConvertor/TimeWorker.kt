package com.example.mynirvana.presentation.timeConvertor

import java.sql.Date
import java.util.*

object TimeWorker {

    fun convertTimeFromSecondsToMinutesFormat(timeInSeconds: Long): String {
        val minutes = (timeInSeconds / 60).toInt()
        val seconds = timeInSeconds % 60

        val secondsToString = if (seconds < 10) "0$seconds" else seconds.toString()
        val timeWord = when {
            minutes < 1 -> when (seconds) {
                1L -> "секунда"
                in 2..4 -> "секунды"
                else -> "секунд"
            }
            minutes == 1 -> "минута"
            minutes in 2..4 -> "минуты"
            else -> "минут"
        }

        return "$minutes:$secondsToString $timeWord"
    }

    fun convertTimeFromMinutesAndSecondsToMinutesFormat(minutes: Int, seconds: Int): String {
        val secondsToString = if (seconds < 10) "0$seconds" else seconds.toString()
        val timeWord = when {
            minutes < 1 -> when (seconds) {
                1 -> "секунда"
                in 2..4 -> "секунды"
                else -> "секунд"
            }
            minutes == 1 -> "минута"
            minutes in 2..4 -> "минуты"
            else -> "минут"
        }

        return "$minutes:$secondsToString $timeWord"
    }

    fun convertTimeFromSecondsToMinutesFormatWithoutTimeWord(seconds: Long): String {
        var tempSeconds = seconds
        val minutes = seconds / 60
        tempSeconds -= 60 * minutes
        val secondsToString = if (tempSeconds < 10) "0$tempSeconds" else tempSeconds.toString()

        return "$minutes:$secondsToString"
    }

    fun convertMinutesAndSecondsToSeconds(minutes: Int, seconds: Int): Long =
        (minutes * 60 + seconds).toLong()

    fun convertSecondsTo24HoursFormat(seconds: Long): String {
        var tempSeconds = seconds
        val hours = tempSeconds / 3600
        tempSeconds -= hours * 3600
        val minutes = tempSeconds / 60

        val hoursInString = when {
            hours < 10 -> "0$hours"
            else -> hours.toString()
        }

        val minutesInString = when {
            minutes < 10 -> "0$minutes"
            else -> minutes.toString()
        }

        return "$hoursInString:$minutesInString"
    }

    fun convertTimeToDayOfMonthAndMonth(date: Date): String {
        val months = listOf(
            "Января", "Февраля", "Марта", "Апреля",
            "Мая", "Июня", "Июля", "Августа", "Сентября",
            "Октября", "Ноября", "Декабря"
        )

        val calendar = Calendar.getInstance()
        calendar.time = date

        return "${calendar.get(Calendar.DAY_OF_MONTH)} ${months[calendar.get(Calendar.MONTH)]} ${
            calendar.get(
                Calendar.YEAR
            )
        } года"
    }

    fun checkIsProvidedDateIsToday(date: Date): Boolean {
        val calendar = Calendar.getInstance()
        val calendarToCompare = Calendar.getInstance()

        calendarToCompare.time = date

        return (calendar.get(Calendar.YEAR) == calendarToCompare.get(Calendar.YEAR) && calendar.get(
            Calendar.MONTH
        ) == calendarToCompare.get(Calendar.MONTH) && calendar.get(Calendar.DAY_OF_MONTH) == calendarToCompare.get(
            Calendar.DAY_OF_MONTH
        )
                )
    }

    fun compareTwoDates(firstDate: Date, secondsDate: Date): Boolean {
        val firstCalendar = Calendar.getInstance()
        val secondsCalendar = Calendar.getInstance()

        firstCalendar.time = firstDate
        secondsCalendar.time = secondsDate

        return (firstCalendar.get(Calendar.YEAR) == secondsCalendar.get(Calendar.YEAR) && firstCalendar.get(
            Calendar.MONTH
        ) == secondsCalendar.get(Calendar.MONTH) && firstCalendar.get(Calendar.DAY_OF_MONTH) == secondsCalendar.get(
            Calendar.DAY_OF_MONTH
        ))
    }
}