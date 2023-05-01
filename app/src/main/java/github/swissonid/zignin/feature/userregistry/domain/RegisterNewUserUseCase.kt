package github.swissonid.zignin.feature.userregistry.domain

import github.swissonid.zignin.feature.userregistry.domain.model.NewUser
import github.swissonid.zignin.feature.userregistry.domain.model.RegisteredUser

class RegisterNewUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(user: NewUser): Result<RegisteredUser> =
        userRepository.registerUser(user)
}