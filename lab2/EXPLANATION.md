# Лабораторна робота №2 — Пояснення

> **Git гілка:** `main`

---

## 📋 Що потрібно було зробити
- Підключити та налаштувати бібліотеку логування `Napier` для всіх таргетів KMP проєкту.
- Додати підтримку роботи з датою та часом через офіційну бібліотеку `kotlinx-datetime`.
- Створити інтерфейс `DateTimeProvider` та його реалізацію `DateTimeProviderImpl` в `commonMain` для отримання локального часу та поточної часової зони користувача.
- Забезпечити виклик створених методів та виведення інформації у вікно додатку разом із логуванням старту програми.

---

## 📁 Структура проєкту (важливі файли)
composeApp/src/
├── commonMain/kotlin/ua/edu/chnu/kkn/beginningkotlinmultiplatform/
│   ├── App.kt   ← інтерфейс додатку, виклик методів DateTimeProvider та логування Napier
│   ├── data/timezones/
│   │   ├── DateTimeProvider.kt   ← інтерфейс постачальника дати/часу та його реалізація
│   │   ├── DateTimeZoneService.kt   ← інтерфейс сервісу часових зон
│   │   └── KotlinxDateTimeZoneService.kt   ← реалізація сервісу через kotlinx-datetime
│   └── platform/
│       └── Platform.kt   ← expect функція для ініціалізації логера
├── androidMain/kotlin/ua/edu/chnu/kkn/beginningkotlinmultiplatform/platform/
│   └── Logger.android.kt   ← actual реалізація ініціалізації логера для Android
├── jvmMain/kotlin/ua/edu/chnu/kkn/beginningkotlinmultiplatform/platform/
│   └── Logger.desktop.kt   ← actual реалізація ініціалізації логера для Desktop
└── ...

---

## ✅ Виконані завдання

### 1. Інтерфейс та реалізація DateTimeProvider (DateTimeProvider.kt)
**Що зроблено:** Створено `DateTimeProvider` інтерфейс для отримання поточного системного часу та часової зони.
**Де знаходиться:** `composeApp/src/commonMain/kotlin/ua/edu/chnu/kkn/beginningkotlinmultiplatform/data/timezones/DateTimeProvider.kt`
**Ключовий фрагмент:**
```kotlin
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
```
**Пояснення:** Тут я описав спосіб доступу до часу пристрою. За допомогою `Clock.System.now()` ми беремо поточний момент UTC, а потім конвертуємо його у локальну дату та час користувача через системну часову зону за замовчуванням `TimeZone.currentSystemDefault()`.

### 2. Реалізація сервісу часових зон (KotlinxDateTimeZoneService.kt)
**Що зроблено:** Реалізовано інтерфейс `DateTimeZoneService` для конвертації миттєвого часу в локальний з логуванням через `Napier`.
**Де знаходиться:** `composeApp/src/commonMain/kotlin/ua/edu/chnu/kkn/beginningkotlinmultiplatform/data/timezones/KotlinxDateTimeZoneService.kt`
**Ключовий фрагмент:**
```kotlin
override fun toLocalDateTime(instant: Instant, timeZoneId: String): LocalDateTime {
    val zone = resolveTimeZone(timeZoneId)
    val localDateTime = instant.toLocalDateTime(zone)
    Napier.d(
        message = "Converted instant $instant to local time $localDateTime for zone ${zone.id}.",
        tag = "DateTimeZoneService"
    )
    return localDateTime
}
```
**Пояснення:** Цей сервіс використовується для перетворення часу між різними таймзонами. Кожне успішне перетворення або помилка (наприклад, коли вказано невідому таймзону і ми повертаємо дефолтну) обов'язково логується через `Napier`.

### 3. Логування запуску додатку (App.kt)
**Що зроблено:** При старті додатку ініціалізується логер і записується інформація про пристрій та час у лог.
**Де знаходиться:** `composeApp/src/commonMain/kotlin/ua/edu/chnu/kkn/beginningkotlinmultiplatform/App.kt`
**Ключовий фрагмент:**
```kotlin
LaunchedEffect(Unit) {
    initLogger()
    Napier.i(
        message = "App started. Current platform: ${platformName()}. Current Date/Time: $currentDateTime, TimeZone: $currentTimeZone",
        tag = "App"
    )
}
```
**Пояснення:** При першому рендерингу додатку я викликаю функцію `initLogger()`, яка налаштовує логер відповідно до платформи, та логую інформаційний меседж з поточною платформою, датою та таймзоною користувача за допомогою `Napier.i`.

---

## 🔗 Додані залежності
| Бібліотека | Навіщо |
|-----------|--------|
| `io.github.aakira:napier:2.7.1` | Кросплатформна бібліотека логування (підтримує Logcat на Android, Log на JVM тощо). |
| `org.jetbrains.kotlinx:kotlinx-datetime:0.6.0` | Робота з часом, датами та таймзонами у спільному коді KMP. |

---

## 🧩 Архітектурні рішення

**expect/actual логер:**
Логер потребує специфічної ініціалізації на кожній платформі.
- В `commonMain` оголошено `expect fun initLogger()`.
- В `androidMain` підставляється `Napier.base(DebugAntilog())` для виведення в Android Logcat.
- В `jvmMain` (Desktop) або `wasmJsMain` ініціалізується логер для консольного виводу.

---

## ⚠️ Особливості та нюанси
Для Web (WasmJs) платформи у налаштуваннях `settings.gradle.kts` було замінено `RepositoriesMode.FAIL_ON_PROJECT_REPOS` на `PREFER_PROJECT` та налаштовано антилог для підтримки браузерного виводу логів.
