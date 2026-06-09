# Лабораторна робота №6 — Пояснення

> **Git гілка:** `feature/system-info-app`

---

## 📋 Що потрібно було зробити
- Створити новий Kotlin Multiplatform (KMP) проєкт з назвою `lab6`.
- Оголосити механізм `expect`/`actual` для отримання системної інформації про пристрій (назва ОС, версія ОС та модель пристрою).
- Реалізувати `actual` функції для платформ Android та Desktop (JVM).
- Розробити спільний інтерфейс користувача з використанням Material3 теми для виведення отриманої системної інформації.
- Налаштувати логування через бібліотеку `Kermit`.

---

## 📁 Структура проєкту (важливі файли)
composeApp/src/
├── commonMain/kotlin/com/example/lab6/
│   ├── Platform.kt   ← expect функція getSystemInfo та модель SystemInfo
│   ├── App.kt   ← інтерфейс додатку, що викликає getSystemInfo
│   └── theme/
│       └── Theme.kt   ← кастомна Material3 тема Lab6Theme
├── androidMain/kotlin/com/example/lab6/
│   └── Platform.android.kt   ← actual реалізація для Android через Build API
├── jvmMain/kotlin/com/example/lab6/
│   └── Platform.jvm.kt   ← actual реалізація для Desktop через System.getProperty
└── settings.gradle.kts   ← конфігурація проєкту та зміна імені на "lab6"

---

## ✅ Виконані завдання

### 1. Нова назва проєкту (settings.gradle.kts)
**Що зроблено:** Назву проєкту змінено на `lab6`.
**Де знаходиться:** `settings.gradle.kts`
**Ключовий фрагмент:**
```kotlin
rootProject.name = "lab6"
include(":composeApp")
```
**Пояснення:** Тут я змінив стандартне ім'я проєкту в Gradle на `lab6`, щоб відокремити цю серію лабораторних робіт від попереднього додатку таймзон.

### 2. Оголошення expect-структури системної інформації (Platform.kt)
**Що зроблено:** Створено спільний клас даних `SystemInfo` та `expect` функцію `getSystemInfo()`.
**Де знаходиться:** `composeApp/src/commonMain/kotlin/com/example/lab6/Platform.kt`
**Ключовий фрагмент:**
```kotlin
data class SystemInfo(
    val osName: String,
    val osVersion: String,
    val deviceModel: String
)

expect fun getSystemInfo(): SystemInfo
```
**Пояснення:** Це наше оголошення очікуваного функціоналу. Я описав спільну модель даних `SystemInfo` та функцію `getSystemInfo()`, реалізацію якої кожна платформа має надати самостійно.

### 3. Реалізація actual для Android (Platform.android.kt)
**Що зроблено:** Реалізовано збір характеристик Android пристрою через API `android.os.Build`.
**Де знаходиться:** `composeApp/src/androidMain/kotlin/com/example/lab6/Platform.android.kt`
**Ключовий фрагмент:**
```kotlin
actual fun getSystemInfo(): SystemInfo = SystemInfo(
    osName = "Android",
    osVersion = "${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})",
    deviceModel = "${Build.MANUFACTURER} ${Build.MODEL}"
)
```
**Пояснення:** Тут я написав реальну логіку для платформи Android. Ми звертаємося до системного класу `Build`, звідки забираємо версію Android, API level, виробника та модель смартфона.

### 4. Реалізація actual для Desktop (Platform.jvm.kt)
**Що зроблено:** Написано збір інформації про операційну систему десктопа через системні властивості Java (`System.getProperty`).
**Де знаходиться:** `composeApp/src/jvmMain/kotlin/com/example/lab6/Platform.jvm.kt`
**Ключовий фрагмент:**
```kotlin
actual fun getSystemInfo(): SystemInfo = SystemInfo(
    osName = System.getProperty("os.name") ?: "Desktop (JVM)",
    osVersion = System.getProperty("os.version") ?: "Unknown",
    deviceModel = "${System.getProperty("os.arch")} JVM (${System.getProperty("java.vendor")} ${System.getProperty("java.version")})"
)
```
**Пояснення:** Для десктопної версії я використав стандартні змінні оточення JVM. Це дозволяє нам дізнатися назву ОС (Windows/macOS/Linux), її версію, архітектуру процесора та виробника встановленої версії Java.

### 5. Інтерфейс користувача та Kermit логер (App.kt)
**Що зроблено:** Системні дані отримуються безпосередньо в інтерфейсі за допомогою `remember` та виводяться в Material3 картку з логуванням через `Kermit`.
**Де знаходиться:** `composeApp/src/commonMain/kotlin/com/example/lab6/App.kt`
**Ключовий фрагмент:**
```kotlin
val systemInfo = remember { getSystemInfo() }

LaunchedEffect(Unit) {
    Logger.withTag("Lab6App").i { "Resolved System Information: $systemInfo" }
}
```
**Пояснення:** Тут у спільному коді я викликав нашу функцію `getSystemInfo()`. За допомогою `LaunchedEffect` ми логуємо отримані дані у консоль один раз при запуску додатку, а потім відображаємо їх на екрані.

---

## 🔗 Додані залежності
| Бібліотека | Навіщо |
|-----------|--------|
| `co.touchlab:kermit:2.1.0` | Кросплатформне логування системної інформації при старті додатку. |

---

## 🧩 Архітектурні рішення

**expect/actual:**
Оголошено `expect fun getSystemInfo(): SystemInfo` у `commonMain`. Кожна платформа реалізує її самостійно (`Platform.android.kt` та `Platform.jvm.kt`), повертаючи специфічні для своєї ОС параметри пристрою.

---

## ⚠️ Особливості та нюанси
Оскільки в цій лабораторній роботі ще немає архітектурних шарів на кшталт ViewModel чи репозиторіїв, виклик `getSystemInfo()` відбувається прямо у Composable-функції `App()` за допомогою `remember`, що робить отримання даних повністю синхронним при ініціалізації UI.
