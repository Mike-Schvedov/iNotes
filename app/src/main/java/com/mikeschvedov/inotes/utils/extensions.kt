package com.mikeschvedov.inotes.utils

import java.text.SimpleDateFormat
import java.util.*

// Taking in a Unix time and formatting it to a date as String
fun Long.tbx_fromUnixToFormatted(): String {
    val sdf = SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault())
    sdf.timeZone = TimeZone.getTimeZone("Asia/Jerusalem");
    val netDate = Date(this * 1000)
    return sdf.format(netDate)
}