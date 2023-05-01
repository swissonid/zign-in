package github.swissonid.zignin.feature.userregistry.data

import github.swissonid.zignin.feature.userregistry.domain.model.Birthday
import github.swissonid.zignin.feature.userregistry.domain.model.EMail
import github.swissonid.zignin.feature.userregistry.domain.model.Name
import github.swissonid.zignin.feature.userregistry.domain.model.NewUser
import github.swissonid.zignin.feature.userregistry.domain.model.RegisteredUser
import github.swissonid.zignin.feature.userregistry.domain.model.User
import github.swissonid.zignin.feature.userregistry.domain.model.UserId
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String? = null,
    val name: String,
    val email: String,
    val birthday: String,
) {
    companion object {
        // Because we coming from the domain and we know all the branches of the sealed class
        // we do not need to use the Result type here
        @JvmStatic
        fun fromDomain(user: User): UserDto {
            return user.toDto()
        }

        fun fromJson(json: String): Result<UserDto> {
            return try {
                val dto = NonStrictJsonSerializer.decodeFromString(serializer(), json)
                Result.success(dto)
            } catch (e: Exception) {
                Result.failure(e)
            }

        }
    }

    fun toJson(): String {
        return NonStrictJsonSerializer.encodeToString(serializer(), this)
    }

    fun toDomain(): Result<User> {
        return try {
            // Like that we can localize the error to the specific field
            val name = Name.fromStringAndTrim(name)
            if (name.isFailure) return Result.failure(name.exceptionOrNull()!!)

            val birthday = Birthday.fromString(birthday)
            if (birthday.isFailure) return Result.failure(birthday.exceptionOrNull()!!)

            val eMail = EMail.fromString(email)
            if (eMail.isFailure) return Result.failure(eMail.exceptionOrNull()!!)

            if (id == null) {
                return Result.success(
                    NewUser(
                        name = name.getOrThrow(),
                        birthday = birthday.getOrThrow(),
                        email = eMail.getOrThrow(),
                    )
                )
            }

            return Result.success(
                RegisteredUser(
                    name = name.getOrThrow(),
                    birthday = birthday.getOrThrow(),
                    email = eMail.getOrThrow(),
                    id = UserId(id)
                )
            )

        } catch (e: Exception) {
            Result.failure(e)
        }

    }
}

fun User.toDto(): UserDto {
    return UserDto(
        name = name.toString(),
        birthday = birthday.toString(),
        email = email.toString(),
        id = when (this) {
            // By doing this we are forcing the compiler to check that we are handling all the branches
            is RegisteredUser -> id.value
            is NewUser -> null
        }
    )
}