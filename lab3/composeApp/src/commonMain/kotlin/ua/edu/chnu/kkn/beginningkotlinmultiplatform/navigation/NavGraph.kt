package ua.edu.chnu.kkn.beginningkotlinmultiplatform.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.buttons.ButtonsScreen
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.checkboxes.CheckboxesScreen
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.chips.ChipsScreen
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.datepickerdialog.DatepickerDialogScreen
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.dialog.DialogScreen
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.divider.DividerScreen
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.main.MainScreen
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.progressbar.ProgressBarScreen
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.radiobuttons.RadioButtonsScreen
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.switches.SwitchScreen
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.timepickerdialog.TimepickerDialogScreen

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Buttons : Screen("buttons")
    data object Checkboxes : Screen("checkboxes")
    data object Chips : Screen("chips")
    data object DatepickerDialog : Screen("datepicker_dialog")
    data object Dialog : Screen("dialog")
    data object Divider : Screen("divider")
    data object ProgressBar : Screen("progress_bar")
    data object RadioButtons : Screen("radio_buttons")
    data object Switch : Screen("switch")
    data object TimepickerDialog : Screen("timepicker_dialog")
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            MainScreen(
                onNavigate = { screen ->
                    navController.navigate(screen.route)
                }
            )
        }
        composable(Screen.Buttons.route) { ButtonsScreen(onBackClick = { navController.popBackStack() }) }
        composable(Screen.Checkboxes.route) { CheckboxesScreen(onBackClick = { navController.popBackStack() }) }
        composable(Screen.Chips.route) { ChipsScreen(onBackClick = { navController.popBackStack() }) }
        composable(Screen.DatepickerDialog.route) { DatepickerDialogScreen(onBackClick = { navController.popBackStack() }) }
        composable(Screen.Dialog.route) { DialogScreen(onBackClick = { navController.popBackStack() }) }
        composable(Screen.Divider.route) { DividerScreen(onBackClick = { navController.popBackStack() }) }
        composable(Screen.ProgressBar.route) { ProgressBarScreen(onBackClick = { navController.popBackStack() }) }
        composable(Screen.RadioButtons.route) { RadioButtonsScreen(onBackClick = { navController.popBackStack() }) }
        composable(Screen.Switch.route) { SwitchScreen(onBackClick = { navController.popBackStack() }) }
        composable(Screen.TimepickerDialog.route) { TimepickerDialogScreen(onBackClick = { navController.popBackStack() }) }
    }
}
