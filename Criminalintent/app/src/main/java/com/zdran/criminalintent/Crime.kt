package com.zdran.criminalintent

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Crime(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var title: String = "",
    var data: Date = Date(),
    var isSolved: Boolean = false,
    var suspect: String = ""
) {
    val photoName
        get() = "IMG_${id}.jpg"
}