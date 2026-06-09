# Лабораторна робота №8 — Пояснення

> **Git гілка:** `feature/system-info-app`

---

## 📋 Що потрібно було зробити
- Впровадити архітектурний паттерн Dependency Injection за допомогою бібліотеки **Koin**.
- Виділити джерело даних в окремий клас репозиторію `AboutRepository` для отримання статичної інформації про додаток.
- Звільнити `AboutViewModel` від захардкодженних значень шляхом передачі `AboutRepository` через конструктор.
- Створити кросплатформні Koin-модулі та налаштувати ініціалізацію `startKoin` для Android та Desktop платформ.
- Реалізувати ін'єкцію ViewModel в UI через кросплатформний `expect`/`actual` хелпер, вирішивши проблему несумісності Android-специфічного `koinViewModel()`.

---

## 📁 Структура проєкту (важливі файли)
composeApp/src/
├── commonMain/kotlin/com/example/lab6/
│   ├── Platform.kt   ← expect функція getAboutViewModel
│   ├── App.kt   ← UI, який використовує getAboutViewModel() за замовчуванням
│   ├── AboutViewModel.kt   ← ViewModel, що приймає AboutRepository
│   ├── di/
│   │   └── AppModule.kt   ← опис Koin модулів (репозиторій та фабрика ViewModel)
│   └── repository/
│       └── AboutRepository.kt   ← інтерфейс та реалізація AboutRepository
├── androidMain/kotlin/com/example/lab6/
│   ├── App.kt   ← Android Application клас з ініціалізацією startKoin
│   └── Platform.android.kt   ← actual реалізація getAboutViewModel через koinViewModel()
├── jvmMain/kotlin/com/example/lab6/
│   ├── main.kt   ← точка входу Desktop, ініціалізація startKoin
│   └── Platform.jvm.kt   ← actual реалізація getAboutViewModel через GlobalContext
└── ...

---

## ✅ Виконані завдання

### 1. Виділення репозиторію (AboutRepository)
**Що зроблено:** Створено інтерфейс та реалізацію репозиторію для збереження та повернення описів додатку.
**Де знаходиться:** `composeApp/src/commonMain/kotlin/com/example/lab6/repository/AboutRepository.kt`
**Ключовий фрагмент:**
```kotlin
interface AboutRepository {
    fun getAppName(): String
    fun getVersion(): String
    fun getAuthor(): String
    fun getDescription(): String
}
```
**Пояснення:** Для відділення бізнес-даних від ViewModel я створив репозиторій. Тепер метадані додатку лежать у чистому класі `AboutRepositoryImpl`, а не захардкоджені всередині життєвого циклу ViewModel.

### 2. Constructor Injection у ViewModel
**Що зроблено:** `AboutViewModel` більше не створює об'єкти самостійно, а очікує їх отримання ззовні.
**Де знаходиться:** `composeApp/src/commonMain/kotlin/com/example/lab6/AboutViewModel.kt`
**Ключовий фрагмент:**
```kotlin
class AboutViewModel(
    private val repository: AboutRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AboutUiState(
        appName = repository.getAppName(),
        version = repository.getVersion(),
        author = repository.getAuthor(),
        description = repository.getDescription(),
        // ...
    ))
}
```
**Пояснення:** Я переписав ViewModel так, щоб вона приймала `AboutRepository` у конструкторі. Це робить код значно гнучкішим та дозволяє легко тестувати ViewModel, підміняючи репозиторій мок-об'єктами.

### 3. Опис Koin модуля (AppModule.kt)
**Що зроблено:** Описано правила створення залежностей у спільному модулі `appModule`.
**Де знаходиться:** `composeApp/src/commonMain/kotlin/com/example/lab6/di/AppModule.kt`
**Ключовий фрагмент:**
```kotlin
val appModule = module {
    single<AboutRepository> { AboutRepositoryImpl() }
    factory { AboutViewModel(get()) }
}
```
**Пояснення:** Це ядро нашого DI. Я зареєстрував `AboutRepositoryImpl` як синглтон, а `AboutViewModel` — як фабрику. Коли Koin створює ViewModel, він автоматично підставляє зареєстрований синглтон репозиторію за допомогою виклику `get()`.

### 4. Кросплатформне отримання ViewModel
**Що зроблено:** Через expect/actual реалізовано отримання ViewModel для кожної платформи окремо, бо Android Compose та Desktop використовують різні механізми.
**Де знаходиться:**
- `Platform.kt` (expect)
- `Platform.android.kt` (actual Android)
- `Platform.jvm.kt` (actual Desktop)
**Ключовий фрагмент (JVM):**
```kotlin
@Composable
actual fun getAboutViewModel(): AboutViewModel = remember {
    GlobalContext.get().get()
}
```
**Пояснення:** Це важливе архітектурне рішення. Бібліотека `koin-androidx-compose` працює тільки на Android. Щоб спільний інтерфейс у `commonMain` скомпілювався для Desktop без помилок, я створив expect-функцію `getAboutViewModel()`. Android використовує нативний `koinViewModel()`, а Desktop — вручну дістає зареєстрований інстанс з `GlobalContext`.

---

## 🔗 Додані залежності
| Бібліотека | Навіщо |
|-----------|--------|
| `io.insert-koin:koin-core:3.5.3` | Базовий кросплатформний DI контейнер. |
| `io.insert-koin:koin-android:3.5.3` | Інтеграція Koin з Android (передача Context). |
| `io.insert-koin:koin-androidx-compose:3.5.3` | Готові Compose-хелпери (`koinViewModel`) для Android. |

---

## 🧩 Архітектурні рішення

**Koin DI:**
Всі залежності реєструються у загальному Koin-модулі `appModule`. Ініціалізація `startKoin` запускається на старті кожної платформи. ViewModel інжектується через expect/actual хелпер `getAboutViewModel()`, що повністю ховає специфічні для кожної ОС механізми під капот платформного шару.

---

## ⚠️ Особливості та нюанси
Оскільки ми не можемо напряму викликати `koinViewModel()` у `commonMain/App.kt` без додавання залежності `koin-androidx-compose` (яка завалить збірку під Desktop JVM), ми використали expect/actual обгортку. Це дозволяє зберегти чистий декларативний стиль нашого спільного UI без зайвого дублювання екранів.
