package ua.edu.chnu.kkn.beginningkotlinmultiplatform

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.shared_mobile.main.MainScreen
import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.JsModule


@OptIn(ExperimentalWasmJsInterop::class)
@JsModule("@js-joda/timezone")
external object JsJodaTimeZoneModule

private val jsJodaTz = JsJodaTimeZoneModule

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    // ensures timezone data is loaded
    kotlinx.datetime.TimeZone.availableZoneIds

    ComposeViewport {
        MainScreen()
    }
}