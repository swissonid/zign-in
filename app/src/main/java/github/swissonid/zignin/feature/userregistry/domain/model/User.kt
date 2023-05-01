package github.swissonid.zignin.feature.userregistry.domain.model

sealed interface User {
    val name: Name
    val birthday: Birthday
    val email: EMail
}

@JvmInline
value class UserId(val value: String)

data class RegisteredUser(
    override val name: Name,
    override val birthday: Birthday,
    override val email: EMail,
    val id: UserId
) : User

data class NewUser(
    override val name: Name,
    override val birthday: Birthday,
    override val email: EMail,
) : User
