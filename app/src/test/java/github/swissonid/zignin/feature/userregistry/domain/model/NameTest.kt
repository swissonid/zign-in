package github.swissonid.zignin.feature.userregistry.domain.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Suite


@RunWith(Suite::class)
@Suite.SuiteClasses(
    InvalidNameTests::class,
    ValidNameTest::class
)
class NameTest

@RunWith(Parameterized::class)
class InvalidNameTests(val value: String, val expected: Any) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0}")
        fun data(): Collection<Array<Any>> = listOf(
            arrayOf("", NotAValidNameException.EmptyName),
            arrayOf(" ", NotAValidNameException.OnlyBlankSpace),
        )
    }

    @Test
    fun `checkIfStringIsAValidName - should return NotAValidNameException`() {
        val result = checkIfStringIsAValidName(value)
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isEqualTo(expected)
    }

    @Test
    fun `Name_fromString - should return NotAValidNameException`() {
        val result = Name.fromStringAndTrim(value)
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isEqualTo(expected)
    }

    @Test(expected = NotAValidNameException::class)
    fun `Name constructor`() {
        Name(value)
    }
}

class ValidNameTest {
    private val name = "Joe Done"

    @Test
    fun `checkIfStringIsAValidName - should return the given value when name is valid`() {
        val result = checkIfStringIsAValidName(name)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(name)
    }

    @Test
    fun `Name_fromString - should return a trimmed name when name is ' swissonid '`() {
        val result = Name.fromStringAndTrim(" $name ")
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().toString()).isEqualTo(name)
    }

    @Test
    fun `Name constructor`() {
        val result = Name(name)
        assertThat(result.toString()).isEqualTo(name)
    }
}

