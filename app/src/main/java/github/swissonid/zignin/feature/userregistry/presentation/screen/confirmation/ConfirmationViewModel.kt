package github.swissonid.zignin.feature.userregistry.presentation.screen.confirmation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import github.swissonid.zignin.R
import github.swissonid.zignin.feature.userregistry.domain.GetCurrentUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmationViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
) : ViewModel() {

    init {
        viewModelScope.launch {
            val result = getCurrentUserUseCase()
            result.onSuccess { _uiState.update { state -> state.copy(user = it) } }
            result.onFailure {
                _uiState.update { state ->
                    state.copy(error = R.string.error__general)
                }
            }
        }
    }

    private val _uiState = MutableStateFlow(ConfirmationUiState())

    val uiState: StateFlow<ConfirmationUiState> = _uiState.asStateFlow()
}