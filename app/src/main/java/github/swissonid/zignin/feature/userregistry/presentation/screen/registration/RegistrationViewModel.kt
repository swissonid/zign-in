package github.swissonid.zignin.feature.userregistry.presentation.screen.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import github.swissonid.zignin.feature.userregistry.domain.RegisterNewUserUseCase
import github.swissonid.zignin.feature.userregistry.domain.model.Birthday
import github.swissonid.zignin.feature.userregistry.domain.model.EMail
import github.swissonid.zignin.feature.userregistry.domain.model.Name
import github.swissonid.zignin.feature.userregistry.domain.model.NewUser
import github.swissonid.zignin.feature.userregistry.domain.model.validateBirthday
import github.swissonid.zignin.feature.userregistry.domain.model.validateEmail
import github.swissonid.zignin.feature.userregistry.domain.model.validateName
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.toLocalDate
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerNewUserUseCase: RegisterNewUserUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationUiState())


    val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()

    private fun validateForm(uiState: RegistrationUiState) {
        val formIsValid = uiState.name.isValid && uiState.email.isValid && uiState.birthday.isValid
        val updatedUiState = uiState.copy(isFormValid = formIsValid)
        _uiState.update { updatedUiState }
    }


    fun onNameChanges(nameValue: String) {
        val result = validateName(nameValue)
        val updatedName = _uiState.value.name.copy(
            value = nameValue,
            isDirty = true,
            isValid = result.isSuccess,
        )
        val updateUiState = _uiState.value.copy(name = updatedName)
        validateForm(updateUiState)
    }

    fun onEmailChanges(emailValue: String) {
        val result = validateEmail(emailValue)
        val updatedEmail = _uiState.value.email.copy(
            value = emailValue,
            isDirty = true,
            isValid = result.isSuccess,
        )
        val updateUiState = _uiState.value.copy(email = updatedEmail)
        validateForm(updateUiState)
    }

    fun onBirthdayChanges(birthdayValue: String) {
        val result = validateBirthday(birthdayValue)
        val updatedBirthday = _uiState.value.birthday.copy(
            value = birthdayValue,
            isDirty = true,
            isValid = result.isSuccess,
        )
        val updateUiState = _uiState.value.copy(birthday = updatedBirthday)
        validateForm(updateUiState)
    }

    fun onRegistration(onRegistrationDone: () -> Unit/* This would be better solved with flow or channel of events*/) {

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(2000)
            // This would be better in a use case
            val newUser = NewUser(
                name = Name(_uiState.value.name.value!!),
                email = EMail(_uiState.value.email.value!!),
                birthday = Birthday(_uiState.value.birthday.value!!.toLocalDate())
            )
            val registerResult = registerNewUserUseCase(newUser)
            if (registerResult.isFailure) {
                _uiState.update { it.copy(hasError = true, isLoading = false) }
            } else {
                _uiState.update { it.copy(isLoading = false) }
                delay(300)
                onRegistrationDone()
            }

        }
    }

}