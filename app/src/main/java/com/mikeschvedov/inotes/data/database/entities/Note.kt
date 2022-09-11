package com.mikeschvedov.inotes.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Note (
    @PrimaryKey
    val noteID: String = UUID.randomUUID().toString(),
    val content: String,
    val date: Long
)