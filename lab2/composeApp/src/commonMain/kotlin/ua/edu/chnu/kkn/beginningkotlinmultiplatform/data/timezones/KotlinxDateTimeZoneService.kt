package ua.edu.chnu.kkn.beginningkotlinmultiplatform.data.timezones

import io.github.aakira.napier.Napier
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Instant

class KotlinxDateTimeZoneService : DateTimeZoneService {
    override fun nowInstant(): Instant = Clock.System.now()

    override fun availableTimeZoneIds(): Set<String> = TimeZone.availableZoneIds

    override fun currentSystemTimeZoneId(): String = TimeZone.currentSystemDefault().id

    override fun resolveTimeZone(timeZoneId: String): TimeZone = runCatching {
        TimeZone.of(timeZoneId)
    }.getOrElse {
        val fallback = TimeZone.currentSystemDefault()
        Napier.w(
            message = "Unknown time zone '$timeZoneId'. Fallback to '${fallback.id}'.",
            tag = "DateTimeZoneService"
        )
        fallback
    }

    override fun toLocalDateTime(instant: Instant, timeZoneId: String): LocalDateTime {
        val zone = resolveTimeZone(timeZoneId)
        val localDateTime = instant.toLocalDateTime(zone)
        Napier.d(
            message = "Converted instant $instant to local time $localDateTime for zone ${zone.id}.",
            tag = "DateTimeZoneService"
        )
        return localDateTime
    }

    override fun format(instant: Instant, timeZoneId: String): String {
        val localDateTime = toLocalDateTime(instant, timeZoneId)
        return localDateTime.toString()
    }
}
