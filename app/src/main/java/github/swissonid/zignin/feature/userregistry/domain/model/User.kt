package github.swissonid.zignin.feature.userregistry.domain.model

sealed interface User {
    val name: Name
    val birthday: Birthday
    val eMail: EMail
}

@JvmInline
value class UserId(val value: String)

data class RegisteredUser(
    override val name: Name,
    override val birthday: Birthday,
    override val eMail: EMail,
    val id: UserId
) : User

data class NewUser(
    override val name: Name,
    override val birthday: Birthday,
    override val eMail: EMail,
) : User

