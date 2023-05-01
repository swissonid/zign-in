@file:OptIn(ExperimentalMaterial3Api::class)

package github.swissonid.zignin.feature.userregistry.presenation.screen.confirmation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import github.swissonid.zignin.R
import github.swissonid.zignin.feature.userregistry.domain.model.Birthday
import github.swissonid.zignin.feature.userregistry.domain.model.EMail
import github.swissonid.zignin.feature.userregistry.domain.model.Name
import github.swissonid.zignin.feature.userregistry.domain.model.RegisteredUser
import github.swissonid.zignin.feature.userregistry.domain.model.UserId
import github.swissonid.zignin.ui.theme.ZigninTheme
import kotlinx.datetime.toLocalDate

@Composable
fun SmartConfirmationScreen(
    confirmationViewModel: ConfirmationViewModel,
) {
    val confirmationUiState by confirmationViewModel.uiState.collectAsState()
    ConfirmationScreen(
        uiState = confirmationUiState
    )
}

@Composable
fun ConfirmationScreen(
    uiState: ConfirmationUiState = ConfirmationUiState(),
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.confirmation_screen__title)) }
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(stringResource(id = R.string.confirmation_screen_thank_you))
            if (uiState.user != null) {
                Text("${uiState.user.name}")
                Text("${uiState.user.email}")
                Text("${uiState.user.birthday}")
            } else {
                Text(stringResource(id = R.string.error__general))
            }
        }
    }
}


@Preview
@Composable
fun ConfirmationScreenPreview() {
    ZigninTheme {
        ConfirmationScreen(
            uiState = ConfirmationUiState(
                user = RegisteredUser(
                    id = UserId("2387432"),
                    name = Name("Test Name"),
                    birthday = Birthday("1985-11-01".toLocalDate()),
                    email = EMail("test@testing.com")
                )
            )
        )
    }
}