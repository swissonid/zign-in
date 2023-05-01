@file:OptIn(ExperimentalMaterial3Api::class)

package github.swissonid.zignin.feature.userregistry.presenation.screen.registration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
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
import github.swissonid.zignin.R

@Composable
fun SmartRegistrationScreen(
    registrationViewModel: RegistrationViewModel,
    onRegistrationDone: () -> Unit = {}
) {
    val registrationUiState by registrationViewModel.uiState.collectAsState()

    RegistrationScreen(
        uiState = registrationUiState,
        onNameChanges = { registrationViewModel.onNameChanges(it) },
        onEmailChanges = { registrationViewModel.onEmailChanges(it) },
        onBirthdayChanges = { registrationViewModel.onBirthdayChanges(it) },
        onRegistration = { registrationViewModel.onRegistration(onRegistrationDone) },
    )
}

@Composable
fun RegistrationScreen(
    onNameChanges: (String) -> Unit = {},
    onEmailChanges: (String) -> Unit = {},
    onBirthdayChanges: (String) -> Unit = {},
    onRegistration: () -> Unit,
    uiState: RegistrationUiState = RegistrationUiState()
) {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val spacerModifier = Modifier
                .fillMaxWidth()
                .height(16.dp)

            NameField(
                value = uiState.name.value,
                enabled = uiState.isLoading.not(),
                isError = uiState.name.isValid.not() && uiState.name.isDirty,
                onNameChanges = onNameChanges
            )
            Spacer(modifier = spacerModifier)
            EmailField(
                value = uiState.email.value,
                enabled = uiState.isLoading.not(),
                isError = uiState.email.isValid.not() && uiState.email.isDirty,
                onEmailChanges = onEmailChanges
            )
            Spacer(modifier = spacerModifier)
            BirthdayField(
                value = uiState.birthday.value,
                enabled = uiState.isLoading.not(),
                isError = uiState.birthday.isValid.not() && uiState.birthday.isDirty,
                onBirthdayChanges = onBirthdayChanges
            )
            Spacer(modifier = spacerModifier)

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.isFormValid && uiState.isLoading.not(),
                onClick = onRegistration
            ) {

                Text(stringResource(id = R.string.registration_screen__action_button_text))
                if (uiState.isLoading) {
                    Spacer(modifier = Modifier.padding(8.dp))
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                }

            }
            Spacer(modifier = spacerModifier)

            if (uiState.hasError) {
                Text(stringResource(id = R.string.error__general))
            }
        }

    }

}

@Composable
private fun NameField(
    value: String?,
    onNameChanges: (String) -> Unit,
    isError: Boolean = false,
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next
) {
    var nameHasFocus by remember { mutableStateOf(false) }
    TextField(
        value = value ?: "",
        enabled = enabled,
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
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next
) {
    var emailHasFocus by remember { mutableStateOf(false) }
    val showError = isError && !emailHasFocus
    TextField(
        value = value ?: "",
        onValueChange = onEmailChanges,
        maxLines = 1,
        enabled = enabled,
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
    enabled: Boolean = true,
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
        enabled = enabled,
        label = { Text(stringResource(id = R.string.registration_screen__label_birthday)) },
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                birthdayHasFocus = focusState.hasFocus
            }
    )
}
