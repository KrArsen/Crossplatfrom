# Лабораторна робота №3 — Пояснення

> **Git гілка:** `main`

---

## 📋 Що потрібно було зробити
- Створити кастомну Material3 тему (`AppTheme`) з індивідуальними палітрами та власним шрифтом (Audiowide).
- Інтегрувати бібліотеку навігації `navigation-compose` для переходів між екранами.
- Створити екрани демонстрації компонентів Compose:
  - `ButtonsScreen` (різні види кнопок: Filled, Outlined, Text, Elevated).
  - `CheckboxesScreen` (прапорці вибору).
  - `ChipsScreen` (короткі мітки/чипи).
  - `DatepickerDialogScreen` (діалог вибору дати).
  - `DialogScreen` (кастомний спливаючий діалог).
  - `DividerScreen` (розділювачі елементів).
  - `ProgressBarScreen` (індикатори завантаження — лінійний та круговий).
  - `RadioButtonsScreen` (перемикачі взаємовиключного вибору).
  - `SwitchScreen` (повзунки перемикання станів).
  - `TimepickerDialogScreen` (діалог вибору часу).
- Створити головне меню `MainScreen` з кнопками переходу на кожен з демонстраційних екранів.

---

## 📁 Структура проєкту (важливі файли)
composeApp/src/
├── commonMain/kotlin/ua/edu/chnu/kkn/beginningkotlinmultiplatform/
│   ├── App.kt   ← точка запуску UI додатку, виклик NavGraph
│   ├── navigation/
│   │   └── NavGraph.kt   ← опис маршрутів Screen та логіка перемикання екранів
│   └── ui/
│       ├── theme/
│       │   ├── Theme.kt   ← налаштування AppTheme та завантаження шрифту Audiowide
│       │   ├── Color.kt   ← опис палітри кольорів світлої/темної схем
│       │   └── Type.kt   ← конфігурація Typography для теми
│       └── screens/
│           ├── components/
│           │   └── ScreenScaffold.kt   ← спільний Scafflold з TopAppBar та кнопкою назад
│           ├── main/
│           │   └── MainScreen.kt   ← головний екран з кнопками навігації
│           ├── buttons/
│           │   └── ButtonsScreen.kt   ← демонстрація кнопок
│           └── ...   ← інші екрани компонентів (Checkboxes, Chips, Dialogs тощо)
└── ...

---

## ✅ Виконані завдання

### 1. Кастомне налаштування теми (Theme.kt)
**Що зроблено:** Реалізовано Material3 тему з підтримкою темного та світлого режимів та завантаженням власного шрифту Audiowide з ресурсів Compose.
**Де знаходиться:** `composeApp/src/commonMain/kotlin/ua/edu/chnu/kkn/beginningkotlinmultiplatform/ui/theme/Theme.kt`
**Ключовий фрагмент:**
```kotlin
@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colorScheme = if (darkTheme) darkScheme else lightScheme
    val audioWideFont = Font(Res.font.audiowide_regular)
    val appTypography = remember { getTypography(audioWideFont) }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = appTypography,
        content = content
    )
}
```
**Пояснення:** Тут я описав кастомну тему нашого додатку. Ми завантажуємо файл шрифту Audiowide через генератор ресурсів `Res.font.audiowide_regular`, створюємо на його основі типографіку додатку, та передаємо її разом зі світлою або темною схемою кольорів у системний `MaterialTheme`.

### 2. Навігаційне дерево (NavGraph.kt)
**Що зроблено:** Налаштовано роутинг додатку за допомогою `NavHost` та `rememberNavController()`.
**Де знаходиться:** `composeApp/src/commonMain/kotlin/ua/edu/chnu/kkn/beginningkotlinmultiplatform/navigation/NavGraph.kt`
**Ключовий фрагмент:**
```kotlin
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            MainScreen(onNavigate = { screen -> navController.navigate(screen.route) })
        }
        composable(Screen.Buttons.route) { ButtonsScreen(onBackClick = { navController.popBackStack() }) }
        // ... інші маршрути
    }
}
```
**Пояснення:** Цей файл керує всіма переходами між екранами. Я оголосив об'єкт `Screen` з текстовими маршрутами для кожного екрана, а `NavHost` пов'язує кожен маршрут із відповідним Composable-екраном та обробляє повернення назад через `navController.popBackStack()`.

### 3. Головне меню додатку (MainScreen.kt)
**Що зроблено:** Створено список кнопок для навігації до демонстраційних екранів у вигляді `LazyColumn`.
**Де знаходиться:** `composeApp/src/commonMain/kotlin/ua/edu/chnu/kkn/beginningkotlinmultiplatform/ui/screens/main/MainScreen.kt`
**Ключовий фрагмент:**
```kotlin
LazyColumn(
    modifier = Modifier.padding(padding),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
) {
    items(entries) { entry ->
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onNavigate(entry.second) }
        ) {
            Text(entry.first)
        }
    }
}
```
**Пояснення:** Тут я створив стартову сторінку додатку. Ми виводимо у вертикальному списку `LazyColumn` кнопки для кожного компонента. При натисканні на будь-яку з них викликається колбек `onNavigate`, що запускає перехід у нашому `NavGraph`.

---

## 🔗 Додані залежності
| Бібліотека | Навіщо |
|-----------|--------|
| `androidx.navigation:navigation-compose:2.7.7` | Забезпечення роботи декларативної навігації Compose Multiplatform. |

---

## 🧩 Архітектурні рішення

**Спільна розмітка екрану (ScreenScaffold):**
Для уникнення копіювання однакового коду заголовків на кожному екрані було створено загальний компонент `ScreenScaffold`. Він містить системний `Scaffold` з `TopAppBar`, який автоматично показує назву поточного екрана та кнопку "Назад", якщо користувач зайшов у підменю.

---

## ⚠️ Особливості та нюанси
Для деяких складніших діалогів (наприклад, `DatePicker` та `TimePicker`) було розроблено екрани-обгортки зі станами видимості діалогу, що дозволяє показувати нативні системні віконця Compose поверх контенту і коректно отримувати вибрані значення користувачем.
