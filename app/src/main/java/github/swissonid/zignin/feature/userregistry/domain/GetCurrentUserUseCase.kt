package github.swissonid.zignin.feature.userregistry.domain

import github.swissonid.zignin.feature.userregistry.domain.model.RegisteredUser

class GetCurrentUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(): Result<RegisteredUser> {
        return userRepository.currentUser()
    }
}