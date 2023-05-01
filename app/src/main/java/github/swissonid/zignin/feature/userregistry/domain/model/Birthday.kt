package github.swissonid.zignin.feature.userregistry.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate
import java.time.format.DateTimeParseException


val latestBirthday = "2021-12-31".toLocalDate()
val earliestBirthday = "1900-01-01".toLocalDate()


/**
 * Birthday model.
 * @param value [LocalDate] value of the birthday.
 */
@JvmInline
value class Birthday(val value: LocalDate) {
    init {
        checkIfDateIsAValidBirthday(value).onFailure { throw it }
    }

    companion object {
        /**
         * Create a [Birthday] from a [String].
         *
         * @param value string to create the [Birthday] from.
         * @return [Result] with [Birthday] if the string is a valid birthday, otherwise [Result] with [NotAValidBirthdayException].
         */
        fun fromString(value: String): Result<Birthday> {
            return try {
                Result.success(Birthday(value.toLocalDate()))
            } catch (e: Exception) {
                if (e !is NotAValidBirthdayException && e is IllegalArgumentException) {
                    Result.failure<NotAValidBirthdayException>(NotAValidBirthdayException.InvalidDate)
                }
                // The kotlinx.datetime.DateTimeFormatException is not public, so we can't catch it.
                // We can only catch the DateTimeParseException, which is the cause of the DateTimeFormatException.
                if (e.cause != null && e.cause is DateTimeParseException) {
                    Result.failure(NotAValidBirthdayException.InvalidDate)
                } else {
                    Result.failure(e)
                }
            }
        }

        /**
         * Create a [Birthday] from a [LocalDate].
         * @param value [LocalDate] to create the [Birthday] from.
         * @return [Result] with [Birthday] if the string is a valid birthday, otherwise [Result] with [NotAValidBirthdayException].
         */
        fun fromLocalDate(value: LocalDate): Result<Birthday> {
            return try {
                Result.success(Birthday(value))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
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


// A possible improvement would be add the root cause of the exception to the exception itself.
sealed class NotAValidBirthdayException : IllegalArgumentException() {
    object InvalidDate : NotAValidBirthdayException()
    object ToOld : NotAValidBirthdayException()
    object ToYoung : NotAValidBirthdayException()
}