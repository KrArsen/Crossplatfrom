package ua.edu.chnu.kkn.beginningkotlinmultiplatform.data.timezones

import kotlin.time.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

interface DateTimeProvider {
    fun getCurrentLocalDateTime(): LocalDateTime
    fun getCurrentTimeZone(): TimeZone
}

class DateTimeProviderImpl : DateTimeProvider {
    override fun getCurrentLocalDateTime(): LocalDateTime {
        val now = Clock.System.now()
        val zone = getCurrentTimeZone()
        return now.toLocalDateTime(zone)
    }

    override fun getCurrentTimeZone(): TimeZone {
        return TimeZone.currentSystemDefault()
    }
}
