package github.swissonid.zignin.feature.userregistry.domain.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Suite

@RunWith(value = Suite::class)
@Suite.SuiteClasses(
    InvalidEMailTest::class,
    ValidEmailTest::class
)
class EMailTest


@RunWith(value = Parameterized::class)
class InvalidEMailTest(
    private val email: String,
    private val expectedException: NotAValidEMailException
) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0}")
        fun data(): Collection<Array<Any?>> = listOf(
            arrayOf("", NotAValidEMailException.EmptyName),
            arrayOf(" ", NotAValidEMailException.OnlyBlankSpace),
            arrayOf("a", NotAValidEMailException.InvalidEMail),
            arrayOf("a@b.c", NotAValidEMailException.InvalidEMail),
            arrayOf("a@.ch", NotAValidEMailException.InvalidEMail),
            arrayOf("a@.ch", NotAValidEMailException.InvalidEMail),
            arrayOf("a@@b.ch", NotAValidEMailException.InvalidEMail),
            arrayOf("a@b@.ch", NotAValidEMailException.InvalidEMail),
            arrayOf("a@bch", NotAValidEMailException.InvalidEMail),
        )
    }

    @Test
    fun `validateEmail - should throw a NotAValidEMailException`() {
        val result = validateEmail(email)
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isEqualTo(expectedException)
    }

    @Test
    fun `EMail_fromString - should throw a NotAValidEMailException`() {
        val result = EMail.fromString(email)
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isEqualTo(expectedException)
    }

    @Test(expected = NotAValidEMailException::class)
    fun `EMail constructor`() {
        EMail(email)
    }
}

@RunWith(value = Parameterized::class)
class ValidEmailTest(private val email: String) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0}")
        fun data(): Collection<String> = listOf(
            "a@b.ch",
            "a@b.nom.co"
        )
    }

    @Test
    fun `EMail_fromString - should return a valid email`() {
        val result = EMail.fromString(email)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().value).isEqualTo(email)
    }

    @Test
    fun `validateEmail - should return a valid email`() {
        val result = validateEmail(email)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(email)
    }

    @Test
    fun `EMail constructor`() {
        val emailObject = EMail(email)
        assertThat(emailObject.value).isEqualTo(email)
    }
}

// This class is intended to be used for debugging purposes only
/*class EmailTestDebug {
    @Test
    fun `should throw a NotAValidEMailException - Debug`() {
        val result = EMail.fromString("a")
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isEqualTo(NotAValidEMailException.InvalidEMail)
    }

    @Test
    fun `should be a valid email - Debug`() {
        val result = EMail.fromString("a@b.ch")
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().value).isEqualTo("a@b.ch")
    }
}*/



