package ua.edu.chnu.kkn.beginningkotlinmultiplatform.platform

import io.github.aakira.napier.ConsoleAntilog
import io.github.aakira.napier.Napier

actual fun initLogger() {
    Napier.base(ConsoleAntilog())
}
