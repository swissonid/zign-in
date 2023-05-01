package github.swissonid.zignin.feature.userregistry.presenation.screen.confirmation

import github.swissonid.zignin.feature.userregistry.domain.model.RegisteredUser

data class ConfirmationUiState(
    val isLoading: Boolean = true,
    val error: Int? = null,
    val user: RegisteredUser? = null,
)