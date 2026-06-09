# Лабораторна робота №4 — Пояснення

> **Git гілка:** `feature/timezone-app`

---

## 📋 Що потрібно було зробити
- Створити мобільний інтерфейс для додатку таймзон з використанням вкладкового інтерфейсу (Bottom Navigation / NavigationBar).
- Реалізувати екран списку таймзон `TimeZonePage` (відображення поточної зони користувача, список вибраних зон у вигляді карток `TimeZoneCard`, та FAB-кнопка для додавання нової таймзони).
- Реалізувати екран пошуку найкращого часу зустрічі `FindMeetingPage` (два числових селектори `NumberPicker` для старту та кінця проміжку зустрічі та запуск пошуку відповідних годин).
- Створити діалоги: `AddTimeZoneDialog` (для додавання таймзони) та `MeetingDialog` (з результатами пошуку зустрічі).
- Інтегрувати логіку `TimeZoneHelper` з другої лабораторної роботи для обчислення часу та пошуку.

---

## 📁 Структура проєкту (важливі файли)
composeApp/src/
├── commonMain/kotlin/ua/edu/chnu/kkn/beginningkotlinmultiplatform/
│   ├── App.kt   ← точка збирання інтерфейсу додатку
│   ├── data/timezones/
│   │   ├── TimeZoneHelper.kt   ← інтерфейс помічника з часовими зонами
│   │   └── TimeZoneHelperImpl.kt   ← бізнес-логіка розрахунків часу
│   ├── dialogs/
│   │   ├── SelectTimeZoneDialog.kt   ← загальний діалог вибору таймзони
│   │   └── MeetingResultDialog.kt   ← загальний діалог результатів зустрічі
│   ├── ui/
│   │   ├── shared_mobile/main/
│   │   │   ├── MainScreen.kt   ← екран з bottom bar та перемиканням сторінок
│   │   │   ├── TimeZonePage.kt   ← сторінка списку часових зон
│   │   │   └── FindMeetingPage.kt   ← сторінка пошуку оптимального часу
│   │   ├── timezones/
│   │   │   └── TimeZoneCard.kt   ← картка окремої таймзони
│   │   └── shared/components/
│   │       ├── NumberPicker.kt   ← компонент вибору годин
│   │       └── AnimatedSwipeDismiss.kt   ← анімація видалення свайпом
└── ...

---

## ✅ Виконані завдання

### 1. Екран з навігацією (MainScreen)
**Що зроблено:** Створено спільний контейнер `MainScreen`, який керує перемиканням вкладок "Timezones" та "Find Time" за допомогою `NavigationBar` та `Scaffold`.
**Де знаходиться:** `composeApp/src/commonMain/kotlin/ua/edu/chnu/kkn/beginningkotlinmultiplatform/ui/shared_mobile/main/MainScreen.kt`
**Ключовий фрагмент:**
```kotlin
bottomBar = {
    NavigationBar {
        bottomNavigationItems.forEachIndexed { index, bottomNavigationItem ->
            NavigationBarItem(
                label = { Text(bottomNavigationItem.route) },
                icon = { Icon(bottomNavigationItem.icon, contentDescription = null) },
                selected = selectedIndex.intValue == index,
                onClick = { selectedIndex.intValue = index }
            )
        }
    }
}
```
**Пояснення:** Тут я створив основний скелет нашого мобільного екрана. У ньому я використав `NavigationBar` для перемикання між вкладками, а стан `selectedIndex` вказує Compose, яку саме сторінку показувати у тілі `Scaffold` (`TimeZonesPage` або `FindMeetingPage`).

### 2. Екран таймзон та видалення свайпом (TimeZonePage)
**Що зроблено:** Відображення списку обраних таймзон, де кожен елемент обгорнутий в `AnimatedSwipeDismiss` для видалення за допомогою свайпу вліво/вправо.
**Де знаходиться:** `composeApp/src/commonMain/kotlin/ua/edu/chnu/kkn/beginningkotlinmultiplatform/ui/shared_mobile/main/TimeZonePage.kt`
**Ключовий фрагмент:**
```kotlin
LazyColumn(state = listState) {
    items(timezoneStrings, key = { it }) { timezone ->
        AnimatedSwipeDismiss(
            item = timezone,
            background = { /* Червоний фон з іконкою кошика */ },
            content = { TimeZoneCard(timezone = timezone) },
            onDismiss = { timezoneStrings.remove(timezone) }
        )
    }
}
```
**Пояснення:** Цей екран відповідає за виведення списку доданих користувачем таймзон. Щоб зробити інтерфейс сучасним та зручним, я інтегрував `AnimatedSwipeDismiss`, що дозволяє легко видаляти таймзону зі списку простим свайпом вбік.

### 3. Екран пошуку зустрічі (FindMeetingPage)
**Що зроблено:** Користувач за допомогою кастомного `NumberPicker` обирає початок та кінець робочого дня, відмічає часові зони учасників та запускає пошук сумісних годин.
**Де знаходиться:** `composeApp/src/commonMain/kotlin/ua/edu/chnu/kkn/beginningkotlinmultiplatform/ui/shared_mobile/main/FindMeetingPage.kt`
**Ключовий фрагмент:**
```kotlin
OutlinedButton(
    onClick = {
        meetingHours.clear()
        meetingHours.addAll(
            timezoneHelper.search(
                startTime.intValue,
                endTime.intValue,
                getSelectedTimeZones(timezoneStrings, selectedTimeZones)
            )
        )
        showMeetingDialog.value = true
    }) {
    Text("Search")
}
```
**Пояснення:** Тут я реалізував логіку підбору часу для зустрічі. Ми беремо вибраний інтервал годин за допомогою `NumberPicker`, передаємо список часових зон учасників у наш `timezoneHelper.search(...)` з другої лабораторної, а знайдені години показуємо у `MeetingDialog`.

---

## 🔗 Додані залежності
| Бібліотека | Навіщо |
|-----------|--------|
| `org.jetbrains.kotlinx:kotlinx-datetime:0.6.0` | Розрахунки часу та отримання списку доступних часових зон. |
| `co.touchlab:kermit:2.1.0` | Логування проміжних результатів розрахунків часу. |

---

## 🧩 Архітектурні рішення

**Використання TimeZoneHelper:**
Усі розрахунки часу, форматування дат та пошук годин делеговані класу `TimeZoneHelperImpl`. Ми створюємо його екземпляр безпосередньо у відповідних Composable-компонентах (наприклад, у `TimeZonePage` для виклику `timezoneHelper.getTime(timezone)`) для розділення UI та логіки обчислень.

---

## ⚠️ Особливості та нюанси
Для зручного вводу годин без стандартного громіздкого TimePicker було написано компактний компонент `NumberPicker`, який дозволяє крутити години вгору/вниз у межах від 0 до 23 за допомогою двох кнопок стрілочок.
