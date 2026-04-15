package ua.edu.chnu.kkn.beginningkotlinmultiplatform.data.timezones

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone

interface DateTimeZoneService {
    fun nowInstant(): Instant
    fun availableTimeZoneIds(): Set<String>
    fun currentSystemTimeZoneId(): String
    fun toLocalDateTime(instant: Instant, timeZoneId: String): LocalDateTime
    fun format(instant: Instant, timeZoneId: String): String
    fun resolveTimeZone(timeZoneId: String): TimeZone
}
