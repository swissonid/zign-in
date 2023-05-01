@file:OptIn(ExperimentalMaterial3Api::class)

package github.swissonid.zignin.feature.userregistry.presenation.registration

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import github.swissonid.zignin.R

@Composable
fun SmartRegistrationScreen(
    registrationViewModel: RegistrationViewModel = viewModel()
) {
    val registrationUiState by registrationViewModel.uiState.collectAsState()
    RegistrationScreen(
        uiState = registrationUiState,
        onNameChanges = { registrationViewModel.onNameChanges(it) },
        onEmailChanges = { registrationViewModel.onEmailChanges(it) },
        onBirthdayChanges = { registrationViewModel.onBirthdayChanges(it) },

        )
}

@Composable
fun RegistrationScreen(
    onNameChanges: (String) -> Unit = {},
    onEmailChanges: (String) -> Unit = {},
    onBirthdayChanges: (String) -> Unit = {},
    uiState: RegistrationUiState = RegistrationUiState()
) {
    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val spacerModifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)

                NameField(
                    value = uiState.name.value,
                    isError = uiState.name.isValid.not() && uiState.name.isDirty,
                    onNameChanges = onNameChanges
                )
                Spacer(modifier = spacerModifier)
                EmailField(
                    value = uiState.email.value,
                    isError = uiState.email.isValid.not() && uiState.email.isDirty,
                    onEmailChanges = onEmailChanges
                )
                Spacer(modifier = spacerModifier)
                BirthdayField(
                    value = uiState.birthday.value,
                    isError = uiState.birthday.isValid.not() && uiState.birthday.isDirty,
                    onBirthdayChanges = onBirthdayChanges
                )
                Spacer(modifier = spacerModifier)

                OutlinedButton(onClick = { /*TODO*/ }) {
                    Text("Registrieren")
                }

            }
        }


    }

}

@Composable
private fun NameField(
    value: String?,
    onNameChanges: (String) -> Unit,
    isError: Boolean = false,
    imeAction: ImeAction = ImeAction.Next
) {
    var nameHasFocus by remember { mutableStateOf(false) }
    TextField(
        value = value ?: "",
        onValueChange = onNameChanges,
        maxLines = 1,
        keyboardOptions = KeyboardOptions.Default.copy(
            autoCorrect = false,
            imeAction = imeAction
        ),
        isError = isError,
        label = { Text(stringResource(id = R.string.registration_screen__label_name)) },
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { nameHasFocus = it.hasFocus }
    )
}

@Composable
private fun EmailField(
    value: String?,
    onEmailChanges: (String) -> Unit,
    isError: Boolean = false,
    imeAction: ImeAction = ImeAction.Next
) {
    var emailHasFocus by remember { mutableStateOf(false) }
    val showError = isError && !emailHasFocus
    TextField(
        value = value ?: "",
        onValueChange = onEmailChanges,
        maxLines = 1,
        isError = showError,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email,
            imeAction = imeAction
        ),
        label = { Text(stringResource(id = R.string.registration_screen__label_email)) },
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged {
                emailHasFocus = it.hasFocus
            }
    )
}

@Composable
private fun BirthdayField(
    value: String?,
    onBirthdayChanges: (String) -> Unit,
    isError: Boolean = false,
    imeAction: ImeAction = ImeAction.Done
) {
    var birthdayHasFocus by remember { mutableStateOf(false) }
    TextField(
        value = value ?: "",
        maxLines = 1,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction
        ),
        isError = isError && !birthdayHasFocus,
        supportingText = { if (birthdayHasFocus) Text("yyyy-mm-dd") },
        onValueChange = onBirthdayChanges,
        label = { Text(stringResource(id = R.string.registration_screen__label_birthday)) },
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                birthdayHasFocus = focusState.hasFocus
            }
    )
}
