package github.swissonid.zignin.feature.userregistry.presenation.registration

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import github.swissonid.zignin.feature.userregistry.domain.GetCurrentUserUseCase
import github.swissonid.zignin.feature.userregistry.domain.RegisterNewUserUseCase
import github.swissonid.zignin.feature.userregistry.domain.model.validateBirthday
import github.swissonid.zignin.feature.userregistry.domain.model.validateEmail
import github.swissonid.zignin.feature.userregistry.domain.model.validateName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val registerNewUserUseCase: RegisterNewUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationUiState())

    val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()

    fun onNameChanges(nameValue: String) {
        val result = validateName(nameValue)
        _uiState.update {
            it.copy(
                name = it.name.copy(
                    value = nameValue,
                    isDirty = true,
                    isValid = result.isSuccess,
                )
            )
        }
    }

    fun onEmailChanges(emailValue: String) {
        val result = validateEmail(emailValue)
        _uiState.update {
            it.copy(
                email = it.email.copy(
                    value = emailValue,
                    isDirty = true,
                    isValid = result.isSuccess
                )
            )
        }
    }

    fun onBirthdayChanges(birthdayValue: String) {
        val result = validateBirthday(birthdayValue)
        _uiState.update {
            it.copy(
                birthday = it.birthday.copy(
                    value = birthdayValue,
                    isDirty = true,
                    isValid = result.isSuccess
                )
            )
        }
    }

}