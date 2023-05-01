package github.swissonid.zignin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import github.swissonid.zignin.feature.userregistry.presenation.registration.SmartRegistrationScreen
import github.swissonid.zignin.ui.theme.ZigninTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZigninTheme {
                SmartRegistrationScreen()
            }
        }
    }
}
