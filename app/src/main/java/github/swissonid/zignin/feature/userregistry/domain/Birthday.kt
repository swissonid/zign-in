package github.swissonid.zignin.feature.userregistry.domain

import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate


val latestBirthday = "2021-12-31".toLocalDate()
val earliestBirthday = "1900-01-01".toLocalDate()


@JvmInline
value class Birthday private constructor(val value: LocalDate) {
    companion object {
        /**
         * Create a [Birthday] from a [String].
         *
         * @param value string to create the [Birthday] from.
         * @return [Result] with [Birthday] if the string is a valid birthday, otherwise [Result] with [NotAValidBirthdayException].
         */
        fun fromString(value: String): Result<Birthday> {
            return checkIfDateIsAValidBirthday(value).map { Birthday(it.toLocalDate()) }
        }

        fun fromLocalDate(value: LocalDate): Result<Birthday> {
            return checkIfDateIsAValidBirthday(value).map { Birthday(it) }
        }
    }
}


sealed class NotAValidBirthdayException : IllegalArgumentException() {
    object InvalidDate : NotAValidBirthdayException()
    object ToOld : NotAValidBirthdayException()
    object ToYoung : NotAValidBirthdayException()
}

/**
 * Check if the string is a valid birthday.
 * @param isoString [String] to check.
 * @return [Result] with [String] if the string is a valid birthday, otherwise [Result] with [NotAValidBirthdayException].
 */
fun checkIfDateIsAValidBirthday(isoString: String): Result<String> {
    return try {
        val localDate = LocalDate.parse(isoString)
        checkIfDateIsAValidBirthday(localDate).map { isoString }
    } catch (e: Exception) {
        Result.failure(NotAValidBirthdayException.InvalidDate)
    }
}


/**
 * Check if the string is a valid birthday.
 * @param localDate [LocalDate] to check.
 * @return [Result] with [LocalDate] if the string is a valid birthday, otherwise [Result] with [NotAValidBirthdayException].
 */
fun checkIfDateIsAValidBirthday(localDate: LocalDate): Result<LocalDate> {
    if (localDate > latestBirthday) {
        return Result.failure(NotAValidBirthdayException.ToYoung)
    }
    if (localDate < earliestBirthday) {
        return Result.failure(NotAValidBirthdayException.ToOld)
    }
    return Result.success(localDate)
}