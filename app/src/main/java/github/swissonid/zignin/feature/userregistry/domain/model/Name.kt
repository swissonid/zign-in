package github.swissonid.zignin.feature.userregistry.domain.model

@JvmInline
value class Name(private val value: String) {
    init {
        checkIfStringIsAValidName(value)
            .onFailure { throw it }
    }

    override fun toString(): String {
        return value
    }

    companion object {
        /**
         * Create a [Name] from a [String]. If the string is valid but still has blank spaces at the beginning or end,
         * they will be removed.
         *
         * @param value string to create the [Name] from.
         * @return [Result] with [Name] if the string is a valid name, otherwise [Result] with [NotAValidNameException].
         */
        fun fromStringAndTrim(value: String): Result<Name> =
            checkIfStringIsAValidName(value).map { Name(it.trim()) }
    }
}

sealed class NotAValidNameException : IllegalArgumentException() {
    object EmptyName : NotAValidNameException()
    object OnlyBlankSpace : NotAValidNameException()
}

/**
 * Check if the string is a valid name.
 * @param value string to check.
 * @return [Result] with [Unit] if the string is a valid name, otherwise [Result] with [NotAValidNameException].
 */
fun checkIfStringIsAValidName(value: String): Result<String> = when {
    value.isEmpty() -> Result.failure(NotAValidNameException.EmptyName)
    value.isBlank() -> Result.failure(NotAValidNameException.OnlyBlankSpace)
    else -> Result.success(value)
}
