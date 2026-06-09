# Лабораторна робота №7 — Пояснення

> **Git гілка:** `feature/system-info-app`

---

## 📋 Що потрібно було зробити
- Інтегрувати життєвий цикл Jetpack Lifecycle ViewModel у спільний код кросплатформного додатку.
- Створити `AboutViewModel` та структуру стану екрану `AboutUiState`.
- Перенести збір системних даних та збереження статичних метаданих додатку з шару UI (Composable) до шару ViewModel.
- Забезпечити реактивне оновлення інтерфейсу через `StateFlow` та `collectAsState()`.

---

## 📁 Структура проєкту (важливі файли)
composeApp/src/
├── commonMain/kotlin/com/example/lab6/
│   ├── AboutViewModel.kt   ← опис AboutUiState та AboutViewModel з бізнес-даними
│   ├── App.kt   ← UI, який отримує ViewModel та підписується на uiState
│   └── Platform.kt   ← expect функції для системних даних
└── ...

---

## ✅ Виконані завдання

### 1. Створення AboutViewModel та AboutUiState
**Що зроблено:** Створено ViewModel клас, який інкапсулює стан екрану "Про додаток" та підвантажує системну інформацію.
**Де знаходиться:** `composeApp/src/commonMain/kotlin/com/example/lab6/AboutViewModel.kt`
**Ключовий фрагмент:**
```kotlin
data class AboutUiState(
    val appName: String = "",
    val version: String = "",
    val author: String = "",
    val description: String = "",
    val osName: String = "",
    val osVersion: String = "",
    val deviceModel: String = ""
)

class AboutViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AboutUiState(
        appName = "Diagnostics App",
        version = "1.0.0",
        author = "Student",
        description = "Kotlin Multiplatform application with dynamic Material3 theme and Kermit logger.",
        osName = getSystemInfo().osName,
        osVersion = getSystemInfo().osVersion,
        deviceModel = getSystemInfo().deviceModel
    ))
    val uiState: StateFlow<AboutUiState> = _uiState
}
```
**Пояснення:** Тут я виніс усю логіку підготовки даних з UI в окремий клас `AboutViewModel`. Стан екрану зберігається в об'єкті `AboutUiState`, а сама ViewModel при створенні наповнює цей стан як статичними метаданими (назва додатку, автор), так і динамічними даними ОС через `getSystemInfo()`.

### 2. Реактивне зв'язування з UI (App.kt)
**Що зроблено:** Компонент `App` тепер приймає `AboutViewModel` як параметр та підписується на оновлення стану через `collectAsState()`.
**Де знаходиться:** `composeApp/src/commonMain/kotlin/com/example/lab6/App.kt`
**Ключовий фрагмент:**
```kotlin
@Composable
fun App(viewModel: AboutViewModel = viewModel()) {
    Lab6Theme {
        val uiState by viewModel.uiState.collectAsState()
        
        LaunchedEffect(uiState) {
            Logger.withTag("Lab7App").i { "Resolved About State from ViewModel: $uiState" }
        }
        // UI відображає дані з uiState...
    }
}
```
**Пояснення:** Тут я оновив інтерфейс додатку. Тепер Composable-функція не знає, звідки беруться дані — вона просто підписується на `viewModel.uiState` за допомогою `collectAsState()` та реагує на будь-які зміни стану.

### 3. Налаштування Koin DI
**Що зроблено:** `⚠️ Не знайдено` у цій лабораторній роботі.
**Пояснення:** У `lab7` впровадження залежностей Koin ще відсутнє. Натомість ViewModel створюється напряму за допомогою стандартного фабричного конструктора `viewModel()` у Composable функції. Повноцінну інтеграцію Koin DI реалізовано у наступній роботі (`lab8`).

---

## 🔗 Додані залежності
| Бібліотека | Навіщо |
|-----------|--------|
| `androidx.lifecycle:lifecycle-viewmodel:2.8.0` | Підтримка ViewModel архітектури у спільному коді KMP. |
| `androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0` | Компоненти Compose для зв'язування ViewModel з UI циклом. |

---

## 🧩 Архітектурні рішення

**ViewModel + StateFlow:**
Дані про пристрій та додаток тепер відокремлені від UI-елементів. `AboutViewModel` зберігає стан у приватному `MutableStateFlow` та експонує його назовні як публічний незмінний `StateFlow`. UI підписується на нього у вигляді Compose `State` для автоматичного перерендерингу.

---

## ⚠️ Особливості та нюанси
Оскільки в цій лабораторній роботі ще немає DI контейнера, створення `AboutViewModel` відбувається за допомогою стандартного `viewModel()` хелпера. Оскільки у конструкторі `AboutViewModel` немає параметрів (усі дані захардкоджені всередині або викликаються синхронно через `getSystemInfo()`), такий підхід працює без кастомних фабрик.
