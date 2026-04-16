package ua.edu.chnu.kkn.beginningkotlinmultiplatform

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import beginningkotlinmultiplatform.composeapp.generated.resources.Res
import beginningkotlinmultiplatform.composeapp.generated.resources.find_meeting
import beginningkotlinmultiplatform.composeapp.generated.resources.world_clocks
import org.jetbrains.compose.resources.stringResource
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.shared_mobile.main.MainScreen

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(
                actionBarFun = {
                    TopAppBar(
                        title = {
                            when (it) {
                                0 -> Text(text = stringResource(Res.string.world_clocks))
                                else -> Text(text = stringResource(Res.string.find_meeting))
                            }
                        }
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
fun AppAndroidPreview() {
    MainScreen(
        actionBarFun = {
            TopAppBar(
                title = {
                    when (it) {
                        0 -> Text(text = stringResource(Res.string.world_clocks))
                        else -> Text(text = stringResource(Res.string.find_meeting))
                    }
                }
            )
        }
    )
}
