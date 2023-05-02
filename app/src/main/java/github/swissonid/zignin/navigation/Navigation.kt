package github.swissonid.zignin.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import github.swissonid.zignin.feature.userregistry.presentation.screen.confirmation.SmartConfirmationScreen
import github.swissonid.zignin.feature.userregistry.presentation.screen.registration.SmartRegistrationScreen

// This should be done more type safe maybe with a sealed class
class Routes {
    companion object {
        const val confirmationScreen = "confirmation"
        const val registrationScreen = "registration"
    }
}

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.registrationScreen,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(Routes.registrationScreen) {
            SmartRegistrationScreen(
                registrationViewModel = hiltViewModel(),
                onRegistrationDone = {
                    navController.navigate(Routes.confirmationScreen)
                }
            )
        }
        composable(Routes.confirmationScreen) {
            SmartConfirmationScreen(
                confirmationViewModel = hiltViewModel()
            )
        }
    }

}
