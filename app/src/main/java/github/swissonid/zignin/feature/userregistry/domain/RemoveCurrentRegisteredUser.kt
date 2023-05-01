package github.swissonid.zignin.feature.userregistry.domain

class RemoveCurrentRegisteredUser(private val userRepository: UserRepository) {
    suspend operator fun invoke(): Result<Unit> =
        userRepository.removeCurrentUser()
}