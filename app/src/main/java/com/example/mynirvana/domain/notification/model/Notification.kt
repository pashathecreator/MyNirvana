package com.example.mynirvana.domain.notification.model

data class Notification(
    val title: String = "Напоминание о запланированном деле",
    val message: String,
    var timeWhenNotificationAlarms: Long = 0
)
