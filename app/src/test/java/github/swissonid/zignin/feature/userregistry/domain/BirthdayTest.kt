package github.swissonid.zignin.feature.userregistry.domain

import com.google.common.truth.Truth.assertThat
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Suite


@RunWith(Suite::class)
@Suite.SuiteClasses(
    InvalidStringBirthdayTest::class,
    InvalidLocalDateBirthdayTest::class,
    ValidStringBirthdayTest::class,
    ValidLocalDateBirthdayTest::class
)
class BirthdayTest

@RunWith(Parameterized::class)
class InvalidStringBirthdayTest(private val value: String, private val expectedResult: Any) {


    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0}")
        fun data(): Collection<Array<Any?>> = listOf(
            arrayOf("", NotAValidBirthdayException.InvalidDate),
            arrayOf(" ", NotAValidBirthdayException.InvalidDate),
            arrayOf("any string", NotAValidBirthdayException.InvalidDate),
            arrayOf("31.12.1985", NotAValidBirthdayException.InvalidDate),
            arrayOf("2022-01-01", NotAValidBirthdayException.ToYoung),
            arrayOf("1899-12-31", NotAValidBirthdayException.ToOld),
        )
    }

    @Test
    fun `Birthday_fromString - should throw a NotAValidBirthdayException`() {
        val result = Birthday.fromString(value)
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isEqualTo(expectedResult)
    }

    @Test
    fun `checkIfDateIsAValidBirthday - should throw a NotAValidBirthdayException`() {
        val result = checkIfDateIsAValidBirthday(value)
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isEqualTo(expectedResult)
    }
}

@RunWith(Parameterized::class)
class InvalidLocalDateBirthdayTest(private val value: LocalDate, private val expectedResult: Any) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0}")
        fun data(): Collection<Array<Any?>> = listOf(
            arrayOf("2022-01-01".toLocalDate(), NotAValidBirthdayException.ToYoung),
            arrayOf("1899-12-31".toLocalDate(), NotAValidBirthdayException.ToOld),
        )
    }

    @Test
    fun `Birthday_fromString - should throw a NotAValidBirthdayException`() {
        val result = Birthday.fromLocalDate(value)
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isEqualTo(expectedResult)
    }

    @Test
    fun `checkIfDateIsAValidBirthday - should throw a NotAValidBirthdayException`() {
        val result = checkIfDateIsAValidBirthday(value)
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isEqualTo(expectedResult)
    }
}

class ValidStringBirthdayTest {
    private val birthday = "1985-12-31"

    @Test
    fun `Birthday_fromString - should create a Birthday`() {
        val result = Birthday.fromString(birthday)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().value).isEqualTo(birthday.toLocalDate())
    }

    @Test
    fun `checkIfDateIsAValidBirthday - should return the same value as was entered`() {
        val result = checkIfDateIsAValidBirthday(birthday)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(birthday)
    }
}


class ValidLocalDateBirthdayTest {
    private val birthday = "1985-12-31".toLocalDate()

    @Test
    fun `Birthday_fromString - should create a Birthday`() {
        val result = Birthday.fromLocalDate(birthday)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().value).isEqualTo(birthday)
    }

    @Test
    fun `checkIfDateIsAValidBirthday - should return the same value as was entered`() {
        val result = checkIfDateIsAValidBirthday(birthday)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(birthday)
    }
}
