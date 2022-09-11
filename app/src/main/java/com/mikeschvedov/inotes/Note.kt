package com.mikeschvedov.inotes

import java.util.*

data class Note (
    val noteID: String = UUID.randomUUID().toString(),
    val content: String,
    val date: String
)