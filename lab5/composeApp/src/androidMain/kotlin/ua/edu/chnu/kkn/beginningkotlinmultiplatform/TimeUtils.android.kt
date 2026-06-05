package ua.edu.chnu.kkn.beginningkotlinmultiplatform

import kotlinx.datetime.TimeZone

actual fun getCurrentTimeZone(): TimeZone =
    TimeZone.currentSystemDefault()
