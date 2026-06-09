# Лабораторна робота №5 — Пояснення

> **Git гілка:** `feature/timezone-app`

---

## 📋 Що потрібно було сделать
- Розширити додаток таймзон підтримкою Desktop (JVM) та Web (WasmJs) платформ.
- Реалізувати expect/actual механізм для відображення діалогових вікон (`showDialog`), адаптованих до особливостей кожної платформи.
- На Desktop реалізувати можливість відкриття кількох незалежних операційних вікон додатку (`Window`) через `application{}`.
- Додати системне меню `MenuBar` та гарячі клавіші `KeyShortcut` для керування вікнами та діалогами на десктопі.
- Додати виправлення підтримки таймзон на Web платформах (JS/Wasm) через npm бібліотеку `@js-joda/timezone`.

---

## 📁 Структура проєкту (важливі файли)
composeApp/src/
├── commonMain/kotlin/ua/edu/chnu/kkn/beginningkotlinmultiplatform/
│   ├── DialogHelper.kt   ← expect функція showDialog та оголошення DialogController
│   └── App.kt   ← інтерфейс додатку, який приймає колбеки для меню десктопа
├── jvmMain/kotlin/ua/edu/chnu/kkn/beginningkotlinmultiplatform/
│   ├── main.kt   ← запуск десктоп додатку, підтримка декількох вікон та MenuBar
│   └── DialogHelper.jvm.kt   ← actual реалізація showDialog для Desktop через список вікон
├── webMain/kotlin/ua/edu/chnu/kkn/beginningkotlinmultiplatform/
│   ├── main.kt   ← точка входу для Web, ініціалізація CanvasBasedWindow
│   └── DialogHelper.web.kt   ← actual реалізація showDialog для Web
└── ...

---

## ✅ Виконані завдання

### 1. Expect/Actual діалоги для Desktop (showDialog)
**Що зроблено:** Реалізовано кросплатформне відображення діалогів. На десктопі замість стандартних Overlay-діалогів створюється окреме повноцінне OS вікно.
**Де знаходиться:** 
- `composeApp/src/commonMain/kotlin/ua/edu/chnu/kkn/beginningkotlinmultiplatform/DialogHelper.kt` (expect)
- `composeApp/src/jvmMain/kotlin/ua/edu/chnu/kkn/beginningkotlinmultiplatform/DialogHelper.jvm.kt` (actual)
**Ключовий фрагмент:**
```kotlin
actual fun showDialog(
    title: String,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    // ...
    val dialogData = DesktopDialogData(title, onDismiss) {
        CompositionLocalProvider(LocalDialogController provides controller) {
            content()
        }
    }
    activeDesktopDialogs.add(dialogData)
}
```
**Пояснення:** Тут я реалізував концепцію віконних діалогів для Desktop. Функція `showDialog` просто додає опис діалогу у список `activeDesktopDialogs`. Наш десктопний циклічний рендер бачить новий елемент у списку та відкриває для нього окреме вікно `Window`.

### 2. Запуск кількох OS вікон на десктопі (main)
**Що зроблено:** У точці входу десктоп-додатку реалізовано рендер масиву вікон, де користувач може динамічно додавати та закривати вікна.
**Де знаходиться:** `composeApp/src/jvmMain/kotlin/ua/edu/chnu/kkn/beginningkotlinmultiplatform/main.kt`
**Ключовий фрагмент:**
```kotlin
fun main() = application {
    val windows = remember { mutableStateListOf("Головне вікно") }
    // ...
    windows.toList().forEach { windowTitle ->
        Window(
            onCloseRequest = { windows.remove(windowTitle) },
            title = windowTitle
        ) {
            // ...
        }
    }
}
```
**Пояснення:** Тут я написав десктопний запуск додатку. Замість одного вікна ми крутимося у циклі по списку `windows`. Коли користувач створює нове вікно (наприклад через кнопку або меню), ми додаємо новий рядок у список `windows`, і Compose автоматично створює нове операційне вікно на екрані.

### 3. Системне меню та гарячі клавіші (MenuBar)
**Що зроблено:** До кожного вікна прикріплено меню `MenuBar` з підменю "Файл" та "Дії" та гарячими клавішами (Ctrl+N, Ctrl+W, Ctrl+Q, Ctrl+D).
**Де знаходиться:** `composeApp/src/jvmMain/kotlin/ua/edu/chnu/kkn/beginningkotlinmultiplatform/main.kt`
**Ключовий фрагмент:**
```kotlin
MenuBar {
    Menu("Файл") {
        Item(
            "Нове вікно",
            shortcut = KeyShortcut(Key.N, ctrl = true),
            onClick = { windows.add("Вікно ${windows.size + 1}") }
        )
    }
}
```
**Пояснення:** Тут я додав нативне десктопне меню зверху вікна. Воно прив'язане до комбінацій клавіш (наприклад, натискання `Ctrl + N` створює нове вікно, а `Ctrl + D` відкриває тестове діалогове вікно).

---

## 🔗 Додані залежності
| Бібліотека | Навіщо |
|-----------|--------|
| `npm("@js-joda/timezone", "2.25.1")` | Виправлення підтримки таймзон на Web (JS/WasmJs), оскільки браузерний JS двигун без цієї бази даних не може працювати зі списком світових часових зон. |

---

## 🧩 Архітектурні рішення

**Expect/Actual:**
Оголошено `expect fun showDialog(...)` в `commonMain`.
- На **Android/Web** реалізація показує стандартний Overlay поверх поточного екрана.
- На **Desktop (JVM)** реалізація додає опис діалогу в глобальний список `activeDesktopDialogs`, який рендериться у вигляді окремих вікон операційної системи (`Window`) в циклі `application {}`.

---

## ⚠️ Особливості та нюанси
Оскільки Compose Multiplatform підтримує Web (WasmJs), виникає проблема з бібліотекою `kotlinx-datetime` на клієнті, яка не може отримати список доступних часових зон. Для цього в конфігурацію залежностей `wasmJsMain` та `jsMain` додано npm-модуль `@js-joda/timezone`, який інтегрує базу даних таймзон прямо у збірку.
