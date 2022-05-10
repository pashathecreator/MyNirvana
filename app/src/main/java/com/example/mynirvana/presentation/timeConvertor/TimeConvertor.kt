package com.example.mynirvana.presentation.timeConvertor

object TimeConvertor {

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
}