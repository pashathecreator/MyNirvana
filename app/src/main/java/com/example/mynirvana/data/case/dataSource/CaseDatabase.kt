package com.example.mynirvana.data.case.dataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mynirvana.domain.case.model.Case

@Database(entities = [Case::class], version = 1)
abstract class CaseDatabase : RoomDatabase() {

    abstract fun getCaseDao(): CaseDao

    companion object {
        const val DATABASE_NAME = "case"
    }

}