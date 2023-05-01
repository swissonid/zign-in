package github.swissonid.zignin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import github.swissonid.zignin.feature.userregistry.presenation.registration.RegistrationViewModel
import github.swissonid.zignin.feature.userregistry.presenation.registration.SmartRegistrationScreen
import github.swissonid.zignin.ui.theme.ZigninTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZigninTheme {
                val viewModel = hiltViewModel<RegistrationViewModel>()
                SmartRegistrationScreen(viewModel)
            }
        }
    }
}
