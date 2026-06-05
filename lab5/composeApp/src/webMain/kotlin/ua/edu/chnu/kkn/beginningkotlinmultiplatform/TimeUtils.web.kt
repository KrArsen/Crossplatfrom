package ua.edu.chnu.kkn.beginningkotlinmultiplatform

import kotlinx.datetime.TimeZone
import kotlin.js.ExperimentalWasmJsInterop

@OptIn(ExperimentalWasmJsInterop::class)
private fun getBrowserTimeZoneName(): String =
    js("Intl.DateTimeFormat().resolvedOptions().timeZone")

actual fun getCurrentTimeZone(): TimeZone =
    TimeZone.of(getBrowserTimeZoneName())
