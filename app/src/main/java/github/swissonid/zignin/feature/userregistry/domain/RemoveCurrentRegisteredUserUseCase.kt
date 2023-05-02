package github.swissonid.zignin.feature.userregistry.domain

class RemoveCurrentRegisteredUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(): Result<Unit> =
        userRepository.removeCurrentUser()
}