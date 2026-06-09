package ua.edu.chnu.kkn.beginningkotlinmultiplatform.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

data class MeetingSlot(
    val utcHour: Int,
    val timesPerZone: Map<String, String>
)

class TimeZoneViewModel {
    val selectedTimeZones: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val meetingResult: MutableStateFlow<List<MeetingSlot>?> = MutableStateFlow(null)

    fun addTimeZone(id: String) {
        if (id !in selectedTimeZones.value) {
            selectedTimeZones.value = selectedTimeZones.value + id
        }
    }

    fun removeTimeZone(id: String) {
        selectedTimeZones.value = selectedTimeZones.value.filterNot { it == id }
    }

    fun findMeetingTime(startHour: Int, endHour: Int): List<MeetingSlot> {
        val selectedZones = selectedTimeZones.value
        if (selectedZones.isEmpty()) {
            meetingResult.value = emptyList()
            return emptyList()
        }

        val validHours = buildSet {
            if (startHour <= endHour) {
                (startHour..endHour).forEach { add(it) }
            } else {
                (startHour..23).forEach { add(it) }
                (0..endHour).forEach { add(it) }
            }
        }

        val now = Clock.System.now()
        val utcDate = now.toLocalDateTime(TimeZone.UTC).date

        val slots = (0..23).mapNotNull { utcHour ->
            val utcDateTime = LocalDateTime(
                year = utcDate.year,
                monthNumber = utcDate.monthNumber,
                dayOfMonth = utcDate.dayOfMonth,
                hour = utcHour,
                minute = 0,
                second = 0,
                nanosecond = 0
            )
            val utcInstant = utcDateTime.toInstant(TimeZone.UTC)

            val timesPerZone = selectedZones.associateWith { zoneId ->
                val zoneTime = utcInstant.toLocalDateTime(TimeZone.of(zoneId))
                "${zoneTime.hour.twoDigits()}:${zoneTime.minute.twoDigits()}"
            }

            val allInRange = selectedZones.all { zoneId ->
                val zoneHour = utcInstant.toLocalDateTime(TimeZone.of(zoneId)).hour
                zoneHour in validHours
            }

            if (allInRange) MeetingSlot(utcHour = utcHour, timesPerZone = timesPerZone) else null
        }

        meetingResult.value = slots
        return slots
    }
}

private fun Int.twoDigits(): String = toString().padStart(2, '0')
