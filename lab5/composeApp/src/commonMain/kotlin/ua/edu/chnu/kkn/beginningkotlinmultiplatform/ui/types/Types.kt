package ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.types

import androidx.compose.runtime.Composable

typealias onAddType =  (List<String>) -> Unit

typealias onDismissType =  () -> Unit

typealias composeFun =  @Composable () -> Unit

typealias topBarFun =  @Composable (Int) -> Unit

@Composable
fun EmptyComposable() {
}
