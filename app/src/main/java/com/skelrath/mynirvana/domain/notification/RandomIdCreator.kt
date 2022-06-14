package com.skelrath.mynirvana.domain.notification

import java.util.*

object RandomIdCreator {
    fun createId() = UUID.randomUUID().hashCode()
}