package ua.edu.chnu.kkn.beginningkotlinmultiplatform

import kotlinx.datetime.TimeZone

@JsName("eval")
external fun jsEval(code: String): String

actual fun getCurrentTimeZone(): TimeZone {
    val tzId = jsEval(
        "Intl.DateTimeFormat().resolvedOptions().timeZone"
    )
    return TimeZone.of(tzId)
}
