package github.swissonid.zignin.feature.userregistry.domain

import android.util.Log
import github.swissonid.zignin.feature.userregistry.domain.model.RegisteredUser

class GetCurrentUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(): Result<RegisteredUser> {
        val result = userRepository.currentUser()
        Log.e("GetCurrentUserUseCase", "Was is failerue${result.isFailure}")
        return result
    }
}