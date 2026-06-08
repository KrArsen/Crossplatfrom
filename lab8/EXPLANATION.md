# Лабораторна робота №8: Впровадження Koin DI в Kotlin Multiplatform (KMP)

У цій лабораторній роботі було успішно інтегровано фреймворк впровадження залежностей (Dependency Injection) **Koin** у кросплатформний проєкт KMP.

## Зміни та архітектура

### 1. Додавання залежностей (Koin)
У файлі [gradle/libs.versions.toml](file:///D:/UNIVERCITY/AndroidStudioProjects/lab8/gradle/libs.versions.toml) було оголошено версії та бібліотеки Koin:
- `koin-core` для спільного коду (`commonMain`).
- `koin-android` та `koin-androidx-compose` для Android-специфічного коду.

У файлі [composeApp/build.gradle.kts](file:///D:/UNIVERCITY/AndroidStudioProjects/lab8/composeApp/build.gradle.kts) ці залежності підключені до відповідних source set-ів.

### 2. Створення джерела даних та репозиторію
Створено інтерфейс [AboutRepository](file:///D:/UNIVERCITY/AndroidStudioProjects/lab8/composeApp/src/commonMain/kotlin/com/example/lab6/repository/AboutRepository.kt) та його реалізацію `AboutRepositoryImpl`, які містять статичні метадані додатку:
- Назва додатку (`Diagnostics App`)
- Версія (`1.0.0`)
- Автор (`Student`)
- Опис додатку

### 3. Рефакторинг ViewModel
Клас [AboutViewModel](file:///D:/UNIVERCITY/AndroidStudioProjects/lab8/composeApp/src/commonMain/kotlin/com/example/lab6/AboutViewModel.kt) тепер не має захардкоджених даних про додаток, а приймає `AboutRepository` через конструктор:
```kotlin
class AboutViewModel(
    private val repository: AboutRepository
) : ViewModel() { ... }
```

### 4. Конфігурація Koin модулів (DI Container)
Створено загальний модуль [AppModule.kt](file:///D:/UNIVERCITY/AndroidStudioProjects/lab8/composeApp/src/commonMain/kotlin/com/example/lab6/di/AppModule.kt) у `commonMain`:
```kotlin
val appModule = module {
    single<AboutRepository> { AboutRepositoryImpl() }
    factory { AboutViewModel(get()) }
}
```

### 5. Кросплатформне впровадження ViewModel
Оскільки `koinViewModel()` від `koin-androidx-compose` працює лише на Android і ламає компіляцію для Desktop (JVM), було використано механізм `expect`/`actual`:

- У [Platform.kt](file:///D:/UNIVERCITY/AndroidStudioProjects/lab8/composeApp/src/commonMain/kotlin/com/example/lab6/Platform.kt) (`commonMain`):
  ```kotlin
  @Composable
  expect fun getAboutViewModel(): AboutViewModel
  ```
- У [Platform.android.kt](file:///D:/UNIVERCITY/AndroidStudioProjects/lab8/composeApp/src/androidMain/kotlin/com/example/lab6/Platform.android.kt) (`androidMain`):
  ```kotlin
  @Composable
  actual fun getAboutViewModel(): AboutViewModel = koinViewModel()
  ```
- У [Platform.jvm.kt](file:///D:/UNIVERCITY/AndroidStudioProjects/lab8/composeApp/src/jvmMain/kotlin/com/example/lab6/Platform.jvm.kt) (`jvmMain`):
  ```kotlin
  @Composable
  actual fun getAboutViewModel(): AboutViewModel = remember { GlobalContext.get().get() }
  ```

### 6. Ініціалізація Koin
- **Android**: Koin запускається у кастомному класі [App.kt](file:///D:/UNIVERCITY/AndroidStudioProjects/lab8/composeApp/src/androidMain/kotlin/com/example/lab6/App.kt), який зареєстрований в [AndroidManifest.xml](file:///D:/UNIVERCITY/AndroidStudioProjects/lab8/composeApp/src/androidMain/AndroidManifest.xml).
- **Desktop (JVM)**: Koin ініціалізується безпосередньо перед рендером вікна у функції `main()` у [main.kt](file:///D:/UNIVERCITY/AndroidStudioProjects/lab8/composeApp/src/jvmMain/kotlin/com/example/lab6/main.kt).

### 7. Підключення до UI
У файлі [App.kt](file:///D:/UNIVERCITY/AndroidStudioProjects/lab8/composeApp/src/commonMain/kotlin/com/example/lab6/App.kt) компонент `App` використовує кросплатформний інжектор за замовчуванням:
```kotlin
@Composable
fun App(viewModel: AboutViewModel = getAboutViewModel()) { ... }
```

---
*Лабораторна робота №8 повністю завершена та успішно компілюється під Android та JVM/Desktop.*
