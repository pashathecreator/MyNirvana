package com.example.mynirvana.domain.notification

object NotificationIdCreator {
    private val idSet = mutableSetOf<Int>()

    fun createNotificationIdAndReturnItAndAlsoAddThisIdToIdSet(): Int {
        var randomNumberForId: Int
        do {
            randomNumberForId = (1..999).random()
        } while (idSet.contains(randomNumberForId))

        idSet.add(randomNumberForId)

        return randomNumberForId
    }

}