package com.example.newsaggregatorapp.util

import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun getTimeAgo(dateString: String?): String {
    if (dateString.isNullOrEmpty()) return "Unknown time"

    val formatter = DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of("UTC"))
    val time = ZonedDateTime.parse(dateString, formatter)
    val now = ZonedDateTime.now(ZoneId.of("UTC"))

    val diff = Duration.between(time, now)

    return when {
        diff.toMinutes() < 1 -> "Just now"
        diff.toMinutes() < 60 -> "${diff.toMinutes()} minutes ago"
        diff.toHours() < 24 -> "${diff.toHours()} hours ago"
        diff.toDays() < 7 -> "${diff.toDays()} days ago"
        else -> "${diff.toDays() / 7} weeks ago"
    }
}