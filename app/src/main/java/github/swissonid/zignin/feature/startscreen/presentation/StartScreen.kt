package github.swissonid.zignin.feature.startscreen.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import github.swissonid.zignin.navigation.Navigation

@Composable
fun StartScreen(
    navigationViewModel: StartScreenViewModel = hiltViewModel(),
) {
    val navigationUiState by navigationViewModel.uiState.collectAsState()
    val startDestination = navigationUiState.startScreen

    if (startDestination == null) {
        FullScreenLoading()
    } else {
        Navigation(startDestination = startDestination)
    }
}


@Composable
private fun FullScreenLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}