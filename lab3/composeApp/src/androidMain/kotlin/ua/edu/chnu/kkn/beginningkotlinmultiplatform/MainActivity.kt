package ua.edu.chnu.kkn.beginningkotlinmultiplatform

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.navigation.NavGraph
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                NavGraph()
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    AppTheme {
        NavGraph()
    }
}