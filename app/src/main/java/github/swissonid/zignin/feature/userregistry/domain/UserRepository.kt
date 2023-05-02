package github.swissonid.zignin.feature.userregistry.domain

import github.swissonid.zignin.feature.userregistry.domain.model.NewUser
import github.swissonid.zignin.feature.userregistry.domain.model.RegisteredUser

interface UserRepository {
    suspend fun registerUser(user: NewUser): Result<RegisteredUser>
    suspend fun currentUser(): Result<RegisteredUser>
    suspend fun removeCurrentUser(): Result<Unit>
}

sealed class UserRegistryException : Exception() {
    object AUserAlreadyExists : UserRegistryException()
    object NoUserIsRegistered : UserRegistryException()
    object UnknownErrorException : UserRegistryException()
}