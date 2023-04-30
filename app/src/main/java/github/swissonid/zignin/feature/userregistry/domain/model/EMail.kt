package github.swissonid.zignin.feature.userregistry.domain.model

/**
 * Email validation regex.
 * 1. ^[\w-\.]+ : Begins with word characters, underscores or hyphens, and must match one or more
 * occurrences. Must contain a "@" symbol right after the first characters.
 * 2. @([\w-]+\.)+ : It matches a word or hyphen, and must contain a dot "." right after the first
 * character. This match must occur at least one time, and be separated by a dot "."
 * 3. [\w-]{2,4}$ : The email string must end with two to four word characters
 */
private val EMAIL_REGEX = Regex("^[\\w-.]+@([\\w-]+\\.)+\\w{2,4}\$")

@JvmInline
value class EMail(val value: String) {
    companion object {
        fun fromString(value: String): Result<EMail> {
            return validateEmail(value).map { EMail(it) }
        }
    }
}


sealed class NotAValidEMailException : IllegalArgumentException() {
    object EmptyName : NotAValidEMailException()
    object OnlyBlankSpace : NotAValidEMailException()
    object InvalidEMail : NotAValidEMailException()
}

fun validateEmail(value: String): Result<String> {
    when {
        value.isEmpty() -> return Result.failure(NotAValidEMailException.EmptyName)
        value.isBlank() -> return Result.failure(NotAValidEMailException.OnlyBlankSpace)
    }
    EMAIL_REGEX.containsMatchIn(value).let {
        return if (it) Result.success(value) else Result.failure(NotAValidEMailException.InvalidEMail)
    }
}