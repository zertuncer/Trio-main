package app.trio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import app.trio.ui.home.HomeRootView
import app.trio.ui.theme.TrioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrioTheme {
                // Entry point is HomeRootView mirroring Trio iOS
                HomeRootView()
            }
        }
    }
}
