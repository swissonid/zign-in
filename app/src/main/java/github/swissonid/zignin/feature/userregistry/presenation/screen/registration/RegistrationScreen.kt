@file:OptIn(ExperimentalMaterial3Api::class)

package github.swissonid.zignin.feature.userregistry.presenation.screen.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import github.swissonid.zignin.R
import github.swissonid.zignin.ui.theme.ZigninTheme

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

@OptIn(ExperimentalMaterial3Api::class)
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
                .fillMaxSize()
                .padding(it)
                .padding(
                    top = 24.dp,
                    start = 16.dp,
                    end = 16.dp,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            val spacerModifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
            Column {
                Text(
                    text = stringResource(id = R.string.registration_screen__title), style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 48.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                )
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
            }

            Column {
                ElevatedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = 16.dp
                        ),
                    enabled = uiState.isFormValid && uiState.isLoading.not(),
                    onClick = onRegistration,
                    shape = RoundedCornerShape(2.dp),
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

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NameField(
    value: String?,
    onNameChanges: (String) -> Unit,
    isError: Boolean = false,
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next
) {
    var nameHasFocus by remember { mutableStateOf(false) }
    val showError = isError && !nameHasFocus
    TextField(
        value = value ?: "",
        enabled = enabled,
        onValueChange = onNameChanges,
        maxLines = 1,
        keyboardOptions = KeyboardOptions.Default.copy(
            autoCorrect = false,
            imeAction = imeAction
        ),
        trailingIcon = {
            if (isError) Icon(Icons.Sharp.Warning, contentDescription = null)
        },
        isError = showError,
        label = { Text(stringResource(id = R.string.registration_screen__label_name)) },
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { nameHasFocus = it.hasFocus }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
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
        trailingIcon = {
            if (isError) Icon(Icons.Sharp.Warning, contentDescription = null)
        },
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

@OptIn(ExperimentalMaterial3Api::class)
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
        trailingIcon = {
            if (isError) Icon(Icons.Sharp.Warning, contentDescription = null)
        },
        label = { Text(stringResource(id = R.string.registration_screen__label_birthday)) },
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                birthdayHasFocus = focusState.hasFocus
            }
    )
}

@Preview
@Composable
fun RegistrationScreenPreview() {
    ZigninTheme {
        RegistrationScreen(onRegistration = {})
    }
}
