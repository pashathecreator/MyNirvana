package com.example.mynirvana.domain.case.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.sql.Date


@Entity
data class Case(
    val name: String,
    val timeWhenCaseStarts: Long,
    val dateOfCase: Date,
    var isCaseCompleted: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
) : Serializable