package github.swissonid.zignin

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import github.swissonid.zignin.feature.userregistry.presenation.screen.confirmation.SmartConfirmationScreen
import github.swissonid.zignin.feature.userregistry.presenation.screen.registration.SmartRegistrationScreen

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "registration",
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable("registration") {
            SmartRegistrationScreen(
                registrationViewModel = hiltViewModel(),
                onRegistrationDone = {
                    navController.navigate("confirmation")
                }
            )
        }
        composable("confirmation") {
            SmartConfirmationScreen(
                confirmationViewModel = hiltViewModel()

            )
        }
    }

}