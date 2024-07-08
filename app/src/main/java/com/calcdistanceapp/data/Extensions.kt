package com.calcdistanceapp.data

import java.time.LocalDate
import java.time.temporal.ChronoUnit

fun LocalDate.isWithinLast24Hours(): Boolean {
    val now = LocalDate.now()
    val hoursDifference = ChronoUnit.HOURS.between(this.atStartOfDay(), now.atStartOfDay())
    return hoursDifference <= 24
}