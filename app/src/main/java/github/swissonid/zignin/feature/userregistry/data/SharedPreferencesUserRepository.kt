package github.swissonid.zignin.feature.userregistry.data

import android.content.SharedPreferences
import github.swissonid.zignin.feature.userregistry.domain.UserRegistryException
import github.swissonid.zignin.feature.userregistry.domain.UserRepository
import github.swissonid.zignin.feature.userregistry.domain.model.NewUser
import github.swissonid.zignin.feature.userregistry.domain.model.RegisteredUser
import github.swissonid.zignin.feature.userregistry.domain.model.UserId
import github.swissonid.zignin.feature.userregistry.domain.model.toRegisteredUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

const val REGISTERED_USER_KEY = "registeredUser"

class SharedPreferencesUserRepository(
    private val sharedPreferences: SharedPreferences,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserRepository {
    override suspend fun registerUser(user: NewUser): Result<RegisteredUser> =
        withContext(ioDispatcher) {
            currentUser().onSuccess {
                return@withContext Result.failure(UserRegistryException.AUserAlreadyExists)
            }

            val userId = UserId(UUID.randomUUID().toString())
            val registeredUser = user.toRegisteredUser(userId)

            with(sharedPreferences.edit()) {
                registeredUser.toDto().toJson().let { json ->
                    putString(REGISTERED_USER_KEY, json)
                    apply()
                }
            }
            return@withContext Result.success(registeredUser)
        }

    override suspend fun currentUser(): Result<RegisteredUser> = withContext(ioDispatcher) {
        val json = sharedPreferences.getString(REGISTERED_USER_KEY, null)
            ?: return@withContext Result.failure(UserRegistryException.NoUserIsRegistered)

        val dto = UserDto.fromJson(json)
        if (dto.isFailure) {
            return@withContext Result.failure(UserRegistryException.UnknownErrorException)
        }
        val domain = dto.getOrThrow().toDomain()
        if (domain.isFailure) {
            return@withContext Result.failure(UserRegistryException.UnknownErrorException)
        }
        when (val user = domain.getOrThrow()) {
            is RegisteredUser -> return@withContext Result.success(user)
            else -> return@withContext Result.failure(UserRegistryException.UnknownErrorException)
        }
    }

    override suspend fun removeCurrentUser(): Result<Unit> = withContext(ioDispatcher) {
        val result = currentUser()
        if (result.isFailure) {
            return@withContext Result.failure(UserRegistryException.NoUserIsRegistered)
        }
        
        with(sharedPreferences.edit()) {
            remove(REGISTERED_USER_KEY)
            apply()
        }
        return@withContext Result.success(Unit)
    }
}