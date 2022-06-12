package com.skelrath.library

import java.util.*

object NotificationIds {
    private var idHashMap = hashMapOf<Int, UUID>()

    fun getUuidById(id: Int): UUID? {
        val uuid = idHashMap[id]
        return uuid
    }


    fun setUuidById(id: Int, uuid: UUID) {
        if (!idHashMap.containsKey(id)) {
            idHashMap[id] = uuid
        }
    }

    fun deleteUuidById(id: Int) {
        if (idHashMap.containsKey(id)) {
            idHashMap.remove(id)
        }
    }
}